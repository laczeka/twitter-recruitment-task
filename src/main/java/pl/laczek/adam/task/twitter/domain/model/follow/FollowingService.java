package pl.laczek.adam.task.twitter.domain.model.follow;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.laczek.adam.task.twitter.api.controller.follow.FollowingCreation;
import pl.laczek.adam.task.twitter.domain.model.author.Author;
import pl.laczek.adam.task.twitter.domain.model.author.AuthorService;
import pl.laczek.adam.task.twitter.domain.ports.FollowingRepository;

import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowingService {
    private final FollowingMapper followingMapper;
    private final FollowingRepository followingRepository;
    private final AuthorService authorService;


    @Transactional
    public void follow(FollowingCreation followingCreation) {
        var normalizedFollowing = normalize(followingCreation);
        FollowingJpa followingJpa = followingMapper.toJpa(normalizedFollowing);
        Preconditions.checkArgument(!followingRepository.exists(Example.of(followingJpa)), "User %s already follow %s", followingCreation.userId(), followingCreation.followedUserId());
        authorService.validateIfAuthorExists(normalizedFollowing.userId());
        authorService.validateIfAuthorExists(normalizedFollowing.followedUserId());
        followingRepository.save(followingJpa);
    }

    private static FollowingCreation normalize(FollowingCreation followingCreation) {
        return new FollowingCreation(Author.of(followingCreation.userId()).getUserId(), Author.of(followingCreation.followedUserId()).getUserId());
    }

    public Set<FollowingCreation> getFollowings(Author author) {
        var user = authorService.findAuthorOrThrow(author);
        return followingMapper.toApi(user.getFollowings());
    }
}
