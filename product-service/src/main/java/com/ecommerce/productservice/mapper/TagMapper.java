package com.ecommerce.productservice.mapper;

import com.ecommerce.productservice.dto.TagDTO;
import com.ecommerce.productservice.model.Tag;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Tag and TagDTO objects.
 * Includes null checks to prevent NullPointerExceptions.
 */
@Component
public class TagMapper {

    private TagMapper() {
        // Private constructor to prevent instantiation
    }

    /**
     * Converts a Tag entity to a TagDTO.
     *
     * @param tag the Tag to convert, can be null
     * @return the converted TagDTO, or null if the input is null
     */
    public static TagDTO toDTO(Tag tag) {
        if (tag == null) {
            return null;
        }
        
        return TagDTO.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }

    /**
     * Converts a TagDTO to a Tag entity.
     *
     * @param dto the TagDTO to convert, can be null
     * @return the converted Tag, or null if the input is null
     */
    public static Tag toEntity(TagDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Tag tag = new Tag();
        if (dto.getId() != null) {
            tag.setId(dto.getId());
        }
        if (dto.getName() != null) {
            tag.setName(dto.getName());
        }
        return tag;
    }
}