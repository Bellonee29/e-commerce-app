package org.partypal.thirdPartyService.googlePlacesServices.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlacesPhoto {
    private int height;
    private int width;
    private String[] html_attributions;
    private String photo_reference;
}
