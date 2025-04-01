package com.waystech.authmanagement.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class NovaResponse<T>{
    private String message;
    private T data;
}
