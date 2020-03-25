package www.mys.com.basesb.api;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import www.mys.com.basesb.vo.request.RequestData;
import www.mys.com.basesb.vo.request.UserRequest;
import www.mys.com.basesb.vo.response.Result;
import www.mys.com.basesb.vo.response.UserResponse;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/basesb/user")
public interface UserApi {

    @PostMapping(value = "/register")
    @RequiresUser
    public Result<UserResponse> register(@RequestBody @Valid UserRequest userRequest
            , BindingResult bindingResult) throws Exception;

    @PostMapping(value = "/login")
    public Result<UserResponse> login(@RequestBody @Valid UserRequest userRequest
            , BindingResult bindingResult) throws Exception;

    @GetMapping(value = "/logout")
    @RequiresUser
    public Result<String> logout() throws Exception;

    @PostMapping(value = "/update")
    @RequiresUser
    public Result<UserResponse> update(@RequestBody @Valid UserRequest userRequest
            , BindingResult bindingResult) throws Exception;

    @GetMapping(value = "/getUsers/{page}/{count}")
    @RequiresUser
    public Result<Page<UserResponse>> getUsers(@PathVariable("page") Integer page
            , @PathVariable("count") Integer count) throws Exception;

    @PostMapping(value = "/delUsers")
    @RequiresUser
    @RequiresRoles("admin")
    public void delUsers(@RequestBody @Valid RequestData<List<Integer>> ids
            , BindingResult bindingResult) throws Exception;

}
