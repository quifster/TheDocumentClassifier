package com.quifster.tdc.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Custom application properties.
 */
@Data
@ConfigurationProperties(prefix = "app")
public class ApplicationProperties {
    private Vision vision = new Vision();

    @Data
    public static class Vision {
        private Authorization authorization = new Authorization();

        @Data
        public static class Authorization {
            /**
             * The access token used to call the Google Vision API.
             */
            private String accessToken;
        }
    }
}
