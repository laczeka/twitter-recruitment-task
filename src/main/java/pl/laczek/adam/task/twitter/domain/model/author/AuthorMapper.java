package pl.laczek.adam.task.twitter.domain.model.author;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.laczek.adam.task.twitter.infrastructure.mapstruct.MapperConfiguration;

@Mapper(config = MapperConfiguration.class)
public interface AuthorMapper {

    @Mapping(target = "id", source = "userId")
    @Mapping(target = "followings", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    AuthorJpa toJpa(Author author);

}
