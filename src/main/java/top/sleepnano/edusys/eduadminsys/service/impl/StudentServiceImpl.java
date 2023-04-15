package top.sleepnano.edusys.eduadminsys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import top.sleepnano.edusys.eduadminsys.dto.PostLoginStudent;
import top.sleepnano.edusys.eduadminsys.dto.PostRegUser;
import top.sleepnano.edusys.eduadminsys.mapper.UserMapper;
import top.sleepnano.edusys.eduadminsys.service.StudentService;
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
    public Result userReg(PostRegUser postRegStudent) {
        // 密码加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodePass = bCryptPasswordEncoder.encode(postRegStudent.getPasswd());

        String role = postRegStudent.getRole()!=null?postRegStudent.getRole():"student:normal";
        try{
            Integer integer1 = userMapper.userCountByRoleAndEmpID("student:%", postRegStudent.getEmployeeID());
            if (integer1>0){
                return VoBuilderUtil.failed(StatusCodeUtil.failed.REGISTER_FAILED,"重复的学号/员工编号, 请联系服务器管理员", null);
            }

            Integer integer = userMapper.InsertIntoUser(
                    postRegStudent.getName(),
                    encodePass,
                    RandomUtil.genUUID().toString(),
                    postRegStudent.getEmployeeID(),
                    "student",
                    role
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

}
