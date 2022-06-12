package com.lab.nosql.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserTokenDTO {
    private String userName;
    private String nickName;
}
