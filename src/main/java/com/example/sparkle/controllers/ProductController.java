package com.example.sparkle.controllers;

import com.example.sparkle.dtos.inputDto.CustomerCardInputDto;
import com.example.sparkle.dtos.inputDto.ProductInputDto;
import com.example.sparkle.dtos.outputDto.CustomerCardOutputDto;
import com.example.sparkle.dtos.outputDto.ProductOutputDto;
import com.example.sparkle.services.CustomerCardService;
import com.example.sparkle.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ProductInputDto> createProduct(@RequestBody ProductInputDto productInputDto){
       ProductInputDto newProductDto = productService.createProduct(productInputDto);
       URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + newProductDto.id).toUriString());
       return ResponseEntity.created(uri).body(newProductDto);
    }
//    ----------------------------------------------------------------------
//    Get
//    ----------------------------------------------------------------------
//    @GetMapping("/{id}")
//    public ResponseEntity<CustomerCardOutputDto> readOneCustomerCard(@PathVariable Long id){
//
//    }
//
    @GetMapping
    public ResponseEntity<List<ProductOutputDto>> readAllCustomerCards(){
        List<ProductOutputDto> productDtoList = productService.readAllProducts();
        return ResponseEntity.ok().body(productDtoList);
    }
////    ----------------------------------------------------------------------
////    Put
////    ----------------------------------------------------------------------
//    @PutMapping("/{id}")
//    public ResponseEntity<CustomerCardOutputDto> updateOneCustomerCard(@RequestBody CustomerCardInputDto cardInputDto, @PathVariable Long id){
//
//    }
////    ----------------------------------------------------------------------
////    Delete
////    ----------------------------------------------------------------------
//    @DeleteMapping("/{id}")
//    public ResponseEntity<HttpStatus> deleteOneCustomerCard(@PathVariable Long id){
//
//    }
}
