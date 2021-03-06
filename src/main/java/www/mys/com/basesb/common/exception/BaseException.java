package www.mys.com.basesb.common.exception;

import www.mys.com.basesb.common.base.BaseResultEnum;

import javax.validation.constraints.NotNull;

//用户异常，用于返回正确的格式及异常码
public class BaseException extends RuntimeException {

    private Integer code;

    public BaseException(@NotNull BaseResultEnum baseResultEnum) {
        super(baseResultEnum.getMsg());
        code = baseResultEnum.getCode();
    }

    public BaseException(String msg, Integer code) {
        super(msg);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
