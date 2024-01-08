package pl.laczek.adam.task.twitter.api.controller.tweet;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import pl.laczek.adam.task.twitter.infrastructure.validation.NoHtmlInjection;

@Schema(name = "TweetCreation", title = "Post object used to create new post request")
public record TweetCreation(
        @Schema(example = "login1", description = "Author login", name = "authorId")
        @Length(min = 3, max = 20)
        String authorId,

        @Schema(example = "Lorem ipsum ", description = "Tweet content, HTML is forbidden, length 2-140", name = "text")
        @NoHtmlInjection
        @Length(min = 2, max = 140)
        String text) {
}
