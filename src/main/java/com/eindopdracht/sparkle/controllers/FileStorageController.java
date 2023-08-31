package com.eindopdracht.sparkle.controllers;


import com.eindopdracht.sparkle.services.FileStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileStorageController {
//    Instance Variables
    private final FileStorageService fileStorageService;
//    Constructor
    public FileStorageController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }
//    MAPPINGS:
//    ----------------------------------------------------------------------
//    Post
//    ----------------------------------------------------------------------
    @PostMapping("/upload")
    public ResponseEntity<String> singleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        String fileUpload = fileStorageService.singlefileUpload(file);
        return ResponseEntity.status(HttpStatus.OK).body(fileUpload);
    }
//    ----------------------------------------------------------------------
//    Get
//    ----------------------------------------------------------------------
    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadSingleFile(@PathVariable String fileName){
        byte[] fileFromStorage = fileStorageService.singleFileDownload(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_PNG)
                .body(fileFromStorage);

    }
}
