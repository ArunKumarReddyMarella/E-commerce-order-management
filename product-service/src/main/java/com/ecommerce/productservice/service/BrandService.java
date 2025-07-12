package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.BrandDTO;
import com.ecommerce.productservice.dto.PageRequestDTO;
import com.ecommerce.productservice.mapper.BrandMapper;
import com.ecommerce.productservice.model.Brand;
import com.ecommerce.productservice.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;

    public BrandDTO createBrand(BrandDTO brandDTO) {
        Brand brand = BrandMapper.toEntity(brandDTO);
        Brand saved = brandRepository.save(brand);
        return BrandMapper.toDTO(saved);
    }

    public Optional<BrandDTO> getBrandById(Long id) {
        return brandRepository.findById(id).map(BrandMapper::toDTO);
    }

    public BrandDTO updateBrand(BrandDTO brandDTO) {
        Brand brand = BrandMapper.toEntity(brandDTO);
        Brand updated = brandRepository.save(brand);
        return BrandMapper.toDTO(updated);
    }

    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }

    public Page<BrandDTO> listBrands(PageRequestDTO pageRequestDTO) {
        Sort sort = Sort.by(Sort.Direction.fromString(pageRequestDTO.getSortDirection()), pageRequestDTO.getSortBy());
        PageRequest pageRequest = PageRequest.of(pageRequestDTO.getPageNumber(), pageRequestDTO.getPageSize(), sort);
        return brandRepository.findAll(pageRequest).map(BrandMapper::toDTO);
    }
} 