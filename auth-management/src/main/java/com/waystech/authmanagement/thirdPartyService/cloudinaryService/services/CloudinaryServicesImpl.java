package org.partypal.thirdPartyService.cloudinaryService.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.partypal.commonModule.exceptions.classes.ImageUploadException;
import org.partypal.thirdPartyService.cloudinaryService.utils.ImageResizer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CloudinaryServicesImpl implements CloudinaryServices{

    private final Cloudinary cloudinary;
    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultGeneratedLink")
    @Override
    public String uploadAndGenerateLink(MultipartFile file, String id){
        int maximumBytes = 5 * 1024 * 1024;
        try {
            byte[] resizedImage = ImageResizer.resizeAndCompressImage(file, maximumBytes);
            cloudinary.uploader().upload(resizedImage, ObjectUtils.asMap("public_id", "image_id"+id));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return cloudinary.url().transformation(
                new Transformation<>().width(200).height(250).crop("fill"))
                .generate("image_id"+id);
    }

    public String getDefaultGeneratedLink(MultipartFile file, Long id, Exception e){
        throw new ImageUploadException("Unable To Upload Image");
    }
}
