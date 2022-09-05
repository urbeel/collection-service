package by.urbel.finaltask.mapper;

import by.urbel.finaltask.domain.item.Item;
import by.urbel.finaltask.dto.requests.ItemRequest;
import by.urbel.finaltask.dto.response.ItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CommentMapper.class, TagMapper.class})
public interface ItemMapper {
    @Mapping(target = "name", source = "name")
    Item map(ItemRequest itemRequest);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "tags", expression = "java(item.getTags().stream().map(tag->tag.getName()).toList())")
    @Mapping(target = "collectionName", source = "collection.name")
    @Mapping(target = "author", source = "collection.owner.username")
    ItemResponse map(Item item);

    List<ItemResponse> map(List<Item> item);
}
