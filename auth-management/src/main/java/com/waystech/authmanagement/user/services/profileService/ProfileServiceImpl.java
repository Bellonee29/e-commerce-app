package com.waystech.authmanagement.user.services.profileService;


import com.google.gson.Gson;
import com.waystech.authmanagement.Utils.UserUtils;
import com.waystech.authmanagement.exceptions.classes.InvalidCredentialsException;
import com.waystech.authmanagement.exceptions.classes.ProfileUpdateException;
import com.waystech.authmanagement.exceptions.classes.UserAlreadyExistException;
import com.waystech.authmanagement.integrations.cloudinaryService.services.CloudinaryServices;
import com.waystech.authmanagement.user.dto.NovaResponse;
import com.waystech.authmanagement.user.dto.request.EditProfileRequest;
import com.waystech.authmanagement.user.dto.response.ProfileResponse;
import com.waystech.authmanagement.user.models.User;
import com.waystech.authmanagement.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public NovaResponse<String> uploadProfile(MultipartFile image, String username, String location){
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
            return new NovaResponse<>("Request Processed", "Profile Updated Successfully");
        }else{
            throw new ProfileUpdateException("Error encountered, Please Try again");
        }
    }

    @Override
    public NovaResponse<ProfileResponse> editProfile(MultipartFile image, String details){
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
        return new NovaResponse<>("Profile Updated Successfully", new ProfileResponse(theUser));
    }

    @Override
    public NovaResponse<ProfileResponse> getLoggedInProfile(){
        User user = userUtils.getUserFromContext();
        return new NovaResponse<>("Profile Updated Successfully", new ProfileResponse(user));
    }

    @Override
    public NovaResponse<String> deleteAccount(){
        User user = userUtils.getUserFromContext();
        userRepository.delete(user);
        return new NovaResponse<>
                ("Request Processed", "Account with email: "+user.getEmail()+" is deleted successfully");
    }

}
