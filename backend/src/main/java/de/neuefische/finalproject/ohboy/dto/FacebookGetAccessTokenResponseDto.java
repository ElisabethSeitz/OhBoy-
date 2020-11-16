package de.neuefische.finalproject.ohboy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacebookGetAccessTokenResponseDto {
    private String access_token;
    private String token_type;
    private int expires_in;
}
