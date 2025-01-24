package com.example.projectManagement.service;

import com.example.projectManagement.model.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AttachmentService {

    Attachment save(Attachment attachment, MultipartFile file) throws IOException;

    Attachment edit(Long id, Attachment attachment, MultipartFile file) throws IOException;

    void remove(Long id);

    List<Attachment> findAll();

    Attachment findById(Long id);

    Attachment findByFileName(String fileName);

}
