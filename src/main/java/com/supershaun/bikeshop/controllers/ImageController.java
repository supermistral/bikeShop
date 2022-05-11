package com.supershaun.bikeshop.controllers;

import com.supershaun.bikeshop.services.interfaces.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Controller
@RequestMapping("/media")
@CrossOrigin
public class ImageController {
    @Autowired
    private IImageService imageService;

    @GetMapping(value = "/**", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    public ResponseEntity<byte[]> getImage(HttpServletRequest request) {
        String requestUrl = request.getRequestURL().toString();
        String filename = requestUrl.split("/media/")[1];

        byte[] image;

        try {
            image = imageService.findByName(filename);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        String[] dividedFilename = filename.split("\\.");

        if (dividedFilename.length == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        String imageFormat = dividedFilename[dividedFilename.length - 1];
        MediaType mediaType;

        switch (imageFormat) {
            case "png":
                mediaType = MediaType.IMAGE_PNG;
                break;
            default:
                mediaType = MediaType.IMAGE_JPEG;
        }

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(image);
    }
}
