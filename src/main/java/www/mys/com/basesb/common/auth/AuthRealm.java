package www.mys.com.basesb.common.auth;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import www.mys.com.basesb.mapper.UserMapper;
import www.mys.com.basesb.pojo.auth.Permission;
import www.mys.com.basesb.pojo.auth.Role;
import www.mys.com.basesb.pojo.auth.User;

import javax.annotation.Resource;

public class AuthRealm extends AuthorizingRealm {

    @Resource(name = "userMapper")
    private UserMapper userMapper;

    //认证.登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        UserToken utoken = (UserToken) token;
        User user = userMapper.getUserByUserNameAndDeviceId(utoken.getUsername(), utoken.getDeviceId());
        return new SimpleAuthenticationInfo(user.getUserName(), user.getPass()
                , this.getClass().getName());
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        String name = (String) principal.getPrimaryPrincipal();
        User user = userMapper.getUserByUserName(name);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (Role role : user.getRoles()) {
            simpleAuthorizationInfo.addRole(role.getRoleName());
            for (Permission permission : role.getPermissions()) {
                simpleAuthorizationInfo.addStringPermission(permission.getPermissionName());
            }
        }
        return simpleAuthorizationInfo;
    }

}
