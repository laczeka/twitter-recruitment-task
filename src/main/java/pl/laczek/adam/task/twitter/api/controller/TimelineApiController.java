package pl.laczek.adam.task.twitter.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.laczek.adam.task.twitter.api.controller.tweet.Tweet;
import pl.laczek.adam.task.twitter.domain.model.author.Author;
import pl.laczek.adam.task.twitter.domain.model.tweet.TimelineService;

import static pl.laczek.adam.task.twitter.api.controller.TimelineApiController.TIMELINE_API_PATH;


@Tag(name = "Timeline", description = "Timeline and wall APIs")
@RestController
@RequestMapping(TIMELINE_API_PATH)
@RequiredArgsConstructor
public class TimelineApiController {
    public static final String TIMELINE_API_PATH = "/api/v1/users";
    private final TimelineService tweetService;

    @Operation(summary = "Returns messages created by author in chronological order",
            description = """
                    Author login is mandatory
                    Result is paginated, sort query param would be ignored
                    """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tweets from wall",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Tweet.class)))),
            @ApiResponse(responseCode = "400", description = "user not exists")
    })
    @Parameters({
            @Parameter(name = "page", description = "Page number, default is 0", example = "0"),
            @Parameter(name = "size", description = "Number of records per page, default is 20, max is 100", example = "20")
    })
    @GetMapping("{userId}/tweets")
    public Page<Tweet> wall(@Parameter(description = "user ID", required = true) @PathVariable String userId,
                            @Parameter(hidden = true) Pageable pageable) {
        return tweetService.getAllTweetsOfAuthor(Author.of(userId), pageable);
    }

    @Operation(summary = "Returns all tweets created by followed authors: newest first.",
            description = """
                    User ID is mandatory
                    Result is paginated, sort query param would be ignored
                                    """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Timeline tweets",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Tweet.class)))),
            @ApiResponse(responseCode = "400", description = "user not exists")
    })
    @Parameters({
            @Parameter(name = "page", description = "Page number, default is 0", example = "0"),
            @Parameter(name = "size", description = "Number of records per page, default is 20, max is 100", example = "20")
    })
    @GetMapping("{userId}/timeline")
    public Page<Tweet> timeline(@Parameter(description = "ID of the user", required = true) @PathVariable String userId,
                                @Parameter(hidden = true) Pageable pageable) {
        return tweetService.getTweetsOnTimeline(Author.of(userId), pageable);
    }
}
