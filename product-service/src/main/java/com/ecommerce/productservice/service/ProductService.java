package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.PageRequestDTO;
import com.ecommerce.productservice.dto.ProductDTO;
import com.ecommerce.productservice.mapper.ProductMapper;
import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.repository.CategoryRepository;
import com.ecommerce.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = ProductMapper.toEntity(productDTO);
        product.setCategory(categoryRepository.findById(productDTO.getCategoryId()).orElse(null));
        product.setBrand(productDTO.getBrand());
        Product saved = productRepository.save(product);
        return ProductMapper.toDTO(saved);
    }

    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id).map(ProductMapper::toDTO);
    }

    public ProductDTO updateProduct(ProductDTO productDTO) {
        Product product = ProductMapper.toEntity(productDTO);
        product.setCategory(categoryRepository.findById(productDTO.getCategoryId()).orElse(null));
        product.setBrand(productDTO.getBrand());
        Product updated = productRepository.save(product);
        return ProductMapper.toDTO(updated);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Page<ProductDTO> listProducts(PageRequestDTO pageRequestDTO) {
        Sort sort = Sort.by(Sort.Direction.fromString(pageRequestDTO.getSortDirection()), pageRequestDTO.getSortBy());
        PageRequest pageRequest = PageRequest.of(pageRequestDTO.getPageNumber(), pageRequestDTO.getPageSize(), sort);
        return productRepository.findAll(pageRequest).map(ProductMapper::toDTO);
    }
} 