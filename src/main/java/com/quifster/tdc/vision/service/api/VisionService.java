package com.quifster.tdc.vision.service.api;

import com.google.api.services.vision.v1.model.EntityAnnotation;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

/**
 * Interface for scanning images.
 */
public interface VisionService {

    List<EntityAnnotation> scanImage(byte[] image) throws GeneralSecurityException, IOException;
}
