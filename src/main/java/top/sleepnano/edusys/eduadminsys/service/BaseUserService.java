package top.sleepnano.edusys.eduadminsys.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import top.sleepnano.edusys.eduadminsys.vo.Result;

public interface BaseUserService extends UserDetailsService {
    Result successLogin(Authentication authentication);

    void logout(String token);
}
