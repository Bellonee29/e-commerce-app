package com.waystech.authmanagement.user.services.profileService;

import com.waystech.authmanagement.user.dto.NovaResponse;
import com.waystech.authmanagement.user.dto.response.ProfileResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ProfileService {
    NovaResponse<String> uploadProfile(MultipartFile image, String username, String location);

    NovaResponse<ProfileResponse> editProfile(MultipartFile image, String request);

    NovaResponse<ProfileResponse> getLoggedInProfile();

    NovaResponse<String> deleteAccount();
}
