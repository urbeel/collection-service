package by.urbel.finaltask.mapper;

import by.urbel.finaltask.domain.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {
    @Mapping(target = "name", source = "name")
    Tag TagToStrTag(String name);
    List<Tag> TagsToStrTags(List<String> name);
}
