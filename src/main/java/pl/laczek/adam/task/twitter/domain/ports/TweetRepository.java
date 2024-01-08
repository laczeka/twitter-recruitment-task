package pl.laczek.adam.task.twitter.domain.ports;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.laczek.adam.task.twitter.domain.model.tweet.TweetJpa;

import java.util.Set;

@Repository
public interface TweetRepository extends JpaRepository<TweetJpa, Long> {
    Page<TweetJpa> findAllByAuthorId(String postAuthor, Pageable pageable);

    Page<TweetJpa> findAllByAuthorIdIn(Set<String> postAuthors, Pageable pageable);

}
