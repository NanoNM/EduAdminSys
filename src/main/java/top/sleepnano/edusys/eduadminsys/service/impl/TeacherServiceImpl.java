package top.sleepnano.edusys.eduadminsys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import top.sleepnano.edusys.eduadminsys.EduAdminSysApplication;
import top.sleepnano.edusys.eduadminsys.dto.PostRegUser;
import top.sleepnano.edusys.eduadminsys.mapper.UserMapper;
import top.sleepnano.edusys.eduadminsys.service.TeacherService;
import top.sleepnano.edusys.eduadminsys.util.RandomUtil;
import top.sleepnano.edusys.eduadminsys.util.StatusCodeUtil;
import top.sleepnano.edusys.eduadminsys.util.VoBuilderUtil;
import top.sleepnano.edusys.eduadminsys.vo.Result;

import static top.sleepnano.edusys.eduadminsys.EduAdminSysApplication.TEACHER_REG_KEY;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    UserMapper userMapper;
    @Override
    public Result userReg(PostRegUser postRegUser) {
        if (StringUtils.isEmpty(postRegUser.getRegKey())){
            return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"错误的Key",postRegUser.getRegKey());
        }

        Integer integer1 = userMapper.userCountByRoleAndEmpID("teacher:%", postRegUser.getEmployeeID());
        if (integer1>0){
            return VoBuilderUtil.failed(StatusCodeUtil.failed.REGISTER_FAILED,"重复的学号/员工编号, 请联系服务器管理员", null);
        }

//        TEACHER_REG_KEY.remove()
        if (!TEACHER_REG_KEY.remove(postRegUser.getRegKey())){
            return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"错误的Key",postRegUser.getRegKey());

        }

        // 密码加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodePass = bCryptPasswordEncoder.encode(postRegUser.getPasswd());

        String role = postRegUser.getRole()!=null?postRegUser.getRole():"teacher:normal";

        try{


            Integer integer = userMapper.InsertIntoUser(
                    postRegUser.getName(),
                    encodePass,
                    RandomUtil.genUUID().toString(),
                    postRegUser.getEmployeeID(),
                    postRegUser.getRegKey(),
                    role
            );

            if (integer>0){
                return VoBuilderUtil.ok(StatusCodeUtil.success.REGISTER_SUCCESS,"register success", integer);
            }
            return VoBuilderUtil.error(StatusCodeUtil.error.REGISTER_ERROR,"register ERROR!! 数据库插入0条数据", null);
        }catch (DuplicateKeyException e){
            return VoBuilderUtil.failed(StatusCodeUtil.failed.REGISTER_FAILED,"我草! 您猜怎么着,UUID重复了,那叫一个稀缺,那叫一个地道,重新投递注册申请即可", null);
        }
    }
}
