package com.munwap.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String full_name;
    private String email;
    private String password;

}
