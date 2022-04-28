package com.lab.nosql.enums;

import lombok.Getter;

@Getter
public enum ResultCodeEnum implements StatusCode {
    SUCCESS("0", "请求成功"),
    FAILED("1001", "请求失败"),
    VALIDATE_ERROR("1002", "参数校验失败"),
    RESPONSE_PACK_ERROR("1003", "response返回包装失败");

    private String code;
    private String msg;

    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}