package pl.laczek.adam.task.twitter.domain.model.follow;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.laczek.adam.task.twitter.api.controller.follow.FollowingCreation;
import pl.laczek.adam.task.twitter.infrastructure.mapstruct.MapperConfiguration;

import java.util.Set;

@Mapper(config = MapperConfiguration.class)
public interface FollowingMapper {

    FollowingCreation toApi(FollowingJpa followingJpa);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    FollowingJpa toJpa(FollowingCreation followingCreation);

    Set<FollowingCreation> toApi(Set<FollowingJpa> followings);

}
