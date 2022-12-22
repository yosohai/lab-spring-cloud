package org.lab.core.enums;

/**
 * 定义org.springframework.http.HttpStatus
 */
public enum ResultCodeEnum {

    // 应用模块公共
    SUCCESS(0, "操作成功"),
    ERROR(1, "操作失败"),

    //web
    WEB_400(400, "错误请求"),
    WEB_401(401, "访问未得到授权"),
    WEB_404(404, "资源未找到"),
    WEB_500(500, "服务器内部错误"),
    SERVER_BUSY(503, "服务器正忙，请稍后再试!"),
    WEB_UNKOWN(999, "未知错误"),

    /*常见异常:600-999*/
    RUNTIME_EXCEPTION(600, "未知异常"),
    NULL_POINTER_EXCEPTION(601, "空指针"),
    CLASS_CAST_EXCEPTION(602, "类型转换异常"),
    IO_EXCEPTION(603, "IO异常"),
    NO_SUCH_METHOD_EXCEPTION(604, "方法存在异常"),
    INDEX_OUT_OF_BOUNDS_EXCEPTION(605, "数组越界"),
    STACK_OVERFLOW_ERROR(606, "栈溢出"),
    ARITHMETIC_EXCEPTION(607, "算术运算错误"),
    OTHER_EXCEPTION(999, "其他异常"),

    /* 参数错误：10001-19999 */
    //sign error
    SIGN_NO_APPID(10001, "appId不能为空"),
    SIGN_NO_TIMESTAMP(10002, "timestamp不能为空"),
    SIGN_NO_SIGN(10003, "sign不能为空"),
    SIGN_NO_NONCE(10004, "nonce不能为空"),
    SIGN_TIMESTAMP_INVALID(10005, "timestamp无效"),
    SIGN_DUPLICATION(10006, "重复的请求"),
    SIGN_VERIFY_FAIL(10007, "sign签名校验失败"),
    PARAM_IS_INVALID(10008, "参数无效"),
    ARG_TYPE_MISMATCH(10009, "参数类型错误"),
    ARG_BIND_EXCEPTION(10010, "参数绑定错误"),
    ARG_VIOLATION(10011, "参数不符合要求"),
    ARG_MISSING(10012, "参数未找到"),

    /* 用户错误：20001-29999*/
    USER_HAS_EXISTED(20001, "用户名已存在"),
    USER_NOT_FIND(20002, "用户名不存在"),
    LOGIN_FAIL(20003, "登录失败"),
    ACCESS_DENIED(20004, "无权访问");
    /*
        其他自定义业务异常列表
     */

    private int status;
    private String message;

    ResultCodeEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer status() {
        return this.status;
    }

    public String message() {
        return this.message;
    }

}
