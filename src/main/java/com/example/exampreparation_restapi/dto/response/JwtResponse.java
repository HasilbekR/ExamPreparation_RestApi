package com.example.exampreparation_restapi.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JwtResponse {
    private String accessToken;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String refreshToken;
}
