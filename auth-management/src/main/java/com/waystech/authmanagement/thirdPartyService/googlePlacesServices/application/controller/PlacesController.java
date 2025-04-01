package org.partypal.thirdPartyService.googlePlacesServices.application.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.partypal.thirdPartyService.googlePlacesServices.application.dto.request.NearbyPlacesRequest;
import org.partypal.thirdPartyService.googlePlacesServices.application.dto.response.GeoResponse;
import org.partypal.thirdPartyService.googlePlacesServices.domain.services.PlacesServices;
import org.partypal.userManagement.application.dto.PartyPalResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("places")
@RequiredArgsConstructor
@Slf4j
public class PlacesController {
    private final PlacesServices placesServices;
    @PostMapping("get-nearby-places")
    public ResponseEntity<PartyPalResponse<GeoResponse>> getNearByPlaces(@Valid @RequestBody NearbyPlacesRequest request){
        log.info("Request Body Obtained: {}", request);
        return new ResponseEntity<>(placesServices.getNearByPlaces(request), HttpStatus.OK);
    }
}
