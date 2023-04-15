package top.sleepnano.edusys.eduadminsys.service.impl;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import top.sleepnano.edusys.eduadminsys.dto.PostRegUser;
import top.sleepnano.edusys.eduadminsys.entity.User;
import top.sleepnano.edusys.eduadminsys.service.AdminService;
import top.sleepnano.edusys.eduadminsys.util.CiphertextUtil;
import top.sleepnano.edusys.eduadminsys.util.RandomUtil;
import top.sleepnano.edusys.eduadminsys.util.StatusCodeUtil;
import top.sleepnano.edusys.eduadminsys.util.VoBuilderUtil;
import top.sleepnano.edusys.eduadminsys.vo.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static top.sleepnano.edusys.eduadminsys.EduAdminSysApplication.TEACHER_REG_KEY;

@Service
public class AdminServiceImpl extends CustomUserDetailsServiceImpl implements AdminService {

    @Override
    public Result userReg(PostRegUser postRegUser) {
        String passwordEncoder = CiphertextUtil.getBCryptPasswordEncoder(postRegUser.getPasswd());

        String role = postRegUser.getRole()!=null?postRegUser.getRole():"student:normal";

        try{
            Integer integer = userMapper.InsertIntoUser(
                    postRegUser.getName(),
                    passwordEncoder,
                    RandomUtil.genUUID().toString(),
                    postRegUser.getEmployeeID(),
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
    public Result getStudents(Integer num,Integer pageSize){
        if (pageSize==null){
            pageSize = 8;
        }
        if (num<=0){
            return VoBuilderUtil.error(StatusCodeUtil.error.ERROR, "无法从"+num+"页开始查询", null);
        }
        List<User> users = null;
        users = userMapper.selectUserByPage((num - 1) * pageSize, pageSize);
        Integer studentCount = userMapper.studentCount();

        Map<String,Object> students = new HashMap<>();

        students.put("students",users);
        students.put("pageTotal",studentCount);

//        不用判空 为空前段展示0条即可
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"查询成功",students);
    }

    @Override
    public Result deleteStudent(String userNo) {
        Integer integer = userMapper.deleteUserByUserNo(userNo);
        if (integer<=0){
            return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"删除学生失败",null);
        }
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"删除成功",userNo);
    }

    @Override
    public Result updateStudent(String userNo,String username,String empID,String role) {
        Integer integer = userMapper.updateUserByUserNo(userNo,username,empID,role);
        if (integer<=0){
            return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"更新学生失败",null);
        }
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"更新成功",userNo);
    }

    @Override
    public Result genTeacherRegCode(Integer nums) {
        for (int i = 0; i < nums; i++) {
            TEACHER_REG_KEY.add(RandomUtil.genRandomStr(16));
        }
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"所有未使用的Key",TEACHER_REG_KEY);
    }
}
