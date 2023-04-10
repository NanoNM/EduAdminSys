package top.sleepnano.edusys.eduadminsys.initialization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import top.sleepnano.edusys.eduadminsys.dto.PostRegUser;
import top.sleepnano.edusys.eduadminsys.entity.User;
import top.sleepnano.edusys.eduadminsys.mapper.UserMapper;
import top.sleepnano.edusys.eduadminsys.service.BaseUserService;
import top.sleepnano.edusys.eduadminsys.util.RandomUtil;

import java.util.Objects;

/**
 * 服务器初始化管理员
 */
@Component
@Order(value = 1)
@Slf4j
public class DefaultAdministratorGenerator implements BaseInitialization{
    @Autowired
    @Qualifier("adminServiceImpl")
    private BaseUserService adminService;
    @Autowired
    UserMapper userMapper;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("12313123");
        User genadmin = userMapper.selectUserByEmpID("GENADMIN");

        if (Objects.nonNull(genadmin)){
            return;
        }

        String rName = RandomUtil.genRandomStr(8);
        String rPasswd = RandomUtil.genRandomStr(8);
        PostRegUser postRegUser = new PostRegUser(rName,rPasswd,"GENADMIN","admin:super");
        System.out.println("\n\r");
        System.out.println("随机账户: "+rName+", 随机密码: "+rPasswd);
        System.out.println("\n\r");
        adminService.userReg(postRegUser);
    }
}
