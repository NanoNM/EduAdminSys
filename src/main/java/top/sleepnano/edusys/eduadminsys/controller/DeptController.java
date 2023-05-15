package top.sleepnano.edusys.eduadminsys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.sleepnano.edusys.eduadminsys.entity.Department;
import top.sleepnano.edusys.eduadminsys.mapper.DepartmentMapper;
import top.sleepnano.edusys.eduadminsys.service.DeptService;
import top.sleepnano.edusys.eduadminsys.util.StatusCodeUtil;
import top.sleepnano.edusys.eduadminsys.util.VoBuilderUtil;
import top.sleepnano.edusys.eduadminsys.vo.Result;

@RestController
@CrossOrigin
public class DeptController implements BaseController {

    @Autowired
    DeptService deptService;

    @Autowired
    DepartmentMapper departmentMapper;
    @GetMapping("/create/dept")
    public Result createDept(@RequestParam("deptname")String deptName,
                             @RequestParam("edusys")Integer edusys){
        return  deptService.createDept(deptName,edusys);
    }

    @GetMapping("/delete/dept")
    public Result deleteDept(@RequestParam("id")Integer id){
        return  deptService.deleteDept(id);
    }

    @GetMapping("/depts")
    public Result getAllDepts(){
        return  deptService.getAllDepts();
    }

    @GetMapping("/dept")
    public Result getDeptByID(@RequestParam("id")Integer id){
        Department department = departmentMapper.selectDeptByID(id);
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"查询成功",department);
    }

    @GetMapping("/deptbindcour")
    public Result deptBindCourse(@RequestParam("deptid")Integer deptid,
                                 @RequestParam("courid")Integer courid){
        return deptService.deptBindCourse(deptid,courid);
    }

    @GetMapping("/updatedept")
    public Result updateDept(@RequestParam("deptid")Integer deptid,
                                 @RequestParam("edusys")Integer edusys){
        Integer integer = departmentMapper.updateDeptEduSys(deptid,edusys);
        if (integer>0){
           return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"更新成功",integer);
        }
        return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"更新失败",integer);
    }
}
