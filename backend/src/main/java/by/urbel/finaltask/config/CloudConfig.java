package by.urbel.finaltask.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudConfig {
    @Value("${CLOUDINARY_CLOUD_NAME}")
    private String cloudName;
    @Value("${CLOUDINARY_API_KEY}")
    private String apiKey;
    @Value("${CLOUDINARY_API_SECRET}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinaryBean() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        return new Cloudinary(config);
    }
}
