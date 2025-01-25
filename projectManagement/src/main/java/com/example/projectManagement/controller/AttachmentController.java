package com.example.projectManagement.controller;

import com.example.projectManagement.model.entity.Attachment;
import com.example.projectManagement.service.impl.AttachmentServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/attachment")
public class AttachmentController {

    private final AttachmentServiceImpl attachmentService;

    public AttachmentController(AttachmentServiceImpl attachmentService) {
        this.attachmentService = attachmentService;
    }

    @GetMapping("/attachments")
    public ResponseEntity<List<Attachment>> findAll() {
        return new ResponseEntity<>(attachmentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/attachment/{id}")
    public ResponseEntity<Attachment> findByAll(@PathVariable Long id) {
        Attachment attachment = attachmentService.findById(id);
        if (attachment != null) {
            return new ResponseEntity<>(attachment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @PostMapping("/attachment")
//    public ResponseEntity<?> save(@RequestPart Attachment attachment, @RequestPart MultipartFile file) {
//        try {
//            Attachment attachment1 = attachmentService.save(attachment, file);
//            return new ResponseEntity<>(attachment1, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> save(@RequestPart("attachment") String attachmentJson, @RequestPart("file") MultipartFile file) {
        try {
            // تبدیل JSON به شیء `Attachment`
            ObjectMapper objectMapper = new ObjectMapper();
            Attachment attachment = objectMapper.readValue(attachmentJson, Attachment.class);

            // ذخیره فایل و اطلاعات دیگر
            Attachment savedAttachment = attachmentService.save(attachment, file);
            return new ResponseEntity<>(savedAttachment, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/attachment/{id}")
    public ResponseEntity<String> edit(@PathVariable Long id, @RequestPart Attachment attachment, @RequestPart MultipartFile file) {
        Attachment attachment1 = null;
        try {
            attachment1 = attachmentService.edit(id, attachment, file);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
        }
        if (attachment1 != null) {
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/attachment/{id}")
    public ResponseEntity<String> remove(@PathVariable Long id) {
        Attachment attachment = attachmentService.findById(id);
        if (attachment != null) {
            attachmentService.remove(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Attachment not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/attachment/{attachmentId}/file")
    public ResponseEntity<byte[]> getFileByAttachmentId(@PathVariable Long attachmentId) {
        Attachment attachment = attachmentService.findById(attachmentId);
        byte[] file = attachment.getContent();

        return ResponseEntity.ok().contentType(MediaType.valueOf((attachment.getFileType()))).body(file);
    }

}
