package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.TagDTO;
import com.ecommerce.productservice.mapper.TagMapper;
import com.ecommerce.productservice.model.Tag;
import com.ecommerce.productservice.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public TagDTO createTag(TagDTO dto) {
        Tag tag = TagMapper.toEntity(dto);
        Tag saved = tagRepository.save(tag);
        return TagMapper.toDTO(saved);
    }

    public Optional<TagDTO> getTagById(Long id) {
        return tagRepository.findById(id).map(TagMapper::toDTO);
    }

    public TagDTO updateTag(TagDTO dto) {
        Tag tag = TagMapper.toEntity(dto);
        Tag updated = tagRepository.save(tag);
        return TagMapper.toDTO(updated);
    }

    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    public List<TagDTO> listTags() {
        return tagRepository.findAll().stream().map(TagMapper::toDTO).collect(Collectors.toList());
    }
} 