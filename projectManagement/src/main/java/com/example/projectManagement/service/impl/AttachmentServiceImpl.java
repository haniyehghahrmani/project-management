package com.example.projectManagement.service.impl;

import com.example.projectManagement.model.entity.Attachment;
import com.example.projectManagement.repository.AttachmentRepository;
import com.example.projectManagement.service.AttachmentService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    @Override
    public Attachment save(Attachment attachment) {
        return attachmentRepository.save(attachment);
    }

    @Override
    public Attachment edit(Attachment attachment) {
        return attachmentRepository.save(attachment);
    }

    @Override
    public Attachment remove(String fileName) {
         Optional<Attachment> attachment =  attachmentRepository.findByFileName(fileName);
         if (attachment.isPresent())
             attachmentRepository.delete(attachment.get());
         return attachment.get();
    }

    @Override
    public Attachment findById(Long id) {
        return attachmentRepository.findById(id).get();
    }

    @Override
    public Attachment findByFileName(String fileName) {
        return attachmentRepository.findByFileName(fileName).get();
    }
}
