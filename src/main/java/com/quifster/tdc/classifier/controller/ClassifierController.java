package com.quifster.tdc.classifier.controller;

import com.quifster.tdc.vision.service.api.VisionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for the classifier page.
 */
@Controller
@RequestMapping("/classifier")
public class ClassifierController {

    private final VisionService visionService;

    public ClassifierController(VisionService visionService) {
        this.visionService = visionService;
    }

    public void classify() {
        // Accept image file
        // Call Google Vision API
        // Throw away any results greater than 500 px outside
        // Check any Rectangle overlaps with templates
        // Highest percentage over 70% is considered a match
    }
}
