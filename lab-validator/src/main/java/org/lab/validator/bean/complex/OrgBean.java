package org.lab.validator.bean.complex;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class OrgBean {
    @NotNull
    private Integer id;
  
    @Valid  //如果此处不用Valid注解，则不会去校验EmployeeBean对象的内部字段
    @NotNull(message = "employee不能为空")
    private EmployeeBean Employee;
}
