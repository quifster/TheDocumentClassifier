package com.quifster.tdc.classifier.controller;

import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.quifster.tdc.vision.service.api.VisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

/**
 * Controller for the classifier page.
 */
@RestController
@RequestMapping("/classifier")
public class ClassifierController {

    private final VisionService visionService;

    @Autowired
    public ClassifierController(VisionService visionService) {
        this.visionService = visionService;
    }

    @GetMapping
    public ResponseEntity<List<EntityAnnotation>> classify() throws IOException, GeneralSecurityException {
        // Accept image file
        String file = "Sample.jpg";
        BufferedInputStream in = new BufferedInputStream(new FileInputStream("C:\\Scans\\" + file));
        byte[] bytes = new byte[in.available()];
        in.read(bytes);
        in.close();

        // Call Google Vision API
        List<EntityAnnotation> result = visionService.scanImage(bytes);

        // Throw away any results greater than 500 px outside
        // Check any Rectangle overlaps with templates
        // Highest percentage over 70% (configurable) is considered a match
        return ResponseEntity.ok(result);
    }
}
