package pl.laczek.adam.task.twitter.api.controller.tweet;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.laczek.adam.task.twitter.domain.model.tweet.TweetService;

import static pl.laczek.adam.task.twitter.api.controller.tweet.TweetApiController.TWEET_API_PATH;

@Tag(name = "Tweet", description = "Tweet APIs")
@RestController
@RequiredArgsConstructor
@RequestMapping(TWEET_API_PATH)
public class TweetApiController {
    public static final String TWEET_API_PATH = "/api/v1/tweet";
    private final TweetService tweetService;

    @Operation(operationId = "createTweet",
            summary = "Create new tweet",
            description = """
                    Author login and tweet content are **mandatory**.
                    * max tweet length - 140 characters.
                    * min tweet length - 2 characters
                    * Author login with length min = 3, max = 20
                    * If author login not exists it would be created with first tweet
                    * HTML content is forbidden
                    """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful creation of Tweet",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TweetCreation.class))),
            @ApiResponse(responseCode = "400", description = "Bad length of post/author")
    })
    @PostMapping("")
    public ResponseEntity<Tweet> createTweet(@Parameter(required = true, description = "Tweet payload")
                                             @Valid @RequestBody TweetCreation tweetCreation) {
        return ResponseEntity.ok(tweetService.addPost(tweetCreation));
    }
}
