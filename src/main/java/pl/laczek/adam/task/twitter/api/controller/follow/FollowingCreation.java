package pl.laczek.adam.task.twitter.api.controller.follow;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "FollowingCreation", title = "Subscription creation used to follow another user's tweets. Both logins should exists")
public record FollowingCreation(
        @Schema(example = "login1", description = "Login of user", name = "userId")
        @NotBlank
        String userId,
        @Schema(example = "login2", description = "Login of another user that we want to follow him", name = "followedUserId")
        @NotBlank
        String followedUserId) {

}
