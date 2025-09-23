package com.empsys.controller;

import com.empsys.entity.DocumentEntity;
import com.empsys.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/api/docs")
@CrossOrigin(origins = "http://localhost:3000")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    // ✅ Upload a document with title
    @PostMapping("/upload")
    public ResponseEntity<?> uploadDocument(
            @RequestParam("email") String email,
            @RequestParam("title") String title,
            @RequestParam("file") MultipartFile file) {

        try {
            DocumentEntity doc = new DocumentEntity();
            doc.setEmail(email);
            doc.setTitle(title);
            doc.setFileName(file.getOriginalFilename());
            doc.setFileData(file.getBytes());

            DocumentEntity saved = documentService.saveDocument(doc);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Upload failed: " + e.getMessage());
        }
    }

    // ✅ Fetch all uploaded documents for employee
    @GetMapping("/employee")
    public ResponseEntity<?> getDocumentsByEmail(@RequestParam String email) {
        List<DocumentEntity> docs = documentService.getDocumentsByEmail(email);
        List<Map<String, String>> response = new ArrayList<>();

        for (DocumentEntity doc : docs) {
            Map<String, String> item = new HashMap<>();
            item.put("title", doc.getTitle());
            item.put("fileName", doc.getFileName());
            response.add(item);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("documents", response);
        return ResponseEntity.ok(result);
    }

    // ✅ Download document by filename
    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable String fileName) {
        Optional<DocumentEntity> optionalDoc = documentService.getDocumentByFileName(fileName);
        if (optionalDoc.isPresent()) {
            DocumentEntity doc = optionalDoc.get();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + doc.getFileName() + "\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(doc.getFileData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
