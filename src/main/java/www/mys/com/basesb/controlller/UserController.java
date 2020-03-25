package www.mys.com.basesb.controlller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import www.mys.com.basesb.api.UserApi;
import www.mys.com.basesb.common.base.StaticParam;
import www.mys.com.basesb.pojo.auth.User;
import www.mys.com.basesb.service.UserService;
import www.mys.com.basesb.utils.DataUtils;
import www.mys.com.basesb.utils.ResultUtils;
import www.mys.com.basesb.utils.net.IPUtils;
import www.mys.com.basesb.vo.request.RequestData;
import www.mys.com.basesb.vo.request.UserRequest;
import www.mys.com.basesb.vo.response.Result;
import www.mys.com.basesb.vo.response.UserResponse;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController implements UserApi {

    @Resource(name = "userServiceImpl")
    private UserService userService;
    @Autowired
    private HttpServletRequest request;

    @Override
//    @SecurityParameter
    public Result<UserResponse> register(@RequestBody @Valid UserRequest userRequest
            , BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new Exception(bindingResult.getFieldError().getDefaultMessage());
        }
        User result = userService.register(
                DataUtils.userRequest2User(userRequest, StaticParam.EMPTY)
                , IPUtils.getIP(request)
        );
        if (result == null) {
            return ResultUtils.field(null);
        }
        return ResultUtils.succeed(DataUtils.user2UserResponse(result));
    }

    @Override
//    @SecurityParameter
    public Result<UserResponse> login(@RequestBody @Valid UserRequest userRequest
            , BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new Exception(bindingResult.getFieldError().getDefaultMessage());
        }
        User result = userService.login(DataUtils.userRequest2User(userRequest, StaticParam.EMPTY)
                , userRequest.isRember(), userRequest.getDeviceType()
                , IPUtils.getIP(request));
        if (result == null) {
            return ResultUtils.field(null);
        }
        return ResultUtils.succeed(DataUtils.user2UserResponse(result));
    }

    @Override
//    @SecurityParameter
    public Result<String> logout() throws Exception {
        return ResultUtils.succeed(userService.logout());
    }

    @Override
//    @SecurityParameter
    public Result<UserResponse> update(@RequestBody @Valid UserRequest userRequest
            , BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new Exception(bindingResult.getFieldError().getDefaultMessage());
        }
        User result = userService.update(DataUtils.userRequest2User(userRequest, StaticParam.EMPTY));
        if (result == null) {
            return ResultUtils.field(null);
        }
        return ResultUtils.succeed(DataUtils.user2UserResponse(result));
    }

    @Override
    public Result<Page<UserResponse>> getUsers(Integer page, Integer count) throws Exception {
        return ResultUtils.succeed(userService.getUsers(page, count));
    }

    @Override
    public void delUsers(@RequestBody @Valid RequestData<List<Integer>> ids, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new Exception(bindingResult.getFieldError().getDefaultMessage());
        }
        userService.delUsers(ids.getData());
    }
}
