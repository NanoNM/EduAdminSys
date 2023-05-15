package top.sleepnano.edusys.eduadminsys.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.sleepnano.edusys.eduadminsys.dto.PostRegUser;
import top.sleepnano.edusys.eduadminsys.entity.StuInfo;
import top.sleepnano.edusys.eduadminsys.mapper.StuInfoMapper;
import top.sleepnano.edusys.eduadminsys.service.StudentService;
import top.sleepnano.edusys.eduadminsys.util.JwtUtil;
import top.sleepnano.edusys.eduadminsys.util.StatusCodeUtil;
import top.sleepnano.edusys.eduadminsys.util.VoBuilderUtil;
import top.sleepnano.edusys.eduadminsys.vo.Result;

@RestController
@RequestMapping("/student")
@CrossOrigin
public class StudentController extends UserBaseController {
    @Autowired
    StudentService studentService;
    @Autowired
    StuInfoMapper stuInfoMapper;

    @PostMapping("/reg")
    public Result studentRegister(@RequestBody PostRegUser postRegStudent){
        return studentService.userReg(postRegStudent);
    }

    @GetMapping("/exam/main")
    public Result studentGetExam(){
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"ok",null);
    }


    @GetMapping("/getclass")
    public Result getMyClasses(HttpServletRequest request) throws Exception {
        String token = request.getHeader("token");
        String userNo = null;
        userNo = JwtUtil.parseJWT(token).getSubject();
        return studentService.getMyClassByUserNo(userNo);
    }

    @GetMapping("/getStudents")
    public Result getStudentByClassID(@RequestParam("classid") Integer classid){
        return studentService.getStudentsByClassID(classid);
    }

    @GetMapping("/moreInfo")
    public Result getMoreStudentInfo(@RequestParam("stid") Integer stid){
        return studentService.getMoreStudentInfo(stid);
    }

    @PostMapping ("/moreInfo/create")
    public Result insertMoreStudentInfo(@RequestBody StuInfo stuInfo){
        int insert = stuInfoMapper.insert(stuInfo);
        if (insert>0){
            return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"插入成功",stuInfo);
        }
        return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"插入失败",stuInfo);
    }

    @PostMapping ("/moreInfo/update")
    public Result updateMoreStudentInfo(@RequestBody StuInfo stuInfo){
        int i = stuInfoMapper.updateByPrimaryKey(stuInfo);
        if (i>0){
            return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"更新成功",stuInfo);
        }
        return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"更新失败",stuInfo);
    }
}
