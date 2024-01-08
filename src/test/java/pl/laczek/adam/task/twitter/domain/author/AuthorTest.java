package pl.laczek.adam.task.twitter.domain.author;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.laczek.adam.task.twitter.api.exception.ProfanityException;
import pl.laczek.adam.task.twitter.domain.model.author.Author;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Author value object tests")
class AuthorTest {

    @Test
    @DisplayName("Login should have length 3-20")
    void testLoginLength() {
        String givenToShort = "ab";
        String givenToLong = IntStream.rangeClosed(1, 21)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());
        assertThatThrownBy(() -> Author.of(givenToShort))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Author.of(givenToLong))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"John_Doe@123", "John Doe 123", "johndoe123", "john.doe123", "j_o_h_n_d_o_e_1_2_3"})
    @DisplayName("Support normalization like it is Gmail")
    void testNormalization(String givenInput) {
        Author expectedAuthor = Author.of("johndoe123");
        Author givenAuthor = Author.of(givenInput);
        assertThat(givenAuthor).isEqualTo(expectedAuthor);
    }

    @ParameterizedTest
    @ValueSource(strings = {"bastards", "ba_st_ar_ds", "s*l*u*t"})
    @DisplayName("Swear words are not allowed as logins")
    void testProfanity(String givenInput) {
        var author = Author.of(givenInput);
        assertThatThrownBy(author::validate)
                .isInstanceOf(ProfanityException.class);
    }
}