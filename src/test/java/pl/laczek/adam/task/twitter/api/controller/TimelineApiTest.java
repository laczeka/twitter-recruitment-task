package pl.laczek.adam.task.twitter.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.laczek.adam.task.twitter.CustomPageImpl;
import pl.laczek.adam.task.twitter.api.controller.follow.FollowingCreation;
import pl.laczek.adam.task.twitter.api.controller.tweet.Tweet;
import pl.laczek.adam.task.twitter.api.controller.tweet.TweetCreation;

import java.util.List;

import static jakarta.servlet.http.HttpServletResponse.SC_NO_CONTENT;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.laczek.adam.task.twitter.api.controller.TimelineApiController.TIMELINE_API_PATH;
import static pl.laczek.adam.task.twitter.api.controller.follow.FollowController.FOLLOW_API_PATH;
import static pl.laczek.adam.task.twitter.api.controller.tweet.TweetApiController.TWEET_API_PATH;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/clear_data.sql")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class TimelineApiTest {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Test
    void testWall() throws Exception {
        // given
        successfulTweet(new TweetCreation("jnowak", "First tweet"));
        successfulTweet(new TweetCreation("jnowak", "Second tweet"));
        successfulTweet(new TweetCreation("kowalski", "Lorem ipsum"));

        // when
        MvcResult mvcResult = mockMvc.perform(get(TIMELINE_API_PATH + "/jnowak/tweets"))
                .andExpect(status().is(SC_OK))
                .andReturn();

        // then
        CustomPageImpl<Tweet> page = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });
        List<Tweet> actualTweets = page.getContent();

        assertThat(actualTweets).hasSize(2);
        assertThat(actualTweets.get(0).createTime())
                .as("ordering is not broken")
                .isAfter(actualTweets.get(1).createTime());
        assertThat(actualTweets)
                .extracting(Tweet::text)
                .containsSequence("Second tweet", "First tweet");
    }

    @Test
    void testTimeline() throws Exception {
        // given
        successfulTweet(new TweetCreation("nowak", "Nowak tweet 1"));
        successfulTweet(new TweetCreation("kowalski", "kowalski tweet 1"));
        successfulTweet(new TweetCreation("nowak", "Nowak tweet 2"));

        createUserBySendFirstTweet("zieba");

        successfulFollow(new FollowingCreation("zieba", "nowak"));
        successfulFollow(new FollowingCreation("zieba", "kowalski"));

        // when
        MvcResult mvcResult = mockMvc.perform(get(TIMELINE_API_PATH + "/zieba/timeline"))
                .andExpect(status().is(SC_OK))
                .andReturn();

        // then
        CustomPageImpl<Tweet> page = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });
        List<Tweet> actualTimeline = page.getContent();

        assertThat(actualTimeline).hasSize(3);
        assertThat(actualTimeline.get(0).createTime())
                .as("ordering is not broken")
                .isAfter(actualTimeline.get(1).createTime());
        assertThat(actualTimeline)
                .extracting(Tweet::text)
                .containsSequence("Nowak tweet 2", "kowalski tweet 1", "Nowak tweet 1");
    }

    private void successfulFollow(FollowingCreation following) throws Exception {
        mockMvc.perform(post(FOLLOW_API_PATH)
                        .content(objectMapper.writeValueAsString(following))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(SC_NO_CONTENT));
    }

    private void createUserBySendFirstTweet(String author) throws Exception {
        mockMvc.perform(post(TWEET_API_PATH)
                .content(objectMapper.writeValueAsString(new TweetCreation(author, "First tweet to create author account")))
                .contentType(MediaType.APPLICATION_JSON));
    }

    private void successfulTweet(TweetCreation tweet) throws Exception {
        mockMvc.perform(post(TWEET_API_PATH)
                        .content(objectMapper.writeValueAsString(tweet))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(SC_OK));
    }
}
