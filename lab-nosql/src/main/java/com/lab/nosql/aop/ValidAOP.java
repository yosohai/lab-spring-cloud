package com.lab.nosql.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

@Aspect
@Component
public class ValidAOP {
    // 定义切点
    private final String POINT_CUT = "execution( * com.lab.nosql..controller.*.*(..))";

    @Pointcut(value = POINT_CUT)
    public void pointCut() {
    }

    @Around(value = "pointCut() && args(.., bindingResult)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, BindingResult bindingResult) throws Throwable {
        Object obj = null;
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            StringBuffer sb = new StringBuffer();
            for (ObjectError error : errors) {
                sb.append(error.getDefaultMessage() + "\n");
            }
            obj = sb.toString();
        } else {
            obj = proceedingJoinPoint.proceed();
        }
        return obj;
    }
}
