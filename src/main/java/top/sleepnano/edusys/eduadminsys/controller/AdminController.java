package top.sleepnano.edusys.eduadminsys.controller;

import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.sleepnano.edusys.eduadminsys.service.impl.AdminServiceImpl;
import top.sleepnano.edusys.eduadminsys.util.StatusCodeUtil;
import top.sleepnano.edusys.eduadminsys.util.VoBuilderUtil;
import top.sleepnano.edusys.eduadminsys.vo.Result;

import java.util.Objects;

/**
 * 管理员相关操作
 * 查询用户,删除用户,更新用户,生成教师注册key,年级管理
 */
@Controller
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {
    @Autowired
    @Qualifier("adminServiceImpl")
    private AdminServiceImpl adminService;

    @GetMapping("/main")
    public String mainPage(){
        System.out.println("helloworld2");
        return "index";
    }

    @ResponseBody
    @GetMapping("/users")
    public Result getAllStudent(@RequestParam("role")String role,@RequestParam("page")Integer page,@Nullable @RequestParam()Integer size){
        if("stu".equals(role)){
            return adminService.getStudents(page,size);
        }else if ("thr".equals(role)){
            return adminService.getTeachers(page,size);
        }

        throw new RuntimeException("非法的角色");
    }

    @ResponseBody
    @GetMapping("/delete/user")
    public Result deleteUser(@RequestParam("userno")String userNo){
        return adminService.deleteUser(userNo);
    }

    @ResponseBody
    @GetMapping("/update/student")
    public Result updateStudent(@RequestParam("userno")String userNo,
                                @RequestParam("username")String username,
                                @RequestParam("empID")String empID,
                                @RequestParam("role")String role){
        return adminService.updateStudent( userNo, username, empID, role);
    }

    @ResponseBody
    @GetMapping("/gen/teacher")
    public Result genTeacherRegKey(@RequestParam("nums")Integer nums){
        return adminService.genTeacherRegCode(nums);
    }


    @ResponseBody
    @GetMapping("/create/grade")
    public Result createGrade(@RequestParam("year")Short year,@RequestParam("name")String gradeName){
        return adminService.createGrade(year,gradeName);
    }

    @ResponseBody
    @GetMapping("/delete/grade")
    public Result deleteGrade(@RequestParam("grade")String grade){
        return adminService.deleteGrade(grade);
    }

    @ResponseBody
    @GetMapping("/create/class")
    public Result createClass(@RequestParam("grade")String gardeName,
                              @RequestParam("name")String className,
                              @RequestParam("dept")Integer deptId){
        return adminService.createClass(gardeName,className,deptId);
    }

    @ResponseBody
    @GetMapping("/create/course")
    public Result createCourse(@RequestParam("course")String courseName,
                               @RequestParam("hour")Integer courseHour,
                               @RequestParam("level")Integer level,
                               @RequestParam("isp")Integer isP,
                               @Nullable @RequestParam("deptId")Integer deptId){

        if (isP!=1){
            if (Objects.isNull(deptId))
                return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"当课程不是公共课时，需要为课程分配系",deptId);

            return adminService.createCourse(courseName,courseHour,level,false,deptId);
        }
        return adminService.createCourse(courseName,courseHour,level);
    }

    @ResponseBody
    @GetMapping("/course")
    public Result getCourse(){

        return adminService.getCourse();
    }


}
