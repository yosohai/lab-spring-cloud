package org.lab.core.result;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.lab.core.enums.ResultCodeEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> extends R {

    private T data;

    /**
     * 成功，创建ResResult：没data数据
     */
    public static Result success() {
        Result result = new Result();
        result.setResultCode(ResultCodeEnum.SUCCESS);
        return result;
    }

    /**
     * 成功，创建ResResult：有data数据
     */
    public static Result success(Object data) {
        Result result = new Result();
        result.setResultCode(ResultCodeEnum.SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * 失败，有data数据
     */
    public static Result fail(ResultCodeEnum resultCode, Object data) {
        Result result = new Result();
        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }
}
