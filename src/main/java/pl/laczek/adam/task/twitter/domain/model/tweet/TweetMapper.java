package pl.laczek.adam.task.twitter.domain.model.tweet;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.laczek.adam.task.twitter.api.controller.tweet.Tweet;
import pl.laczek.adam.task.twitter.api.controller.tweet.TweetCreation;
import pl.laczek.adam.task.twitter.infrastructure.mapstruct.MapperConfiguration;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface TweetMapper {

    Tweet toApi(TweetJpa tweetJpa);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    TweetJpa toJpa(TweetCreation tweetCreation);

    List<Tweet> toApi(List<TweetJpa> tweets);

}
