package top.sleepnano.edusys.eduadminsys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import top.sleepnano.edusys.eduadminsys.dto.PostRegUser;
import top.sleepnano.edusys.eduadminsys.service.TeacherService;
import top.sleepnano.edusys.eduadminsys.vo.Result;

@RestController
@RequestMapping("/teacher")
@CrossOrigin
public class TeacherController extends UserBaseController{
    @Autowired
    @Qualifier("teacherServiceImpl")
    TeacherService teacherService;
    @PostMapping("/reg")
    public Result teacherRegister(@RequestBody PostRegUser postRegUser){
        return teacherService.userReg(postRegUser);
    }
}
