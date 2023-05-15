package top.sleepnano.edusys.eduadminsys.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.models.auth.In;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import top.sleepnano.edusys.eduadminsys.entity.Class;
import top.sleepnano.edusys.eduadminsys.entity.Grade;
import top.sleepnano.edusys.eduadminsys.entity.User;
import top.sleepnano.edusys.eduadminsys.mapper.ClassMapper;
import top.sleepnano.edusys.eduadminsys.mapper.GradeMapper;
import top.sleepnano.edusys.eduadminsys.mapper.UserMapper;
import top.sleepnano.edusys.eduadminsys.service.ClassService;
import top.sleepnano.edusys.eduadminsys.service.StudentService;
import top.sleepnano.edusys.eduadminsys.service.TeacherService;
import top.sleepnano.edusys.eduadminsys.util.JwtUtil;
import top.sleepnano.edusys.eduadminsys.util.StatusCodeUtil;
import top.sleepnano.edusys.eduadminsys.util.VoBuilderUtil;
import top.sleepnano.edusys.eduadminsys.vo.Result;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/class")
public class ClassController implements BaseController{
    @Autowired
    ClassService classService;

    @Autowired
    StudentService studentService;

    @Autowired
    ClassMapper classMapper;

    @Autowired
    GradeMapper gradeMapper;

    @Autowired
    UserMapper userMapper;

    @GetMapping("/classes")
    public Result getClasses(@Nullable @RequestParam("grade")String grade,
                             @Nullable @RequestParam("page")Integer page,
                             @Nullable @RequestParam("size")Integer size,
                             @Nullable @RequestParam("dept")Integer deptid){
        if (grade == null){
            return classService.getAllClasses(page,size);
        }else {
            return classService.getClassesByName(grade,page,size,deptid);
        }
    }
    
    @GetMapping("/joinclass")
    public Result joinClass(HttpServletRequest request, HttpServletResponse response,@RequestParam("class")String cls) throws Exception {
        String token = request.getHeader("token");
        String userNo = null;
        userNo = JwtUtil.parseJWT(token).getSubject();
        return studentService.updateStudentAddClassByUserNo(userNo,cls);

    }

    @GetMapping("/getclass")
    public Result getClassInfoByClassID(@RequestParam("id")Integer id){
        Class classes = classMapper.selectClassesByClassID(id);
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"查询成功",classes);
    }

    // 通过班级id获取辅导员
    @GetMapping("/getcounsellor")
    public Result getCounsellorInfoByClassID(@RequestParam("id")Integer id){
        Class classes = classMapper.selectClassesByClassID(id);
        if (classes.getCounsellorId() == null){
            return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"查询成功",null);
        }
        User teacher = userMapper.selectUserByUserID(classes.getCounsellorId());
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"查询成功",teacher);
    }

    @GetMapping("/updateclass")
    @Transactional
    public Result updateClass(@RequestParam("className")String className,
                              @RequestParam("classYear")String classYear,
                              @RequestParam("id")Integer id){

        System.out.println("className = " + className);
        System.out.println("classYear = " + classYear);
        System.out.println("id = " + id);

        Grade grade = gradeMapper.selectGradeByName(classYear);
        Class classes = classMapper.selectClassesByClassID(id);
        classes.setClassName(className);
        classes.setClassYear(grade.getId());
        int i = classMapper.updateById(classes);
        if (i>0){
            return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"更新成功", classes);
        }
        return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"更新失败", classes);
    }


    @GetMapping("/deleteclass")
    @Transactional
    public Result deleteClass(@RequestParam("id")Integer id){
        Class classes = classMapper.selectClassesByClassID(id);

        List<User> users = userMapper.selectStudentsByClassid(classes.getId());

        users.forEach(it->{
            it.setClassId(-1);
            userMapper.updateById(it);
        });
        Integer integer = classMapper.deleteByID(classes.getId());
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"删除成功 有 "+users.size()+" 个学生失去了它的班级",integer);
    }
}
