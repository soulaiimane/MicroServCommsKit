package org.soulaimane.productservice.controller;

import lombok.AllArgsConstructor;
import org.soulaimane.productservice.dto.ProductRequestDto;
import org.soulaimane.productservice.dto.ProductResponseDto;
import org.soulaimane.productservice.model.Product;
import org.soulaimane.productservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
public class ProductController {
    ProductService productService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponseDto> getAllProducts(){
        return productService.getAllProducts() ;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct( @RequestBody ProductRequestDto productRequestDto){
        productService.addProduct(productRequestDto);

    }
    @GetMapping("/{name}")
    public List<Product> getProduct(@PathVariable String name){
        return productService.getProductByNam(name);

    }
}
