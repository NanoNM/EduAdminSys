package top.sleepnano.edusys.eduadminsys.controller;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.sleepnano.edusys.eduadminsys.EduAdminSysApplication;
import top.sleepnano.edusys.eduadminsys.dto.LoginUser;
import top.sleepnano.edusys.eduadminsys.entity.AllUserInfo;
import top.sleepnano.edusys.eduadminsys.entity.User;
import top.sleepnano.edusys.eduadminsys.mapper.UserMapper;
import top.sleepnano.edusys.eduadminsys.util.JwtUtil;
import top.sleepnano.edusys.eduadminsys.util.StatusCodeUtil;
import top.sleepnano.edusys.eduadminsys.util.VoBuilderUtil;
import top.sleepnano.edusys.eduadminsys.vo.Result;

import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserBaseController implements BaseController{
    @Autowired
    UserMapper userMapper;
    @GetMapping("/info")
    public Result getUserInfo(HttpServletRequest request, @Nullable @RequestParam("s")String all) throws Exception {
        String token = request.getHeader("token");
        String userNo = null;
        userNo = JwtUtil.parseJWT(token).getSubject();

        LoginUser loginUser = (LoginUser) EduAdminSysApplication.LOGIN_USER.get("login:" + userNo);
        if (Objects.isNull(loginUser)){
            return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"用户未登录",null);
        }
        String name = loginUser.getUser().getName();
        AllUserInfo user;
        if (!Objects.isNull(all)){
            user = userMapper.selectAllUserInfoByUserName(name);
        }else {
            user = userMapper.selectUserByUserName(name);
        }
        if (Objects.isNull(user)){
            return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"获取用户信息失败",null);
        }
        user.setPassword(null);
        user.setUserNo(null);
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"成功",user);
    }
}
