package com.example.rotation.Rotation;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("/images")
public class RotationController {
    RotationService rotationService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            RotationEntity savedImage = rotationService.saveImages(file);
            return ResponseEntity.ok("Image processed and saved with ID: " + savedImage.getId());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing image.");
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        RotationEntity imageEntity = rotationService.getImage(id);
        if (imageEntity != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("image/" + imageEntity.getFormat()));
            headers.setContentLength(imageEntity.getModifiedImage().length);
            return ResponseEntity.ok().headers(headers).body(imageEntity.getModifiedImage());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}



