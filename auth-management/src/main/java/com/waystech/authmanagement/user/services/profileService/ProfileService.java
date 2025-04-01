package org.partypal.userManagement.domain.services.profileService;

import org.partypal.userManagement.application.dto.PartyPalResponse;
import org.partypal.userManagement.application.dto.request.EditProfileRequest;
import org.partypal.userManagement.application.dto.response.ProfileResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ProfileService {
    PartyPalResponse<String> uploadProfile(MultipartFile image, String username, String location);

    PartyPalResponse<ProfileResponse> editProfile(MultipartFile image, String request);

    PartyPalResponse<ProfileResponse> getLoggedInProfile();

    PartyPalResponse<String> deleteAccount();
}
