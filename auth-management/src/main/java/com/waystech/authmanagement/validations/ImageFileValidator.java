package org.partypal.commonModule.validations;

import org.partypal.commonModule.exceptions.classes.CustomValidationException;
import org.springframework.web.multipart.MultipartFile;

public class ImageFileValidator {

    public static void isValid(MultipartFile file) {
        if (file != null) {
            // Check if the content type indicates an image
            String contentType = file.getContentType();
            String originalFilename = file.getOriginalFilename();
            byte[] imageBytes;
            try{
                imageBytes = file.getBytes();
            }catch(Exception e){
                throw new CustomValidationException("Invalid Image file");
            }
            if (contentType != null && !contentType.startsWith("image/")) {
                throw new CustomValidationException("Invalid Image file");
            }else if (originalFilename != null) {
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
                // You can specify the allowed image file extensions
                if(!fileExtension.matches("(.jpeg|.jpg|.png|.gif|.bmp|.webp)")){
                    throw new CustomValidationException("Invalid Image file");
                };
            }else if(imageBytes.length > 10*1024*1024){
                throw new CustomValidationException("Image size should not be greater than 10MB");
            }
        }

    }

}
