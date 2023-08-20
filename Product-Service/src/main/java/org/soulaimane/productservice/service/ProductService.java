package org.soulaimane.productservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.soulaimane.productservice.dto.ProductResponseDto;
import org.soulaimane.productservice.model.Product;
import org.soulaimane.productservice.repository.ProductRepository;
import org.soulaimane.productservice.dto.ProductRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ProductService {
    ProductRepository productRepository;
    public void addProduct( @RequestBody ProductRequestDto productRequestDto){
        Product product=new Product(UUID.randomUUID().toString(),productRequestDto.getName(),productRequestDto.getDescription()
        ,productRequestDto.getPrice());
        productRepository.save(product);
        log.info("Product ID={} was saved successfully",product.getId());
    }
    public List<ProductResponseDto> getAllProducts(){
        List<Product> products = productRepository.findAll();
        List<ProductResponseDto> productResponseDtoList = products.stream().map(product
                -> ToProductResponseDto(product)).collect(Collectors.toList());
        return productResponseDtoList;
    }

    private ProductResponseDto ToProductResponseDto(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .description(product.getDescription())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
    public List<Product> getProductByNam(String name) {
        return productRepository.getProductsByName(name);
    }

}
