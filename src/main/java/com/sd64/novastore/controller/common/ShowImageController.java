package com.sd64.novastore.controller.common;

import com.sd64.novastore.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("image")
@RequiredArgsConstructor
public class ShowImageController {
    private final ImageService imageService;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer id) throws IOException {
        byte[] imageData = imageService.get(id);
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(imageData);
    }
}
