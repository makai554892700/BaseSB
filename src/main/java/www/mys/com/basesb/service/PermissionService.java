package www.mys.com.basesb.service;

import www.mys.com.basesb.pojo.auth.Permission;

public interface PermissionService {

    public Permission getPermissionByPermissionName(String permissionName);

    public Permission save(Permission permission);

}
