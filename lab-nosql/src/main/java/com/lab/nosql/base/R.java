package com.lab.nosql.base;

import com.lab.nosql.enums.ResultCodeEnum;
import com.lab.nosql.enums.StatusCode;
import lombok.Data;

@Data
public class R<T> {
    private String code;
    private String msg;
    private T data;

    public R() {
    }

    public R(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // 默认返回成功状态码，数据对象
    public R(T data) {
        this.code = ResultCodeEnum.SUCCESS.getCode();
        this.msg = ResultCodeEnum.SUCCESS.getMsg();
        this.data = data;
    }

    // 返回指定状态码，数据对象
    public R(StatusCode statusCode, T data) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
        this.data = data;
    }

    // 只返回状态码
    public R(StatusCode statusCode) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
        this.data = null;
    }

    public static R error(String code, String msg) {
        return new R(code, msg);
    }

    public static R error(String msg) {
        return new R(ResultCodeEnum.FAILED);
    }

    public static R success(String data) {
        return new R(data);
    }
}
