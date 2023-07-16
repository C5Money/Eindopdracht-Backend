package com.example.sparkle.services;

import com.example.sparkle.dtos.inputDto.CustomerCardInputDto;
import com.example.sparkle.dtos.inputDto.ProductInputDto;
import com.example.sparkle.dtos.outputDto.ProductOutputDto;
import com.example.sparkle.exceptions.ResourceNotFoundException;
import com.example.sparkle.models.CustomerCard;
import com.example.sparkle.models.Product;
import com.example.sparkle.repositories.CustomerCardRepository;
import com.example.sparkle.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
//    Instance Variables
    private final ProductRepository productRepository;
    private final CustomerCardRepository customerCardRepository;
//    private final InventoryRepository inventoryRepository;
//    Constructor
    public ProductService(ProductRepository productRepository, CustomerCardRepository customerCardRepository) {
        this.productRepository = productRepository;
        this.customerCardRepository = customerCardRepository;
//        this.inventoryRepository = inventoryRepository;
    }
//    CRUD:
//    ----------------------------------------------------------------------
//    Create
//    ----------------------------------------------------------------------
    public Long createProductLinkedToCustomerCard(ProductInputDto productInputDto){
        Optional<Product> optionalProduct = productRepository.findByArticleNumberContainingIgnoreCase(productInputDto.articleNumber);
        if(optionalProduct.isPresent()){
            throw new ResourceNotFoundException("Product article number: " + productInputDto.articleNumber + " already exists.");
        }
        Product newProductEntity = inputDtoToEntity(productInputDto);

        Optional<CustomerCard> optionalCustomerCard = customerCardRepository.findById(productInputDto.customerCardId);
        if(optionalCustomerCard.isEmpty()){
            throw new ResourceNotFoundException("Customercard id: " + productInputDto.customerCardId + " doesn't exist or is invalid.");
        } else {
            newProductEntity.setCustomerCard(optionalCustomerCard.get());
        }

        productRepository.save(newProductEntity);
        return newProductEntity.getCustomerCard().getId();

//        Optional<Inventory> optionalInventoryItem = inventoryRepository.findById(productInputDto.inventoryItemId);
//        if( optionalInventoryItem.isEmpty() ){
//            throw new ResourceNotFoundException("This inventory item is invalid or doesn't exist.");
//        } else {
//            newProductEntity.setInventoryItem(optionalInventoryItem.get());
//        }
    }

//    ----------------------------------------------------------------------
//    Read
//    ----------------------------------------------------------------------
    public ProductOutputDto readOneProductId(Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            throw new ResourceNotFoundException("Product id: " + id + " is invalid or doesn't exist.");
        }
        return entityToOutputDto(optionalProduct.get());
    }

    public ProductOutputDto readOneProductName(String productname){
        Product foundProduct = productRepository.findByProductNameContainingIgnoreCase(productname).orElseThrow(()-> new ResourceNotFoundException("Product not found."));
        return entityToOutputDto(foundProduct);
    }

    public ProductOutputDto readOneProductByArticleNumber(String articlenumber){
        Optional<Product> optionalProduct = productRepository.findByArticleNumberContainingIgnoreCase(articlenumber);
        if(optionalProduct.isEmpty()){
            throw new ResourceNotFoundException("Product article number: " + articlenumber + " is invalid or doesn't exist.");
        }
        return entityToOutputDto(optionalProduct.get());
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
    public ProductOutputDto updateOneProduct(Long id, ProductInputDto productInputDto ){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent() ){
            Product updatableProduct = optionalProduct.get();
            Product updatedProduct = updateInputDtoToEntity(productInputDto, updatableProduct);

            productRepository.save(updatedProduct);
            return entityToOutputDto(updatedProduct);
        } else {
            throw new ResourceNotFoundException("Product with id number: " + productInputDto.id + " did not update.");
        }
    }


//    ----------------------------------------------------------------------
//    Delete
//    ----------------------------------------------------------------------
    public void deleteOneProductId(Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty() ){
            throw new ResourceNotFoundException("This product with id: " + id + " is already deleted or doesn't exist.");
        }
        Product foundProduct = optionalProduct.get();
        productRepository.delete(foundProduct);
    }


//    MAPPERS:
//    ----------------------------------------------------------------------
//    InputDto to Entity
//    ----------------------------------------------------------------------
    public Product inputDtoToEntity(ProductInputDto productInputDto){
        Product productEntity = new Product();
        if(productInputDto.productName != null){
            productEntity.setProductName(productInputDto.productName);
        }

        if(productInputDto.articleNumber != null){
            productEntity.setArticleNumber(productInputDto.articleNumber);
        }

        if(productInputDto.unitPrice != null) {
            productEntity.setUnitPrice(productInputDto.unitPrice);
        }

        if(productInputDto.availableStock != null) {
            productEntity.setAvailableStock(productInputDto.availableStock);
        }

        if(productInputDto.category != null){
            productEntity.setCategory(productInputDto.category);
        }

//        if(productInputDto.customerCardId != null){
//            productEntity.setCustomerCard(productEntity.getCustomerCard());
//        }

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

        if(productInputDto.unitPrice != null) {
            productEntity.setUnitPrice(productInputDto.unitPrice);
        }

        if(productInputDto.availableStock != null) {
            productEntity.setAvailableStock(productInputDto.availableStock);
        }

        if(productInputDto.category != null){
            productEntity.setCategory(productInputDto.category);
        }

//        if(productInputDto. != null){
//            productEntity.setCustomerCard(productInputDto.customerCardId);
//        }

        return productEntity;
    }
//    ----------------------------------------------------------------------
//    Entity to OutputDto
//    ----------------------------------------------------------------------
    public ProductOutputDto entityToOutputDto(Product product){
        ProductOutputDto productOutputDto = new ProductOutputDto();
        productOutputDto.id = product.getId();
        productOutputDto.productName = product.getProductName();
        productOutputDto.unitPrice = product.getUnitPrice();
        productOutputDto.availableStock = product.getAvailableStock();
        return productOutputDto;
    }
}
