package com.ecommerce.productservice.mapper;

import com.ecommerce.productservice.dto.TagDTO;
import com.ecommerce.productservice.model.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {
    private TagMapper() {}
    public static TagDTO toDTO(Tag tag) {
        return TagDTO.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }
    public static Tag toEntity(TagDTO dto) {
        Tag tag = new Tag();
        tag.setId(dto.getId());
        tag.setName(dto.getName());
        return tag;
    }
} 