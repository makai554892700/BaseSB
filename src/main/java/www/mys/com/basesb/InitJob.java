package www.mys.com.basesb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Component;
import www.mys.com.basesb.common.base.DefaultUserConf;
import www.mys.com.basesb.common.base.StaticParam;
import www.mys.com.basesb.common.repeatsubmit.RePeatSubmitUtils;
import www.mys.com.basesb.mapper.UserMapper;
import www.mys.com.basesb.pojo.*;
import www.mys.com.basesb.pojo.auth.Permission;
import www.mys.com.basesb.pojo.auth.Role;
import www.mys.com.basesb.pojo.auth.User;
import www.mys.com.basesb.service.*;
import www.mys.com.basesb.utils.LogUtils;
import www.mys.com.basesb.utils.file.FileUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitJob {

    @Resource(name = "permissionServiceImpl")
    private PermissionService permissionService;
    @Resource(name = "roleServiceImpl")
    private RoleService roleService;
    @Resource(name = "userMapper")
    private UserMapper userMapper;
    @Resource(name = "defaultUserConf")
    private DefaultUserConf defaultUserConf;
    @Resource(name = "sysConfigServiceImpl")
    private SysConfigService sysConfigService;
    @Resource(name = "textsServiceImpl")
    private TextsService textsService;
    @Autowired
    private RePeatSubmitUtils rePeatSubmitUtils;

    @PostConstruct
    public void initProject() {
        LogUtils.initRootPath();
        LogUtils.log("Init project.");
        initUser();
        initSysConf();
        new SimpleAsyncTaskExecutor(getClass().getName()).execute(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    public static void initImgPath() {
        String[] pathSplits = StaticParam.IMG_PATH.split("/");
        StringBuilder imgRootPath = new StringBuilder(StaticParam.ROOT_PATH);
        for (String pathSplit : pathSplits) {
            imgRootPath.append(File.separator).append(pathSplit);
            if (FileUtils.sureDir(imgRootPath.toString()) == null) {
                LogUtils.log("create img rootPath error.path=" + imgRootPath);
                return;
            }
        }
    }

    private void initSysConf() {
        InputStream inputStream;
        try {
            ClassPathResource resource = new ClassPathResource("properties" + File.separator + "SysConfig.properties");
            inputStream = resource.getInputStream();
        } catch (Exception e) {
            LogUtils.log("init SysConf error. classpath:properties/SysConfig.properties file not exists.");
            return;
        }
        FileUtils.readLine(inputStream, new FileUtils.LineBack() {
            @Override
            public void onStart(String fileName) {

            }

            @Override
            public void onLine(String line) {
                String[] kvs = line.split("=");
                if (kvs.length == 2) {
                    SysConfig sysConfig = sysConfigService.getSysConfigByKey(kvs[0]);
                    if (sysConfig == null) {
                        sysConfigService.saveSysConfig(kvs[0], kvs[1]);
                        if (StaticParam.IMG_PATH_KEY.equals(kvs[1])) {
                            StaticParam.IMG_PATH = kvs[1];
                            initImgPath();
                        } else if (StaticParam.IMG_HOST_KEY.equals(kvs[0])) {
                            StaticParam.IMG_HOST = kvs[1];
                        }
                    } else {
                        LogUtils.log("data exists.line=" + line);
                    }
                } else {
                    LogUtils.log("data error.line=" + line);
                }
            }

            @Override
            public void onEnd(String fileName) {

            }
        });
    }

    private void initUser() {
        Permission tempPermission = defaultUserConf.getPermission();
        Permission permission = permissionService.getPermissionByPermissionName(tempPermission.getPermissionName());
        if (permission == null) {
            permission = permissionService.save(tempPermission);
        }
        Role tempRole = defaultUserConf.getRole();
        Role role = roleService.getRoleByRoleName(tempRole.getRoleName());
        if (role == null) {
            List<Permission> permissions = new ArrayList<>();
            permissions.add(permission);
            tempRole.setPermissions(permissions);
            role = roleService.save(tempRole);
        }
        User tempUser = defaultUserConf.getUser();
        tempUser.setPackageName(StaticParam.EMPTY);
        User user = userMapper.getUserByUserNameAndDeviceId(tempUser.getUserName(), tempUser.getDeviceId());
        if (user == null) {
            if (userMapper.insert(tempUser.getId(), tempUser.getEmail(), tempUser.getPhoto(), tempUser.getUserDesc()
                    , tempUser.getNickName(), tempUser.getPass(), tempUser.getUserName()
                    , tempUser.getSex(), tempUser.getUserName(), tempUser.getPackageName()
                    , tempUser.isAvailable()
            ) > 0) {
                List<Role> roleList = new ArrayList<>();
                roleList.add(role);
                tempUser.setRoles(roleList);
                userMapper.save(tempUser);
                LogUtils.log("Insert user ok");
            } else {
                LogUtils.log("Insert user error.");
            }
        } else {
            LogUtils.log("User already exist.");
        }
    }

}
