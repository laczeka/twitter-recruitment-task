package pl.laczek.adam.task.twitter.domain.model.author;

import com.google.common.base.Preconditions;
import lombok.EqualsAndHashCode;

import lombok.Getter;
import lombok.ToString;
import pl.laczek.adam.task.twitter.domain.profanity.EnglishProfanityFilter;

@EqualsAndHashCode
@ToString
@Getter
public class Author {
    private static final String NORMALIZATION_REGEX = "[^a-zA-Z0-9]";
    private final String userId;
    Author(String userId) {
        this.userId = userId;
    }

    //this logic can be moved to of method but i don't want to use validation for Timeline controllers
    public Author validate(){
        EnglishProfanityFilter.validate(userId);
        return this;
    }

    public static Author of(String userId) {
        Preconditions.checkNotNull(userId, "Author can't be null");
        Preconditions.checkArgument(userId.length() > 3 && userId.length() <= 20, "Author login should have 3-20 length");
        return new Author(normalize(userId));
    }

    //GMAIL like normalization
    private static String normalize(String userId){
        return userId.replaceAll(NORMALIZATION_REGEX, "").toLowerCase();
    }
}
