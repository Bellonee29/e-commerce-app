package org.partypal.userManagement.domain.services.profileService;


import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.partypal.commonModule.exceptions.classes.InvalidCredentialsException;
import org.partypal.commonModule.exceptions.classes.ProfileUpdateException;
import org.partypal.commonModule.exceptions.classes.UserAlreadyExistException;
import org.partypal.commonModule.utils.DateUtils;
import org.partypal.commonModule.utils.UserUtils;
import org.partypal.thirdPartyService.cloudinaryService.services.CloudinaryServices;
import org.partypal.userManagement.application.dto.PartyPalResponse;
import org.partypal.userManagement.application.dto.request.EditProfileRequest;
import org.partypal.userManagement.application.dto.response.ProfileResponse;
import org.partypal.userManagement.domain.models.User;
import org.partypal.userManagement.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements ProfileService{
    private final UserRepository userRepository;
    private final CloudinaryServices cloudinaryServices;
    private final UserUtils userUtils;
    public static final Gson gson = new Gson();

    @Override
    public PartyPalResponse<String> uploadProfile(MultipartFile image, String username, String location){
        User user = userUtils.getUserFromContext();
        String imageUrl;
        if(image != null){
            imageUrl = cloudinaryServices.uploadAndGenerateLink(image, user.getUserId());
        }else{
            imageUrl ="";
        }
        user.setUsername(username);
        user.setImageUrl(imageUrl);
        user.setLocation(location);
        User theUser = userRepository.save(user);
        if(theUser.getUsername() != null){
            return new PartyPalResponse<>("Request Processed", "Profile Updated Successfully");
        }else{
            throw new ProfileUpdateException("Error encountered, Please Try again");
        }
    }

    @Override
    public PartyPalResponse<ProfileResponse> editProfile(MultipartFile image, String details){
        EditProfileRequest request;
        try{
            request = gson.fromJson(details, EditProfileRequest.class);
        }catch(Exception e) {
            e.printStackTrace();
            throw new InvalidCredentialsException("Invalid Details Schema");
        }
        if(userRepository.existsByUsername(request.getUsername())){
            throw new UserAlreadyExistException("Username Already Exists, Pick a new one");
        }
        User user = userUtils.getUserFromContext();
        String imageUrl;
        if(image != null){
            imageUrl = cloudinaryServices.uploadAndGenerateLink(image, user.getUserId());
        }else{
            imageUrl ="";
        }
        user.setUsername(request.getUsername());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setLocation(request.getLocation());
        user.setImageUrl(imageUrl);
        User theUser = userRepository.save(user);
        return new PartyPalResponse<>("Profile Updated Successfully", new ProfileResponse(theUser));
    }

    @Override
    public PartyPalResponse<ProfileResponse> getLoggedInProfile(){
        User user = userUtils.getUserFromContext();
        return new PartyPalResponse<>("Profile Updated Successfully", new ProfileResponse(user));
    }

    @Override
    public PartyPalResponse<String> deleteAccount(){
        User user = userUtils.getUserFromContext();
        userRepository.delete(user);
        return new PartyPalResponse<>
                ("Request Processed", "Account with email: "+user.getEmail()+" is deleted successfully");
    }

}
