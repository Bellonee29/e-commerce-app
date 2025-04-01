package org.partypal.thirdPartyService.cloudinaryService.services;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface CloudinaryServices {
    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultGeneratedLink")
    String uploadAndGenerateLink(MultipartFile file, String id);
}
