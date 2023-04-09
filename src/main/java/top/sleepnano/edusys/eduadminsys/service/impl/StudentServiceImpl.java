package top.sleepnano.edusys.eduadminsys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import top.sleepnano.edusys.eduadminsys.EduAdminSysApplication;
import top.sleepnano.edusys.eduadminsys.dto.LoginUser;
import top.sleepnano.edusys.eduadminsys.dto.PostLoginStudent;
import top.sleepnano.edusys.eduadminsys.dto.PostRegStudent;
import top.sleepnano.edusys.eduadminsys.entity.User;
import top.sleepnano.edusys.eduadminsys.mapper.UserMapper;
import top.sleepnano.edusys.eduadminsys.service.StudentService;
import top.sleepnano.edusys.eduadminsys.util.JwtUtil;
import top.sleepnano.edusys.eduadminsys.util.RandomUtil;
import top.sleepnano.edusys.eduadminsys.util.StatusCodeUtil;
import top.sleepnano.edusys.eduadminsys.util.VoBuilderUtil;
import top.sleepnano.edusys.eduadminsys.vo.Result;

import java.util.*;

/**
 * 学生相关实现类
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    UserMapper userMapper;


    /**
     * 学生注册实现 使用BCryptPasswordEncoder加密密码
     * 然后插入到数据库中
     * @param postRegStudent 前端投递的信息
     * @return vo信息包装类
     */
    @Override
    public Result userReg(PostRegStudent postRegStudent) {
        // 密码加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodePass = bCryptPasswordEncoder.encode(postRegStudent.getPasswd());

        try{
            Integer integer = userMapper.InsertIntoUser(
                    postRegStudent.getName(),
                    encodePass,
                    RandomUtil.genUUID().toString(),
                    postRegStudent.getEmployeeID(),
                    "student:normal"
                    );
            if (integer>0){
                return VoBuilderUtil.ok(StatusCodeUtil.success.REGISTER_SUCCESS,"register success", integer);
            }
            return VoBuilderUtil.error(StatusCodeUtil.error.REGISTER_ERROR,"register ERROR!! 数据库插入0条数据", null);
        }catch (DuplicateKeyException e){
            if (e.getMessage().indexOf("user_uuid")>0){
                return VoBuilderUtil.failed(StatusCodeUtil.failed.REGISTER_FAILED,"我草! 您猜怎么着,UUID重复了,那叫一个稀缺,那叫一个地道,重新投递注册申请即可", null);
            }
            return VoBuilderUtil.failed(StatusCodeUtil.failed.REGISTER_FAILED,"重复的学号/员工编号, 请联系服务器管理员", null);
        }
    }

    @Override
    public Result userLogin(PostLoginStudent postLoginStudent) {
        return null;
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
        EduAdminSysApplication.LOGIN_USER.put("login:"+userNo,loginUser);
//        redisTemplate.opsForValue().set("login:"+userNo,loginUser);
        return VoBuilderUtil.ok(StatusCodeUtil.success.LOGIN_SUCCESS,"登录成功", jwtMap);
    }

    /**
     * 登出操作实现方法
     * 通过jwt token 从Map删除登录的用户
     * @param token 执行登出操作的账户的jwt token
     */
    @Override
    public void logout(String token) {
        String userNo = null;
        try {
            userNo = JwtUtil.parseJWT(token).getSubject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Object remove = EduAdminSysApplication.LOGIN_USER.remove("login:" + userNo);
    }

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
}
