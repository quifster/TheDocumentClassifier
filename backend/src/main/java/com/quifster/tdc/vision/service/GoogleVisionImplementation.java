package com.quifster.tdc.vision.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionScopes;
import com.google.api.services.vision.v1.model.*;
import com.google.common.collect.ImmutableList;
import com.quifster.tdc.vision.service.api.VisionService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

/**
 * Google Vision Implementation.
 */
@Service
public class GoogleVisionImplementation implements VisionService {

    private static final String APPLICATION_NAME = "the-document-classifier";

    public List<EntityAnnotation> scanImage(byte[] image) throws GeneralSecurityException, IOException {
        Vision vision = authenticate();
        AnnotateImageRequest request = new AnnotateImageRequest()
                .setImage(new Image().encodeContent(image))
                .setFeatures(ImmutableList.of(
                        new Feature().setType("TEXT_DETECTION")
                ));
        Vision.Images.Annotate annotate = vision.images().annotate(
                new BatchAnnotateImagesRequest().setRequests(ImmutableList.of(request))
        );
        annotate.setDisableGZipContent(true);

        BatchAnnotateImagesResponse batchResponse = annotate.execute();
        AnnotateImageResponse response = batchResponse.getResponses().get(0);
        return response.getTextAnnotations();
    }

    private Vision authenticate() throws IOException, GeneralSecurityException {
        GoogleCredential credential = GoogleCredential.getApplicationDefault().createScoped(VisionScopes.all());
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        return new Vision.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
