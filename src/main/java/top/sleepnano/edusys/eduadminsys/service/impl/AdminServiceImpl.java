package top.sleepnano.edusys.eduadminsys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.sleepnano.edusys.eduadminsys.dto.PostRegUser;
import top.sleepnano.edusys.eduadminsys.entity.*;
import top.sleepnano.edusys.eduadminsys.entity.Class;
import top.sleepnano.edusys.eduadminsys.mapper.*;
import top.sleepnano.edusys.eduadminsys.service.AdminService;
import top.sleepnano.edusys.eduadminsys.util.CiphertextUtil;
import top.sleepnano.edusys.eduadminsys.util.RandomUtil;
import top.sleepnano.edusys.eduadminsys.util.StatusCodeUtil;
import top.sleepnano.edusys.eduadminsys.util.VoBuilderUtil;
import top.sleepnano.edusys.eduadminsys.vo.Result;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static top.sleepnano.edusys.eduadminsys.EduAdminSysApplication.TEACHER_REG_KEY;

@Service
public class AdminServiceImpl extends CustomUserDetailsServiceImpl implements AdminService {

    @Autowired
    GradeMapper gradeMapper;

    @Autowired
    ClassMapper classMapper;

    @Autowired
    CurriculumMapper curriculumMapper;

    @Autowired
    DeptCourseMapper deptCourseMapper;
    @Autowired
    DepartmentMapper departmentMapper;

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
    public Result getTeachers(Integer num, Integer pageSize) {
        if (pageSize==null){
            pageSize = 8;
        }
        if (num<=0){
            return VoBuilderUtil.error(StatusCodeUtil.error.ERROR, "无法从"+num+"页开始查询", null);
        }
        List<User> users = null;
        users = userMapper.selectTeacherByPage((num - 1) * pageSize, pageSize);
        Integer teacherCount = userMapper.teacherCount();

        Map<String,Object> students = new HashMap<>();

        students.put("students",users);
        students.put("pageTotal",teacherCount);

//        不用判空 为空前段展示0条即可
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"查询成功",students);
    }

    @Override
    public Result deleteUser(String userNo) {
        User user = userMapper.selectUserByUserNo(userNo);
        if (!"student".equals(user.getRegKey())){
            Class classes =  classMapper.selectClassesByCounselorID(user.getId());
            if (Objects.nonNull(classes)){
                Integer integer = classMapper.updateRetiredCounselorByClassid(classes.getId());
            }
        }

        Integer integer = userMapper.deleteUserByUserNo(userNo);
        if (integer<=0){
            return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"删除失败",null);
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
    public Result updateTeacher(String userNo, String username, String empID, String role) {
        Integer integer = userMapper.updateUserByUserNo(userNo,username,empID,role);
        if (integer<=0){
            return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"更新老师失败",null);
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

    @Override
    public Result createGrade(Short year, String gradeName) {
        try {
            if (gradeMapper.insertGrade(year,gradeName)>0){
                return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"年级创建成功",null);
            }
        }catch (DuplicateKeyException e){
            return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"重复年级",null);
        }

        return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"年级创建失败",null);
    }

    @Override
    public Result createClass(String gardeName, String className, Integer deptID) {
        try {
            Grade grade = gradeMapper.selectGradeByName(gardeName);
            if (Objects.isNull(grade)){
                return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"班级创建失败 没有这年级",gardeName);
            }
            Integer integer = classMapper.insertClassByGardName(gardeName, className, deptID);
            if (integer>0){
                return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"班级创建成功",null);
            }
        }catch (DuplicateKeyException e){
            return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"班级创建失败 重复的班级",null);
        }

        return VoBuilderUtil.error(StatusCodeUtil.error.ERROR,"班级创建失败",null);
    }

    @Override
    public Result getGrades(Integer page, Integer pageSize) {
        if (pageSize==null){
            pageSize = 8;
        }
        if (page<=0){
            return VoBuilderUtil.error(StatusCodeUtil.error.ERROR, "无法从"+page+"页开始查询", null);
        }

        Integer gradeCount = gradeMapper.gradeCount();
        List<Grade> grades = gradeMapper.selectGradesByPage((page - 1) * pageSize, pageSize);

        Map<String,Object> result = new HashMap<>();
        result.put("data",grades);
        result.put("pageTotal",gradeCount);
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"查询成功",result);
    }

    @Override
    public Result getGradesByStatus(String status) {
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"查询成功",gradeMapper.selectGradesByStatus(status));
    }

    @Override
    public Result deleteGrade(String grade) {
        Grade grade1 = gradeMapper.selectGradeByName(grade);
        List<Class> classes = classMapper.selectClassesByGardeIdNoPage(grade1.getId());
        AtomicInteger deleteUser = new AtomicInteger();
        classes.forEach(it->{
            List<User> users = userMapper.selectStudentsByClassid(it.getId());
            users.forEach(i->{
                User user = userMapper.selectUserByUserID(i.getId());
                user.setClassId(-1);
                userMapper.updateById(user);
                deleteUser.addAndGet(1);
            });
        });
        Integer integer = gradeMapper.deleteGradeLink(grade);

        if (integer>0){
            return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"删除成功 有 " + classes.size() +"个班级被删除, 另外有 " + deleteUser +" 个学生失去了他的班级",integer);
        }
        return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"删除失败",null);
    }

    /**
     * 创建公共课
     * @param courseName 课程名称
     * @param courseHour 课时
     * @param level 年级
     *              1，2 大一上下，
     *              3，4 大二上下，
     *              5，6 大三上下，
     *              7，8大四上下 一次类推
     * @return
     */
    @Override
    public Result createCourse(String courseName, Integer courseHour, Integer level) {
        Curriculum curriculum = new Curriculum();
        curriculum.setCourseName(courseName);
        curriculum.setClassHour(courseHour);
        curriculum.setLevel(level);
        curriculum.setPublicRequired(0);
        int insert = curriculumMapper.insert(curriculum);
        if (insert>0)
            return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"课程插入成功",insert);

        return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"课程插入失败",insert);
    }

    /**
     * 创建非公共课程
     * @param courseName 课程名
     * @param courseHour 课时
     * @param level 年级
     * @param b 非公共标记
     * @param deptId 系id
     * @return
     */
    @Override
    @Transactional // 事务注解
    public Result createCourse(String courseName, Integer courseHour, Integer level, boolean b, Integer deptId) {
        Curriculum curriculum = new Curriculum();
        curriculum.setCourseName(courseName);
        curriculum.setClassHour(courseHour);
        curriculum.setLevel(level);
        curriculum.setPublicRequired(1);
        int insert = curriculumMapper.insert(curriculum);
        if (insert<1){
            throw new RuntimeException("插入课程失败");
        }
        DeptCourse deptCourse = new DeptCourse();
        deptCourse.setDeptId(deptId);
        deptCourse.setCourseId(curriculum.getId());
        int insert1 = deptCourseMapper.insert(deptCourse);
        if (insert1<1){
            throw new RuntimeException("插入课程失败");
        }

        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"课程创建成功",curriculum);
    }

    @Override
    public Result getCourse() {
        List<Curriculum> curricula =  curriculumMapper.selectAll();
        List<Object> resultList = new ArrayList<>();
        curricula.forEach(curriculum -> {
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("curriculum",curriculum);
            List<DeptCourse> deptCourses = deptCourseMapper.selectByCurriculumId(curriculum.getId());
            List<Department> departments = new ArrayList<>();
            deptCourses.forEach(deptCourse -> {
                Department department = departmentMapper.selectById(deptCourse.getDeptId());
                departments.add(department);
            });

            resultMap.put("dept",departments);
            resultList.add(resultMap);
        });

        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"查询成功",resultList);
    }

    @Override
    public Result getTeacherRegCode() {
        ArrayList<Object> reg_keys = new ArrayList<>();

        TEACHER_REG_KEY.forEach(str->{
            Map<String,String> map = new HashMap<>();
            map.put("key",str);
            reg_keys.add(map);
        });

        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"获取成功",reg_keys);
    }


}
