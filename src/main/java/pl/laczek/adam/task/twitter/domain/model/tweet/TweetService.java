package pl.laczek.adam.task.twitter.domain.model.tweet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.laczek.adam.task.twitter.api.controller.tweet.Tweet;
import pl.laczek.adam.task.twitter.api.controller.tweet.TweetCreation;
import pl.laczek.adam.task.twitter.domain.model.author.Author;
import pl.laczek.adam.task.twitter.domain.model.author.AuthorService;
import pl.laczek.adam.task.twitter.domain.ports.TweetRepository;

@Service
@RequiredArgsConstructor
public class TweetService {
    private final TweetMapper tweetMapper;
    private final TweetRepository tweetRepository;
    private final AuthorService userService;

    @Transactional
    public Tweet addPost(TweetCreation tweetCreation) {
        userService.createAuthorIfNotExists(Author.of(tweetCreation.authorId()).validate());
        var savedTweet = tweetRepository.save(tweetMapper.toJpa(tweetCreation));
        return tweetMapper.toApi(savedTweet);
    }
}
