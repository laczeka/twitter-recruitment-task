package pl.laczek.adam.task.twitter.domain.profanity;

import com.modernmt.text.profanity.ProfanityFilter;
import pl.laczek.adam.task.twitter.api.exception.ProfanityException;

public class EnglishProfanityFilter {
    private EnglishProfanityFilter(){}
    private static final ProfanityFilter profanityFilter = new ProfanityFilter();
    private static final String ENGLISH = "en";

    private static boolean isProfanity(String input) {
        return profanityFilter.test(ENGLISH, input);
    }

    public static void validate(String input){
        if(isProfanity(input)){
            throw new ProfanityException(input);
        }
    }


}
