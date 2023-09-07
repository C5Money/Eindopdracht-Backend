package com.eindopdracht.sparkle.services;

import com.eindopdracht.sparkle.exceptions.ResourceNotFoundException;
import com.eindopdracht.sparkle.models.FileStorage;
import com.eindopdracht.sparkle.repositories.FileStorageRepository;
import com.eindopdracht.sparkle.utils.FileUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Optional;

@Service
public class FileStorageService {
//    Instance Variables
    private final FileStorageRepository fileStorageRepository;
//    Constructor
    public FileStorageService(FileStorageRepository fileStorageRepository) {
        this.fileStorageRepository = fileStorageRepository;
    }
//    ----------------------------------------------------------------------
//    Upload
//    ----------------------------------------------------------------------
    public String singlefileUpload(MultipartFile file) throws IOException {
        FileStorage uploadFile = new FileStorage();
        uploadFile.setFileName(file.getOriginalFilename());
        uploadFile.setDocFile(FileUtil.compressFile(file.getBytes()));

        fileStorageRepository.save(uploadFile);
        if(uploadFile != null){
            return "File uploaded succesfully: " + file.getOriginalFilename();
        }
        return null;
    }
//    ----------------------------------------------------------------------
//    Download
//    ----------------------------------------------------------------------
    @Transactional
    public byte[] singleFileDownload(String fileName){
        Optional<FileStorage> optionalFileName = fileStorageRepository.findByFileName(fileName);
        if(optionalFileName.isEmpty()){
            throw new ResourceNotFoundException("File not found.");
        }


        return FileUtil.decompressFile(optionalFileName.get().getDocFile());
    }
}