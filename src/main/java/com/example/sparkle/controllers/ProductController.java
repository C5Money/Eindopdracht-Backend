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
        productInputDto.id = newProductDto;
        return ResponseEntity.created(uri).body(productInputDto);
    }
//    ----------------------------------------------------------------------
//    Get
//    ----------------------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<ProductOutputDto> readOneProductById(@PathVariable Long id){
        ProductOutputDto productOutputDto = productService.readOneProductId(id);
        return ResponseEntity.ok().body(productOutputDto);
    }

    @GetMapping("/search")
    public ResponseEntity<ProductOutputDto> readOneProductByName(@RequestParam String productname){
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
    @PutMapping("/{id}")
    public ResponseEntity<ProductOutputDto> updateOneProduct(@RequestBody ProductInputDto productInputDto, @PathVariable Long id){
        ProductOutputDto productOutputDto = productService.updateOneProduct(productInputDto, id);
        return ResponseEntity.accepted().body(productOutputDto);
    }
//    ----------------------------------------------------------------------
//    Delete
//    ----------------------------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOneProductById(@PathVariable Long id){
        productService.deleteOneProductId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
