package com.waystech.authmanagement.validations;

import com.waystech.authmanagement.exceptions.classes.UserUnauthorizedException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Slf4j
@Component
public class WishlistValidation {
    @Value("${company.name}")
    private String api_key;
    @Value("${application.access-key}")
    private String access_key;
    public void validateWishlistHeaders(String apiKey, String accessKey){
        log.info("System keys, api-key: {}, access-key: {}",api_key,access_key);
        if(apiKey == null){
            throw new UserUnauthorizedException("Authentication header missing");
        }else if(!apiKey.equals(api_key)){
            throw new UserUnauthorizedException("Check request headers");
        }
        if(accessKey == null){
            throw new UserUnauthorizedException("Authentication header missing");
        }else if(!accessKey.equals(access_key)){
            throw new UserUnauthorizedException("Check request headers");
        }
    }
}
