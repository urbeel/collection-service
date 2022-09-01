package by.urbel.finaltask.controller;

import by.urbel.finaltask.service.CloudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload-file")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CloudController {
    private final CloudService cloudService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile imageFile){
        return ResponseEntity.ok(cloudService.uploadFileToCloud(imageFile));
    }
}
