package org.lab.validator.check;

import org.hibernate.validator.HibernateValidator;
import org.lab.validator.bean.complex.EmployeeBean;
import org.lab.validator.bean.complex.OrgBean;
import org.lab.validator.bean.complex.PersonBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Nested bean demo
 *
 * @author lzqing
 * @date 2022-08-09
 * @vsrsion 1.0
 **/
public class NestedDemo {
    //初始化一个校验器工厂
    private static ValidatorFactory validatorFactory = Validation
            .byProvider(HibernateValidator.class)
            .configure()
            //校验失败是否立即返回： true-遇到一个错误立即返回不在往下校验，false-校验完所有字段才返回
            .failFast(false)
            .buildValidatorFactory();

    public static void main(String[] args) {
        PersonBean p = new PersonBean();
        p.setAge(30);
        p.setName("zhangsan");
        //p.setIsMarried(true);

        PersonBean p2 = new PersonBean();
        p2.setAge(30);
        //p2.setName("zhangsan2");
        p2.setIsMarried(false);
        //p2.setHasChild(true);

        OrgBean org = new OrgBean();
        //org.setId(1);

        List<PersonBean> list = new ArrayList<>();
        list.add(p);
        list.add(p2);
        //增加一个null，测试是否会校验元素为null
        list.add(null);

        EmployeeBean e = new EmployeeBean();
        e.setPeople(list);
        org.setEmployee(e);

        Set<ConstraintViolation<OrgBean>> result = validatorFactory.getValidator().validate(org);

        System.out.println("遍历输出错误信息：");
        result.forEach(r -> System.out.println(r.getPropertyPath() + ":" + r.getMessage()));
    }
}
