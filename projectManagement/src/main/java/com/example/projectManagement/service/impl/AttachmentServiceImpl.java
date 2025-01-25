package com.example.projectManagement.service.impl;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Attachment;
import com.example.projectManagement.repository.AttachmentRepository;
import com.example.projectManagement.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;

    @Autowired
    public AttachmentServiceImpl(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    @Override
    public Attachment save(Attachment attachment, MultipartFile file) throws IOException {
        attachment.setFileName(file.getOriginalFilename());
        attachment.setFileType(file.getContentType());
        attachment.setContent(file.getBytes());

        return attachmentRepository.save(attachment);
    }

    @Override
    public Attachment edit(Long id, Attachment attachment, MultipartFile file) throws IOException, NoContentException {
        Attachment existingAttachment=attachmentRepository.findById(id)
                .orElseThrow(
                        () -> new NoContentException("No Active Attachment Was Found with id " + id + " To Update!")
                );
        existingAttachment.setFileName(file.getOriginalFilename());
        existingAttachment.setFileType(file.getContentType());
        existingAttachment.setContent(file.getBytes());
        existingAttachment.setCaption(attachment.getCaption());

        return attachmentRepository.saveAndFlush(existingAttachment);
    }

    @Override
    public void remove(Long id) {
        attachmentRepository.deleteById(id);
    }

    @Override
    public List<Attachment> findAll() {
        return attachmentRepository.findAll();
    }

    @Override
    public Attachment findById(Long id) {
        return attachmentRepository.findById(id).orElse(null);
    }

    @Override
    public Attachment findByFileName(String fileName) {
        return attachmentRepository.findByFileName(fileName).orElse(null);
    }

}
