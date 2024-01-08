package pl.laczek.adam.task.twitter.domain.model.tweet;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.laczek.adam.task.twitter.api.controller.follow.FollowingCreation;
import pl.laczek.adam.task.twitter.api.controller.tweet.Tweet;
import pl.laczek.adam.task.twitter.domain.model.author.Author;
import pl.laczek.adam.task.twitter.domain.model.author.AuthorService;
import pl.laczek.adam.task.twitter.domain.model.follow.FollowingService;
import pl.laczek.adam.task.twitter.domain.ports.TweetRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TimelineService {
    private static final int MAX_PAGE_SIZE = 100;
    private static final Sort CREATE_TIME_DESC = Sort.by(Sort.Direction.DESC, "createTime");
    private final TweetMapper tweetMapper;
    private final TweetRepository tweetRepository;
    private final FollowingService followingService;
    private final AuthorService authorService;

    public Page<Tweet> getAllTweetsOfAuthor(Author user, Pageable controllerPageable) {
        var pageable = ensureOrdering(controllerPageable);
        authorService.validateIfAuthorExists(user.getUserId());
        return toTweetsPage(tweetRepository.findAllByAuthorId(user.getUserId(), pageable), pageable);
    }

    public Page<Tweet> getTweetsOnTimeline(Author user, Pageable controllerPageable) {
        var pageable = ensureOrdering(controllerPageable);
        Set<String> followedUsers = findFollowedUsers(user);
        return toTweetsPage(tweetRepository.findAllByAuthorIdIn(followedUsers, pageable), pageable);
    }

    private PageImpl<Tweet> toTweetsPage(Page<TweetJpa> page, Pageable pageable) {
        return new PageImpl<>(tweetMapper.toApi(page.getContent()), pageable, page.getTotalElements());
    }

    private Set<String> findFollowedUsers(Author user) {
        return followingService.getFollowings(user)
                .stream()
                .map(FollowingCreation::followedUserId)
                .collect(Collectors.toSet());
    }

    private Pageable ensureOrdering(Pageable fromController) {
        return PageRequest.of(fromController.getPageNumber(), pageSizeButLessThat100(fromController), CREATE_TIME_DESC);
    }

    private static int pageSizeButLessThat100(Pageable fromController) {
        return Math.min(fromController.getPageSize(), MAX_PAGE_SIZE);
    }
}
