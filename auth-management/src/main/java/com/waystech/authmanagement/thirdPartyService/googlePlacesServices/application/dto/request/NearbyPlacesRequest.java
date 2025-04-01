package org.partypal.thirdPartyService.googlePlacesServices.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NearbyPlacesRequest {
    @NotBlank(message = "Keyword must not be blank")
    private String keyword;
    @NotBlank(message = "Location must not be blank")
    private String location;
    @NotBlank(message = "Radius must not be blank")
    private String radius;
    @NotBlank(message = "Type must not be blank")
    private String type;
}
