package com.example.sparkle.services;

import com.example.sparkle.dtos.inputDto.CustomerCardInputDto;
import com.example.sparkle.dtos.inputDto.ProductInputDto;
import com.example.sparkle.dtos.outputDto.CustomerCardOutputDto;
import com.example.sparkle.dtos.outputDto.ProductOutputDto;
import com.example.sparkle.exceptions.ResourceNotFoundException;
import com.example.sparkle.models.CustomerCard;
import com.example.sparkle.models.Product;
import com.example.sparkle.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Long createProduct(ProductInputDto productInputDto){
        Product newProductEntity = inputDtoToEntity(productInputDto);
        productRepository.save(newProductEntity);
        return newProductEntity.getId();
    }
//    ----------------------------------------------------------------------
//    Read
//    ----------------------------------------------------------------------
    public ProductOutputDto readOneProductId(Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty() || id <= 0){
            throw new ResourceNotFoundException("This id: " + id + " is invalid or doesn't exist.");
        }
        return entityToOutputDto(optionalProduct.get());
    }

    public ProductOutputDto readOneProductName(String productName){
        Product foundProduct = productRepository.findByProductNameContainingIgnoreCase(productName).orElseThrow(()-> new ResourceNotFoundException("Product not found."));
        return entityToOutputDto(foundProduct);
    }

    public List<ProductOutputDto> readAllProducts(){
        List<Product> optionalProductList = productRepository.findAll();
        List<ProductOutputDto> productOutputDtoList = new ArrayList<>();
        if(optionalProductList.isEmpty()){
            throw new ResourceNotFoundException("Products not found.");
        } else {
            for ( Product productEntity : optionalProductList){
                productOutputDtoList.add(entityToOutputDto(productEntity));
            }
        }
        return productOutputDtoList;
    }
//    ----------------------------------------------------------------------
//    Update
//    ----------------------------------------------------------------------
    public ProductOutputDto updateOneProduct(ProductInputDto productInputDto, Long id){
        Product optionalProduct = productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("This id: " + id + " does not exist."));
        Product updatedProduct = updateInputDtoToEntity(productInputDto, optionalProduct);
        productRepository.save(updatedProduct);
        return entityToOutputDto(updatedProduct);
    }
//    ----------------------------------------------------------------------
//    Delete
//    ----------------------------------------------------------------------
    public void deleteOneProductId(Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty() || id <= 0){
            throw new ResourceNotFoundException("This product: " + id + " is already deleted or doesn't exist.");
        }
        productRepository.deleteById(id);
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

    public Product updateInputDtoToEntity(ProductInputDto productInputDto, Product productEntity){
        if(productInputDto.id != null){
            productEntity.setId(productInputDto.id);
        }
        if(productInputDto.productName != null){
            productEntity.setProductName(productInputDto.productName);
        }
        if(productInputDto.articleNumber != null){
            productEntity.setArticleNumber(productInputDto.articleNumber);
        }
        if(productInputDto.price != null){
            productEntity.setPrice(productInputDto.price);
        }
        if(productInputDto.availableStock != null){
            productEntity.setAvailableStock(productInputDto.availableStock);
        }
        if(productInputDto.category != null){
            productEntity.setCategory(productInputDto.category);
        }
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
