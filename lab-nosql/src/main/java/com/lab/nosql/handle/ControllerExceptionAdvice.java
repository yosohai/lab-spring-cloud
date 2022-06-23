package com.lab.nosql.handle;

import com.lab.nosql.base.R;
import com.lab.nosql.enums.ResultCodeEnum;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public R customException(MethodArgumentNotValidException e) {
        // 获取参数校验失败信息
        List<ObjectError> list = e.getBindingResult().getAllErrors();
        StringBuffer errorMsg = new StringBuffer();
        for (ObjectError error : list) {
            errorMsg.append(error.getDefaultMessage() + "\n");
        }
        return R.error(errorMsg.toString());
    }

    @ExceptionHandler({BindException.class})
    public R MethodArgumentNotValidExceptionHandler(BindException e) {
        // 从异常对象中拿到ObjectError对象
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        return new R(ResultCodeEnum.VALIDATE_ERROR, objectError.getDefaultMessage());
    }

}