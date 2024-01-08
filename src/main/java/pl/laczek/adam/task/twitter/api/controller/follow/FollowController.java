package pl.laczek.adam.task.twitter.api.controller.follow;

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
import pl.laczek.adam.task.twitter.domain.model.follow.FollowingService;

import static pl.laczek.adam.task.twitter.api.controller.follow.FollowController.FOLLOW_API_PATH;

@Tag(name = "Follows", description = "Follows APIs")
@RestController
@RequestMapping(FOLLOW_API_PATH)
@RequiredArgsConstructor
public class FollowController {
    public static final String FOLLOW_API_PATH = "/api/v1/following";
    private final FollowingService followingService;

    @Operation(operationId = "createFollow",
            summary = "Creates subscription (one user follow tweets of second user)",
            description = "Both users must *exists in system*. Both users are *mandatory*")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful subscription",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FollowingCreation.class))),
            @ApiResponse(responseCode = "400", description = "user is already followed")
    })
    @PostMapping("")
    //real twitter uses path param https://developer.twitter.com/en/docs/twitter-api/users/follows/api-reference/post-users-source_user_id-following
    //but with DTO payload it was less code to write/validate. For corporate code i would use real twitter approach
    public ResponseEntity follow(@Parameter(description = "follow payload", required = true)
                                                    @Valid @RequestBody FollowingCreation followingCreation) {
        followingService.follow(followingCreation);
        return ResponseEntity.noContent().build();
    }
}
