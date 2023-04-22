package top.sleepnano.edusys.eduadminsys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import top.sleepnano.edusys.eduadminsys.dto.PostRegUser;
import top.sleepnano.edusys.eduadminsys.entity.Class;
import top.sleepnano.edusys.eduadminsys.entity.TeacherClass;
import top.sleepnano.edusys.eduadminsys.entity.User;
import top.sleepnano.edusys.eduadminsys.mapper.ClassMapper;
import top.sleepnano.edusys.eduadminsys.mapper.TeacherClassMapper;
import top.sleepnano.edusys.eduadminsys.mapper.UserMapper;
import top.sleepnano.edusys.eduadminsys.service.TeacherService;
import top.sleepnano.edusys.eduadminsys.util.RandomUtil;
import top.sleepnano.edusys.eduadminsys.util.StatusCodeUtil;
import top.sleepnano.edusys.eduadminsys.util.VoBuilderUtil;
import top.sleepnano.edusys.eduadminsys.vo.Result;

import java.util.List;

import static top.sleepnano.edusys.eduadminsys.EduAdminSysApplication.TEACHER_REG_KEY;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    ClassMapper classMapper;
    @Autowired
    TeacherClassMapper teacherClassMapper;
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

    @Override
    public Result teacherBindClass(String userNo, String classid) {
        TeacherClass teacherClass = new TeacherClass();
        User user = userMapper.selectUserByUserNo(userNo);
        teacherClass.setTeacherId(user.getId());
        teacherClass.setClassId(Integer.valueOf(classid));
        int insert = teacherClassMapper.insert(teacherClass);
        if (insert>0){
            return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"加入班级成功",teacherClass);
        }
        return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"加入班级失败",teacherClass);
    }

    @Override
    public Result getMyClassByUserNo(String userNo) {
        User user = userMapper.selectUserByUserNo(userNo);
        List<Class> classes = teacherClassMapper.selectTeacherClassesByTeacherId(user.getId());
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"查询成功", classes);
    }

    @Override
    public Result joinClass(String userNo, String classid) {
        User user = userMapper.selectUserByUserNo(userNo);
        TeacherClass teacherClass = new TeacherClass();
        teacherClass.setTeacherId(user.getId());
        teacherClass.setClassId(Integer.valueOf(classid));
        int insert = teacherClassMapper.insert(teacherClass);
        if (insert>0){
            return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"加入成功", insert);
        }
        return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"插入失败",insert);

    }

    @Override
    public Result toBeCounselor(String userNo, String classid) {
        User user = userMapper.selectUserByUserNo(userNo);
        Integer integer = classMapper.updateToBeCounselorByClassid(user.getId(),Integer.valueOf(classid));
        if (integer>0){
            return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"成为辅导员成功",integer);
        }
        return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"成为辅导员失败",integer);
    }

    @Override
    public Result retiredCounselor(String userNo, String classid) {
        Integer integer = classMapper.updateRetiredCounselorByClassid(Integer.valueOf(classid));
        if (integer>0){
            return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"成为辅导员成功",integer);
        }
        return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"成为辅导员失败",integer);
    }

    @Override
    public Result deleteClass(String userNo, String classid) {
        User user = userMapper.selectUserByUserNo(userNo);
        Integer delete = teacherClassMapper.deleteByUserIdAndClassid(user.getId(),classid);
        if (delete>0){
            return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"退出班级成功",delete);
        }
        return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"退出班级失败",delete);
    }
}
