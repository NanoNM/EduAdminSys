package top.sleepnano.edusys.eduadminsys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.sleepnano.edusys.eduadminsys.dto.PostLoginStudent;
import top.sleepnano.edusys.eduadminsys.dto.PostRegUser;
import top.sleepnano.edusys.eduadminsys.entity.Class;
import top.sleepnano.edusys.eduadminsys.entity.StuInfo;
import top.sleepnano.edusys.eduadminsys.entity.User;
import top.sleepnano.edusys.eduadminsys.mapper.ClassMapper;
import top.sleepnano.edusys.eduadminsys.mapper.StuInfoMapper;
import top.sleepnano.edusys.eduadminsys.mapper.UserMapper;
import top.sleepnano.edusys.eduadminsys.service.StudentService;
import top.sleepnano.edusys.eduadminsys.util.RandomUtil;
import top.sleepnano.edusys.eduadminsys.util.StatusCodeUtil;
import top.sleepnano.edusys.eduadminsys.util.VoBuilderUtil;
import top.sleepnano.edusys.eduadminsys.vo.Result;

import java.util.List;
import java.util.Objects;

/**
 * 学生相关实现类
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    ClassMapper classMapper;
    @Autowired
    StuInfoMapper stuInfoMapper;


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

    @Override
    @Transactional
    public Result updateStudentAddClassByUserNo(String userNo,String cls) {
        userMapper.updateLastModifyByUserNo(userNo);
        Integer integer = userMapper.updateStudentAddClassByUserNo(userNo, cls);
        Class classes = classMapper.selectClassesByClassID(Integer.valueOf(cls));
        if (Objects.isNull(classes)){
            return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"加入班级失败，没有此班级",integer);
        }
        if (integer>0){
            userMapper.updateLastModifyByUserNo(userNo);
            return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"加入班级成功",integer);
        }
        return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"加入班级失败",null);
    }

    @Override
    public Result getMyClassByUserNo(String userNo) {
//        User user = userMapper.selectUserByUserNo(userNo);
//        List<Class> classes = userMapper.selectTeacherClassesByTeacherId(user.getId());
//        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"查询成功", classes);
        return null;
    }

    @Override
    public Result getStudentsByClassID(Integer classid) {
        List<User> students = userMapper.selectStudentsByClassid(classid);
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"搜索成功",students);
    }

    @Override
    public Result getMoreStudentInfo(Integer stid) {
        StuInfo stuInfo = stuInfoMapper.selectByStid(stid);
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"查询成功",stuInfo);

    }

}
