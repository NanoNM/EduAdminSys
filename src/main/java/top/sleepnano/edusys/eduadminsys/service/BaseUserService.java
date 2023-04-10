package top.sleepnano.edusys.eduadminsys.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import top.sleepnano.edusys.eduadminsys.dto.PostRegUser;
import top.sleepnano.edusys.eduadminsys.vo.Result;

public interface BaseUserService{

    Result userReg(PostRegUser postRegUser);

}
