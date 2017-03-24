package com.quifster.tdc.vision.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionScopes;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.quifster.tdc.vision.service.api.VisionService;
import com.quifster.tdc.vision.service.google.Feature;
import com.quifster.tdc.vision.service.google.FeatureEnum;
import com.quifster.tdc.vision.service.google.Image;
import com.quifster.tdc.vision.service.google.ImageRequest;
import com.quifster.tdc.vision.service.google.Request;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Google Vision Implementation.
 */
@Service
public class GoogleVisionImplementation implements VisionService {

  private static final String APPLICATION_NAME = "the-document-classifier";

  private final String apiKey;
  private final RestTemplate restTemplate;

  @Autowired
  public GoogleVisionImplementation(@Value("${google.cloud.vision.apikey}") String apiKey, RestTemplate restTemplate) {
    this.apiKey = apiKey;
    this.restTemplate = restTemplate;
  }

  public List<EntityAnnotation> scanImage(byte[] inputImage) throws GeneralSecurityException, IOException {
//        Vision vision = authenticate();
//        AnnotateImageRequest request = new AnnotateImageRequest()
//                .setImage(new Image().encodeContent(image))
//                .setFeatures(ImmutableList.of(
//                        new Feature().setType("TEXT_DETECTION")
//                ));
//        Vision.Image.Annotate annotate = vision.images().annotate(
//                new BatchAnnotateImagesRequest().setRequests(ImmutableList.of(request))
//        );
//        annotate.setDisableGZipContent(true);
//
//        BatchAnnotateImagesResponse batchResponse = annotate.execute();
//        AnnotateImageResponse response = batchResponse.getResponses().get(0);
//        return response.getTextAnnotations();

    Request request = new Request();
    ImageRequest imageRequest = new ImageRequest();
    com.quifster.tdc.vision.service.google.Image image = new Image();
    image.setContent(Base64.getEncoder().encodeToString(inputImage));
    imageRequest.setImage(image);

    Feature feature = new Feature();
    feature.setType(FeatureEnum.TEXT_DETECTION);
    imageRequest.setFeatures(Collections.singletonList(feature));
    request.setRequests(Collections.singletonList(imageRequest));

    String result = restTemplate.postForObject("https://vision.googleapis.com/v1/images:annotate?key=" + apiKey, request, String.class);
    return new ArrayList<>();
  }

  private Vision authenticate() throws IOException, GeneralSecurityException {
    GoogleCredential credential = GoogleCredential.getApplicationDefault().createScoped(VisionScopes.all());
    JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
    return new Vision.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, credential)
        .setApplicationName(APPLICATION_NAME)
        .build();
  }
}
