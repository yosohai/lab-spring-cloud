package org.lab.validator.bean;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * 简单bean
 *
 * @author lzqing
 * @date 2022-08-09
 * @vsrsion 1.0
 **/
@Data
public class SimpleBean {

    @NotBlank(message = "姓名不能为空")
    private String name;

    @NotNull(message = "年龄不能为空")
    @Range(min = 0, max = 100, message = "年龄必须在{min}和{max}之间")
    private Integer age;

    @NotNull(message = "是否已婚不能为空")
    private Boolean isMarried;

    @NotEmpty(message = "集合不能为空")
    private Collection collection;

    @NotEmpty(message = "数组不能为空")
    private String[] array;

    @Email
    private String email;

}
