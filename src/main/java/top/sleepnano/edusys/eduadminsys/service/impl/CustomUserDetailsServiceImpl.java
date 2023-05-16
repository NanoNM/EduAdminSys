package top.sleepnano.edusys.eduadminsys.service.impl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.sleepnano.edusys.eduadminsys.EduAdminSysApplication;
import top.sleepnano.edusys.eduadminsys.dto.LoginUser;
import top.sleepnano.edusys.eduadminsys.dto.LoginUserMap;
import top.sleepnano.edusys.eduadminsys.entity.User;
import top.sleepnano.edusys.eduadminsys.mapper.UserMapper;
import top.sleepnano.edusys.eduadminsys.service.CustomUserDetailsService;
import top.sleepnano.edusys.eduadminsys.util.JwtUtil;
import top.sleepnano.edusys.eduadminsys.util.StatusCodeUtil;
import top.sleepnano.edusys.eduadminsys.util.VoBuilderUtil;
import top.sleepnano.edusys.eduadminsys.vo.Result;

import java.io.IOException;
import java.util.*;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
    @Autowired
    UserMapper userMapper;

    /**
     * 设置登录成功的用户的用户信息和用户权限
     * @param username 登录的用户名
     * @return 携带权限的用户信息
     * @throws UsernameNotFoundException 忘了
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectUserByUserName(username);
        if (Objects.isNull(user)){
            throw new RuntimeException("无效的用户名和密码");
        }
//      TODO 拥有权限信息后 请解开
//          List<String> permissions = userMapper.getUserPermission(user.getUserNo());
        List<String> permissions = new ArrayList<>();
        return new LoginUser(user,permissions);
    }

    /**
     * 用户登录成功实现 身份验证成功后交给此方法生成jwt token存入到登录的用户map中
     * TODO 可转移至REDIS中
     * @param authentication springSecurity 传递认证器
     * @return vo返回包装类
     */
    @Override
    public Result successLogin(Authentication authentication) {
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();
        String userNo = loginUser.getUser().getUserNo();
        String jwt = JwtUtil.createJWT(userNo);
        Map<String,String> jwtMap = new HashMap<>();
        jwtMap.put("token",jwt);
        LoginUserMap loginUserMap = new LoginUserMap();
        loginUserMap.setLoginUser(loginUser);
        loginUserMap.setTimestamp(System.currentTimeMillis());
        EduAdminSysApplication.LOGIN_USER.put("login:"+userNo,loginUserMap);
        userMapper.updateLastLoginDate(userNo);
//        redisTemplate.opsForValue().set("login:"+userNo,loginUser);
        return VoBuilderUtil.ok(StatusCodeUtil.success.LOGIN_SUCCESS,"登录成功", jwtMap);
    }

    /**
     * 登出操作实现方法
     * 通过jwt token 从Map删除登录的用户
     * @param token 执行登出操作的账户的jwt token
     */
    @Override
    public void logout(String token, HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException {
        String userNo = null;
        try {
            userNo = JwtUtil.parseJWT(token).getSubject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Object remove = EduAdminSysApplication.LOGIN_USER.remove("login:" + userNo);
        System.out.println(remove);
        if (Objects.isNull(remove)){
            RuntimeException e = new RuntimeException("无效的登出");
            e.printStackTrace();
//             异常捕获，发送到error controller
            request.setAttribute("filter.error", e);
//            将异常分发到/error/exthrow控制器
            request.getRequestDispatcher("/error/exthrow").forward(request, response);
        }
    }
}
