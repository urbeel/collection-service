package by.urbel.finaltask.mapper;

import by.urbel.finaltask.domain.Collection;
import by.urbel.finaltask.dto.requests.CollectionRequest;
import by.urbel.finaltask.dto.response.CollectionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CollectionMapper {
    @Mapping(target = "name", source = "name")
    Collection map(CollectionRequest collectionRequest);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "author", source = "owner.username")
    CollectionResponse map(Collection collection);

    List<CollectionResponse> map(List<Collection> collections);

}
