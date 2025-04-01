package com.waystech.authmanagement.integrations.googlePlacesServices.application.controller;

import com.waystech.authmanagement.integrations.googlePlacesServices.application.dto.request.NearbyPlacesRequest;
import com.waystech.authmanagement.integrations.googlePlacesServices.application.dto.response.GeoResponse;
import com.waystech.authmanagement.integrations.googlePlacesServices.domain.services.PlacesServices;
import com.waystech.authmanagement.user.dto.NovaResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("places")
@RequiredArgsConstructor
@Slf4j
public class PlacesController {
    private final PlacesServices placesServices;
    @PostMapping("get-nearby-places")
    public ResponseEntity<NovaResponse<GeoResponse>> getNearByPlaces(@Valid @RequestBody NearbyPlacesRequest request){
        log.info("Request Body Obtained: {}", request);
        return new ResponseEntity<>(placesServices.getNearByPlaces(request), HttpStatus.OK);
    }
}
