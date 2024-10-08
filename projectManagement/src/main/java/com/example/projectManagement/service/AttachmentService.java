package com.example.projectManagement.service;

import com.example.projectManagement.model.entity.Attachment;

public interface AttachmentService {

    Attachment save(Attachment attachment);

    Attachment edit(Attachment attachment);

    Attachment remove(String fileName);

    Attachment findById(Long id);

    Attachment findByFileName(String fileName);

}
