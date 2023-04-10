package top.sleepnano.edusys.eduadminsys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.sleepnano.edusys.eduadminsys.dto.PostLoginStudent;
import top.sleepnano.edusys.eduadminsys.dto.PostRegUser;
import top.sleepnano.edusys.eduadminsys.service.StudentService;
import top.sleepnano.edusys.eduadminsys.util.StatusCodeUtil;
import top.sleepnano.edusys.eduadminsys.util.VoBuilderUtil;
import top.sleepnano.edusys.eduadminsys.vo.Result;

@RestController
@RequestMapping("/student")
@CrossOrigin
public class StudentController extends UserBaseController {
    @Autowired
    StudentService studentService;

    @PostMapping("/reg")
    public Result studentRegister(@RequestBody PostRegUser postRegStudent){
        return studentService.userReg(postRegStudent);
    }

    @GetMapping("/exam/main")
    public Result studentGetExam(){
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"ok",null);
    }
}
