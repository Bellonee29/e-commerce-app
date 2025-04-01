package com.waystech.authmanagement.integrations.cloudinaryService.utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
@Slf4j
public class ImageResizer {
    public static byte[] resizeAndCompressImage(MultipartFile file,  int maxSizeBytes) throws IOException {
        // Convert the MultipartFile to a byte array
        byte[] originalImageBytes = file.getBytes();
        double quality = 0.7;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        while (originalImageBytes.length > maxSizeBytes) {
            Thumbnails.of(file.getInputStream())
                    .outputQuality(quality)
                    .scale(1.0)
                    .toOutputStream(outputStream);
            originalImageBytes = outputStream.toByteArray();
            outputStream.reset();
        }

        return originalImageBytes;
    }
}

