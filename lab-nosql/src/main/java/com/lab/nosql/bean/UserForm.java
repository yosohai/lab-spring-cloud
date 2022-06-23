package com.lab.nosql.bean;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UserForm {

    @NotBlank
    @Size(min = 2)
    private String name;

    @NotBlank
    @Email
    private String email;

    private String id;
    @NotNull(message = "姓名不能为空")
    private String testeeName;
    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式错误")
    private String testeePhone;
    @NotNull(message = "性别不能为空")
    @Pattern(regexp = "^[0,1]{1}$", message = "性别只能选择男女")
    private String testeeSex;
    @NotNull(message = "出生日期不能为空")
    @Pattern(regexp = "^\\d{4}\\-\\d{2}\\-\\d{2}$", message = "出生日期格式错误")
    private String testeeBirth;

}