package top.sleepnano.edusys.eduadminsys.controller;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.sleepnano.edusys.eduadminsys.service.ClassService;
import top.sleepnano.edusys.eduadminsys.service.StudentService;
import top.sleepnano.edusys.eduadminsys.util.JwtUtil;
import top.sleepnano.edusys.eduadminsys.vo.Result;

@RestController
@CrossOrigin
@RequestMapping("/class")
public class ClassController implements BaseController{
    @Autowired
    ClassService classService;

    @Autowired
    StudentService studentService;

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
}
