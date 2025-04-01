package com.waystech.authmanagement.integrations.googlePlacesServices.domain.services.apiConnection;

import com.waystech.authmanagement.integrations.googlePlacesServices.application.dto.request.NearbyPlacesRequest;
import com.waystech.authmanagement.integrations.googlePlacesServices.application.dto.response.GeoResponse;
import com.waystech.authmanagement.integrations.googlePlacesServices.common.utils.PlacesUrlUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
