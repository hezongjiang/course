package com.course.miniapp.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccessToken {
    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("access_token")
    private String accessToken;
}
