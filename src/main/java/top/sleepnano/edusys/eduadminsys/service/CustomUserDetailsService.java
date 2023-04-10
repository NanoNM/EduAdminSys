package top.sleepnano.edusys.eduadminsys.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import top.sleepnano.edusys.eduadminsys.vo.Result;

@Service
public interface CustomUserDetailsService extends UserDetailsService {

    Result successLogin(Authentication authentication);

    void logout(String token, HttpServletResponse response, HttpServletRequest httpServletRequest) throws Exception;

}
