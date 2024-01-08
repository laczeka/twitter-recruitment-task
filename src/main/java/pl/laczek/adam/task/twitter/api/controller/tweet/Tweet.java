package pl.laczek.adam.task.twitter.api.controller.tweet;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "Tweet", title = "Tweet read model")

public record Tweet(
        @Schema(example = "login1", description = "Author login", name = "authorId")
        String authorId,
        @Schema(example = "Lorem ipsum ", description = "Tweet content", name = "text")
        String text,
        @Schema(example = "2024-01-01T21:30:45", description = "Tweet create time", name = "createTime")
        LocalDateTime createTime) {

}
