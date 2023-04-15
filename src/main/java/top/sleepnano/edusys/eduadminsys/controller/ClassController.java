package top.sleepnano.edusys.eduadminsys.controller;

import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.sleepnano.edusys.eduadminsys.service.ClassService;
import top.sleepnano.edusys.eduadminsys.vo.Result;

@RestController
@CrossOrigin
@RequestMapping("/class")
public class ClassController implements BaseController{
    @Autowired
    ClassService classService;

    @GetMapping("/classes")
    public Result getClasses(@Nullable @RequestParam("grade")String grade,@RequestParam("page")Integer page, @Nullable @RequestParam("size")Integer size){
        if (grade == null){
            return classService.getAllClasses(page,size);
        }else {
            return classService.getClassesByName(grade,page,size);
        }
    }
}
