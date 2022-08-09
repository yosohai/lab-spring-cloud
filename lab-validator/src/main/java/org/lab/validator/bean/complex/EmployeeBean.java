package org.lab.validator.bean.complex;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class EmployeeBean {
    @Valid
    @NotNull(message = "person不能为空")

    /**
     * 此处用到容器元素级别的约束: List<@Valid @NotNull PersonBean>  
     * 会校验容器内部元素是否为null，否则为null时会跳过校验
     * NotNull注解的target包含ElementType.TYPE_USE，因此NotNull可以给泛型注解
     */
    private List<@Valid @NotNull PersonBean> people;
}
