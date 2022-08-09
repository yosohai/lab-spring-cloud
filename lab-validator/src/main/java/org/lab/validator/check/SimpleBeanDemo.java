package org.lab.validator.check;

import org.hibernate.validator.HibernateValidator;
import org.lab.validator.bean.SimpleBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Simple bean demo
 *
 * @author lzqing
 * @date 2022-08-09
 * @vsrsion 1.0
 **/
public class SimpleBeanDemo {
    //初始化一个校验器工厂
    private static ValidatorFactory validatorFactory = Validation
            .byProvider(HibernateValidator.class)
            .configure()
            //校验失败是否立即返回： true-遇到一个错误立即返回不在往下校验，false-校验完所有字段才返回
            .failFast(false)
            .buildValidatorFactory();

    public static void main(String[] args) {
        SimpleBean s = new SimpleBean();
        s.setAge(5);
        s.setName(" ");
        s.setEmail("email");

        Set<ConstraintViolation<SimpleBean>> result = validatorFactory.getValidator().validate(s);

        System.out.println("遍历输出错误信息:");
        //getPropertyPath() 获取属性全路径名
        //getMessage() 获取校验后的错误提示信息
        result.forEach(r -> System.out.println(r.getPropertyPath() + ":" + r.getMessage()));
    }
}
