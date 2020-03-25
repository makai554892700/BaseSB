package www.mys.com.basesb.common.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import www.mys.com.basesb.common.exception.BaseException;
import www.mys.com.basesb.utils.LogUtils;
import www.mys.com.basesb.utils.ResultUtils;
import www.mys.com.basesb.vo.response.Result;

//controller层错误拦截，用于拦截错误页面返回给用户，给用户正确的格式
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public Result<String> onField(BaseException baseException) {
        LogUtils.log("onField error message=" + baseException.getMessage());
        return ResultUtils.field(baseException.getCode(), baseException.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<String> onError(Exception e) {
        LogUtils.log("onError error message=" + e.getMessage());
        return ResultUtils.field(e.getMessage());
    }

}
