package www.mys.com.basesb.service;

import www.mys.com.basesb.pojo.auth.Role;

public interface RoleService {

    public Role getRoleByRoleName(String roleName);

    public Role save(Role role);

}
