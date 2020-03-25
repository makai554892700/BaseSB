package www.mys.com.basesb.service.impl;

import org.springframework.stereotype.Service;
import www.mys.com.basesb.mapper.PermissionMapper;
import www.mys.com.basesb.pojo.auth.Permission;
import www.mys.com.basesb.service.PermissionService;

import javax.annotation.Resource;


@Service("permissionServiceImpl")
public class PermissionServiceImpl implements PermissionService {

    @Resource(name = "permissionMapper")
    private PermissionMapper permissionMapper;

    @Override
    public Permission getPermissionByPermissionName(String permissionName) {
        return permissionMapper.getPermissionByPermissionName(permissionName);
    }

    @Override
    public Permission save(Permission permission) {
        return permissionMapper.save(permission);
    }
}
