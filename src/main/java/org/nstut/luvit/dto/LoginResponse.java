package org.nstut.luvit.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private Integer status;
    private String token;
}
