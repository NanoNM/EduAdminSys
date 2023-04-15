package top.sleepnano.edusys.eduadminsys.controller;

import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.sleepnano.edusys.eduadminsys.service.impl.AdminServiceImpl;
import top.sleepnano.edusys.eduadminsys.vo.Result;

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
    @GetMapping("/students")
    public Result getAllStudent(@RequestParam("page")Integer page,@Nullable @RequestParam()Integer size){
        return adminService.getStudents(page,size);
    }

    @ResponseBody
    @GetMapping("/delete/student")
    public Result deleteStudent(@RequestParam("userno")String userNo){
        return adminService.deleteStudent(userNo);
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
}
