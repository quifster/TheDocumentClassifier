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

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

/**
 * Created by Iain on 9/28/2016.
 */
public class GoogleVisionImplementation implements VisionService {

    private static final String APPLICATION_NAME = "the-document-classifier";

    @Override
    public List<EntityAnnotation> scanImage() throws GeneralSecurityException, IOException {
        Vision vision = authenticate();
        AnnotateImageRequest request = new AnnotateImageRequest()
                .setImage(new Image().encodeContent(new byte[] {}))
                .setFeatures(ImmutableList.of(
                        new Feature().setType("TEXT_DETECTION")
                )); // TODO - Image byte
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
