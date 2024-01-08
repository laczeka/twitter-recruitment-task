package pl.laczek.adam.task.twitter.api.controller.tweet;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.laczek.adam.task.twitter.api.controller.tweet.TweetApiController.TWEET_API_PATH;


@SpringBootTest
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/clear_data.sql")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class TweetApiTest {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Test
    void shouldPostMessageHappyPath() throws Exception {
        // given
        TweetCreation tweetCreation = new TweetCreation("nowak", "Nowak tweet");

        // when
        MvcResult mvcResult = mockMvc.perform(post(TWEET_API_PATH)
                        .content(objectMapper.writeValueAsString(tweetCreation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(SC_OK))
                .andReturn();

        // then
        Tweet actualTweet = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Tweet.class);

        assertThat(actualTweet).isNotNull()
                .extracting(Tweet::text)
                .isEqualTo("Nowak tweet");
        assertThat(actualTweet.createTime())
                .isNotNull()
                .isBefore(LocalDateTime.now());
    }

    @ParameterizedTest
    @MethodSource("provideWrongTweets")
    void shouldValidateTweetCreation(String authorId, String text, String errorMessage) throws Exception {
        // given
        TweetCreation tweetCreation = new TweetCreation(authorId, text);

        // when
        String response = mockMvc.perform(post(TWEET_API_PATH)
                        .content(objectMapper.writeValueAsString(tweetCreation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(SC_BAD_REQUEST))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
        assertThat(response).isEqualTo(errorMessage);
    }

    @Test
    void shouldSwearInUserName() throws Exception {
        // given
        TweetCreation givenTweet = new TweetCreation("bastards", "Lorem ipsum");

        // when
        String response = mockMvc.perform(post(TWEET_API_PATH)
                        .content(objectMapper.writeValueAsString(givenTweet))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(SC_BAD_REQUEST))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
        assertThat(response).isEqualTo("Used profanity: bastards");
    }

    private static Stream<Arguments> provideWrongTweets() {
        return Stream.of(
                Arguments.of("", "Lorem ipsum", "authorId: length must be between 3 and 20"),
                Arguments.of("nowak", "a", "text: length must be between 2 and 140"),
                Arguments.of("nowak", "html injection<script>is forbidden", "text: Unsafe HTML tags included")
                );
    }


}