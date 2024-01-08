package pl.laczek.adam.task.twitter.infrastructure.mapstruct;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MapperConfiguration {
}
