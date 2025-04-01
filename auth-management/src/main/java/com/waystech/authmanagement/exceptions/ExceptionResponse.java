package org.partypal.commonModule.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ExceptionResponse {
    private String message;
    private String path;
    private int statusCode;
    private String time;
}
