package pl.laczek.adam.task.twitter.api.controller.follow;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.laczek.adam.task.twitter.api.controller.tweet.TweetCreation;

import static jakarta.servlet.http.HttpServletResponse.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.laczek.adam.task.twitter.api.controller.follow.FollowController.FOLLOW_API_PATH;
import static pl.laczek.adam.task.twitter.api.controller.tweet.TweetApiController.TWEET_API_PATH;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/clear_data.sql")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("Follow REST api test")
class FollowApiTest {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Test
    @DisplayName("204 is return when emusk successfully follow jbezos")
    void shouldCreateFollowingForHappyPath() throws Exception {
        // given
        createUserBySendFirstTweet("emusk");
        createUserBySendFirstTweet("jbezos");
        var followingCreation = new FollowingCreation("emusk", "jbezos");
        // when
        // then
        successfulFollow(followingCreation);
    }

    private void createUserBySendFirstTweet(String author) throws Exception {
        mockMvc.perform(post(TWEET_API_PATH)
                .content(objectMapper.writeValueAsString(new TweetCreation(author, "First tweet to create author account")))
                .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("400 error is return when amalysz try to follow iswiatek twice")
    void shouldBlocksSecondSubscription() throws Exception {
        // given
        createUserBySendFirstTweet("amalysz");
        createUserBySendFirstTweet("iswiatek");

        FollowingCreation givenFollowing = new FollowingCreation("amalysz", "iswiatek");

        //when
        successfulFollow(givenFollowing);

        MvcResult result = mockMvc.perform(post(FOLLOW_API_PATH)
                        .content(objectMapper.writeValueAsString(givenFollowing))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(SC_BAD_REQUEST))
                .andReturn();

        // then
        assertThat(result.getResponse().getContentAsString())
                .isEqualTo("User amalysz already follow iswiatek");
    }

    @Test
    @DisplayName("400 error is return when amalysz try to follow blank user id")
    void shouldBlocksBlankUser() throws Exception {
        // given
        FollowingCreation givenFollowing = new FollowingCreation("amalysz", "");

        //when
        MvcResult result = mockMvc.perform(post(FOLLOW_API_PATH)
                        .content(objectMapper.writeValueAsString(givenFollowing))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(SC_BAD_REQUEST))
                .andReturn();

        // then
        assertThat(result.getResponse().getContentAsString())
                .isEqualTo("followedUserId: must not be blank");
    }

    @Test
    @DisplayName("404 error is return when any user not exists")
    void shouldBlocksFollowingsIfUserNotExists() throws Exception {
        // given
        FollowingCreation givenFollowing = new FollowingCreation("amalysz", "iswiatek");

        //when
        MvcResult result = mockMvc.perform(post(FOLLOW_API_PATH)
                        .content(objectMapper.writeValueAsString(givenFollowing))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(SC_NOT_FOUND))
                .andReturn();

        // then
        assertThat(result.getResponse().getContentAsString())
                .isEqualTo("User not exists: amalysz");
    }

    private void successfulFollow(FollowingCreation following) throws Exception {
        mockMvc.perform(post(FOLLOW_API_PATH)
                        .content(objectMapper.writeValueAsString(following))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(SC_NO_CONTENT));
    }
}
