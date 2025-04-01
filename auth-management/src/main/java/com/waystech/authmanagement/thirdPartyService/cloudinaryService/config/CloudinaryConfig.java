package org.partypal.thirdPartyService.cloudinaryService.config;

import com.cloudinary.Cloudinary;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Data
public class CloudinaryConfig {
    @Value("${cloud.name}")
    private String name;

    @Value("${cloud.secret}")
    private String secret;

    @Value("${cloud.key}")
    private String key;

    @Bean
    public Cloudinary cloudinary(){
        Map<String, String> cloudConfig = new HashMap<>();
        cloudConfig.put("cloud_name", name);
        cloudConfig.put("api_secret", secret);
        cloudConfig.put("api_key", key);
        return new Cloudinary(cloudConfig);
    }
}
