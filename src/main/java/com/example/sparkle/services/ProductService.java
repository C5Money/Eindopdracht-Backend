package com.example.sparkle.services;

import com.example.sparkle.dtos.inputDto.CustomerCardInputDto;
import com.example.sparkle.dtos.inputDto.ProductInputDto;
import com.example.sparkle.dtos.outputDto.CustomerCardOutputDto;
import com.example.sparkle.dtos.outputDto.ProductOutputDto;
import com.example.sparkle.models.CustomerCard;
import com.example.sparkle.models.Product;
import com.example.sparkle.repositories.CustomerCardRepository;
import com.example.sparkle.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
//    Instance Variables
    private final ProductRepository productRepository;
//    Constructor
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
//    CRUD:
//    ----------------------------------------------------------------------
//    Create
//    ----------------------------------------------------------------------
    public ProductInputDto createProduct(ProductInputDto productInputDto){
        productRepository.save(inputDtoToEntity(productInputDto));
        return productInputDto;
    }
//    ----------------------------------------------------------------------
//    Read
//    ----------------------------------------------------------------------
    public List<ProductOutputDto> readAllProducts(){
        List<Product> productList = productRepository.findAll();
        List<ProductOutputDto> productOutputDtoList = new ArrayList<>();
        for ( Product productEntity : productList){
            productOutputDtoList.add(entityToOutputDto(productEntity));
        }
        return productOutputDtoList;
    }
//    MAPPERS:
//    ----------------------------------------------------------------------
//    InputDto to Entity
//    ----------------------------------------------------------------------
    public Product inputDtoToEntity(ProductInputDto productInputDto){
        Product productEntity = new Product();
        productEntity.setId(productInputDto.id);
        productEntity.setProductName(productInputDto.productName);
        productEntity.setArticleNumber(productInputDto.articleNumber);
        productEntity.setPrice(productInputDto.price);
        productEntity.setAvailableStock(productInputDto.availableStock);
        productEntity.setCategory(productInputDto.category);
        return productEntity;
    }

//    ----------------------------------------------------------------------
//    Entity to OutputDto
//    ----------------------------------------------------------------------
    public ProductOutputDto entityToOutputDto(Product product){
        ProductOutputDto productOutputDto = new ProductOutputDto();
        productOutputDto.id = product.getId();
        productOutputDto.productName = product.getProductName();
        productOutputDto.price = product.getPrice();
        productOutputDto.availableStock = product.getAvailableStock();
        return productOutputDto;
    }



}
