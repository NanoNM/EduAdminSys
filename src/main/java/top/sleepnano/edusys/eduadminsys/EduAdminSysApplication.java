package top.sleepnano.edusys.eduadminsys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
@MapperScan("top.sleepnano.edusys.eduadminsys.mapper")
public class EduAdminSysApplication {
    // 登录的用户
    public static Map<String, Object> LOGIN_USER = new HashMap<>();

    // 生成的教师注册Key
    public static Set<String> TEACHER_REG_KEY = new HashSet<>();

    public static void main(String[] args) {
        SpringApplication.run(EduAdminSysApplication.class, args);
    }

}
