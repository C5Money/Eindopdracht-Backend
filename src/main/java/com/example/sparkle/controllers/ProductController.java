package com.example.sparkle.controllers;

import com.example.sparkle.dtos.inputDto.ProductInputDto;
import com.example.sparkle.dtos.outputDto.ProductOutputDto;
import com.example.sparkle.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
//    Instance Variables
    private final ProductService productService;
//    Constructor
    public ProductController(ProductService productService){
        this.productService = productService;
    }
//    MAPPINGS:
//    ----------------------------------------------------------------------
//    Post
//    ----------------------------------------------------------------------
    @PostMapping
    public ResponseEntity<Object> createProduct(@Valid @RequestBody ProductInputDto productInputDto, BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            StringBuilder stringBuilder = new StringBuilder();
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                stringBuilder.append(fieldError.getField() + ": ");
                stringBuilder.append(fieldError.getDefaultMessage());
                stringBuilder.append("\n");
            }
            return ResponseEntity.badRequest().body(stringBuilder.toString());
        }
        Long newProductDto = productService.createProduct(productInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + newProductDto ).toUriString());
        productInputDto.articleNumber = newProductDto;
        return ResponseEntity.created(uri).body("Product id: " + productInputDto.articleNumber + " is successfully created.");
    }
//    ----------------------------------------------------------------------
//    Get
//    ----------------------------------------------------------------------
    @GetMapping("/{articleNumber}")
    public ResponseEntity<ProductOutputDto> readOneProductByArticleNumber(@PathVariable Long articleNumber){
        ProductOutputDto productOutputDto = productService.readOneProductByArticleNumber(articleNumber);
        return ResponseEntity.ok().body(productOutputDto);
    }

    @GetMapping("/productname/{productname}")
    public ResponseEntity<ProductOutputDto> readOneProductByName(@PathVariable String productname){
        ProductOutputDto productOutputDto = productService.readOneProductName(productname);
        return ResponseEntity.ok().body(productOutputDto);
    }

    @GetMapping
    public ResponseEntity<List<ProductOutputDto>> readAllProducts(){
        List<ProductOutputDto> productDtoList = productService.readAllProducts();
        return ResponseEntity.ok().body(productDtoList);
    }
//    ----------------------------------------------------------------------
//    Put
//    ----------------------------------------------------------------------
    @PutMapping("/{articleNumber}")
    public ResponseEntity<Object> updateOneProduct(@Valid @PathVariable Long articleNumber, @RequestBody ProductInputDto productInputDto, BindingResult bindingResult){
        ProductOutputDto productOutputDto = productService.updateOneProduct(articleNumber, productInputDto );
        if(bindingResult.hasFieldErrors()){
            StringBuilder stringBuilder = new StringBuilder();
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                stringBuilder.append(fieldError.getField() + ": ");
                stringBuilder.append(fieldError.getDefaultMessage());
                stringBuilder.append("\n");
            }
            return ResponseEntity.badRequest().body(stringBuilder.toString());
        }
        return ResponseEntity.ok().body(productOutputDto);
    }

    @PutMapping("/{articleNumber}/inventory/{inventoryId}")
    public ResponseEntity<String> assignProductToInventoryItem(@PathVariable Long articleNumber, @PathVariable Long inventoryId){
        String assignedProduct = productService.assignProductToInventoryItem(articleNumber, inventoryId);
        return ResponseEntity.ok().body(assignedProduct);
    }

//    @PutMapping("/{articleNumber}/cardnumber/{cardnumber}")
//    public ResponseEntity<String> assignProductToCustomerCard(@PathVariable Long articleNumber, @PathVariable Long cardnumber){
//        String assignedProduct = productService.assignProductToCustomerCard(articleNumber, cardnumber);
//        return ResponseEntity.ok().body(assignedProduct);
//    }
//    ----------------------------------------------------------------------
//    Delete
//    ----------------------------------------------------------------------
    @DeleteMapping("/{articleNumber}")
    public ResponseEntity<HttpStatus> deleteOneProductById(@PathVariable Long articleNumber){
        productService.deleteOneProductId(articleNumber);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}