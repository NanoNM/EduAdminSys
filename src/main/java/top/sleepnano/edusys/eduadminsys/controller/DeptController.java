package top.sleepnano.edusys.eduadminsys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.sleepnano.edusys.eduadminsys.service.DeptService;
import top.sleepnano.edusys.eduadminsys.vo.Result;

@RestController
@CrossOrigin
public class DeptController implements BaseController {

    @Autowired
    DeptService deptService;
    @GetMapping("/create/dept")
    public Result createDept(@RequestParam("deptname")String deptName){
        return  deptService.createDept(deptName);
    }

    @GetMapping("/depts")
    public Result getAllDepts(){
        return  deptService.getAllDepts();
    }

    @GetMapping("/deptbindcour")
    public Result deptBindCourse(@RequestParam("deptid")Integer deptid,
                                 @RequestParam("courid")Integer courid){
        return deptService.deptBindCourse(deptid,courid);
    }
}
