package com.quifster.tdc.vision.service.api;

import com.google.api.services.vision.v1.model.EntityAnnotation;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

/**
 * Created by Iain on 9/26/2016.
 */
public interface VisionService {

    List<EntityAnnotation> scanImage() throws GeneralSecurityException, IOException;
}
