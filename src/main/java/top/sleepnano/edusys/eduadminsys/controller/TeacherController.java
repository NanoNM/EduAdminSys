package top.sleepnano.edusys.eduadminsys.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import top.sleepnano.edusys.eduadminsys.dto.PostRegUser;
import top.sleepnano.edusys.eduadminsys.service.TeacherService;
import top.sleepnano.edusys.eduadminsys.util.JwtUtil;
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

    @GetMapping("/bindclass")
    public Result teacherBindClass(HttpServletRequest request, @RequestParam("classid") String classid) throws Exception {
        String token = request.getHeader("token");
        String userNo = null;
        userNo = JwtUtil.parseJWT(token).getSubject();
        System.out.println("userNo = " + userNo);
        return teacherService.teacherBindClass(userNo,classid);
    }

    @GetMapping("/getclass")
    public Result getMyClasses(HttpServletRequest request) throws Exception {
        String token = request.getHeader("token");
        String userNo = null;
        userNo = JwtUtil.parseJWT(token).getSubject();
        return teacherService.getMyClassByUserNo(userNo);
    }

    @GetMapping("/joinclass")
    public Result joinClass(HttpServletRequest request,@RequestParam("classid")String classid) throws Exception {
        String token = request.getHeader("token");
        String userNo = null;
        userNo = JwtUtil.parseJWT(token).getSubject();
        return teacherService.joinClass(userNo,classid);
    }

    @GetMapping("/regcor")
    public Result toBeCounselor(HttpServletRequest request,@RequestParam("classid")String classid) throws Exception {
        String token = request.getHeader("token");
        String userNo = null;
        userNo = JwtUtil.parseJWT(token).getSubject();
        return teacherService.toBeCounselor(userNo,classid);
    }

    @GetMapping("/retirecor")
    public Result retiredCounselor(HttpServletRequest request,@RequestParam("classid")String classid) throws Exception {
        String token = request.getHeader("token");
        String userNo = null;
        userNo = JwtUtil.parseJWT(token).getSubject();
        return teacherService.retiredCounselor(userNo,classid);
    }

    @GetMapping("/deleteClass")
    public Result deleteClass(HttpServletRequest request,@RequestParam("classid")String classid) throws Exception {
        String token = request.getHeader("token");
        String userNo = null;
        userNo = JwtUtil.parseJWT(token).getSubject();
        return teacherService.deleteClass(userNo,classid);
    }



}
