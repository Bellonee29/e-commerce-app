package org.partypal.thirdPartyService.googlePlacesServices.domain.services.apiConnection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.partypal.thirdPartyService.googlePlacesServices.application.dto.request.NearbyPlacesRequest;
import org.partypal.thirdPartyService.googlePlacesServices.application.dto.response.GeoResponse;
import org.partypal.thirdPartyService.googlePlacesServices.common.utils.PlacesUrlUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebClientServicesImpl implements WebClientService{
    private final WebClient webClient;
    private String placesKey;

    @Override
    public GeoResponse getNearByPlaces(NearbyPlacesRequest request){
        log.info("Request Body Obtained: {}", request);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                    .path(PlacesUrlUtils.Search_Near_By_Places)
                    .queryParam("keyword", request.getKeyword())
                    .queryParam("location", request.getLocation())
                    .queryParam("radius", request.getRadius())
                    .queryParam("type", request.getType())
                    .queryParam("key",placesKey)
                    .build()
                )
                .retrieve()
                .bodyToMono(GeoResponse.class)
                .block();
    }
}
