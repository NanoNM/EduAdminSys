package top.sleepnano.edusys.eduadminsys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@SpringBootApplication
@MapperScan("top.sleepnano.edusys.eduadminsys.mapper")
public class EduAdminSysApplication {
    // 登录的用户
    public static Map<String, Object> LOGIN_USER = new HashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(EduAdminSysApplication.class, args);
    }

}
