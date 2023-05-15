package top.sleepnano.edusys.eduadminsys.controller;

import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import top.sleepnano.edusys.eduadminsys.entity.ExamTab;
import top.sleepnano.edusys.eduadminsys.mapper.ExamTabMapper;
import top.sleepnano.edusys.eduadminsys.service.ExamService;
import top.sleepnano.edusys.eduadminsys.util.StatusCodeUtil;
import top.sleepnano.edusys.eduadminsys.util.VoBuilderUtil;
import top.sleepnano.edusys.eduadminsys.vo.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/exam")
public class ExamController implements BaseController {
    @Autowired
    ExamService examService;
    @Autowired
    ExamTabMapper examTabMapper;

    @GetMapping("/exams")
    public Result getAllExamNotification(){
        return examService.getAllExamNotification();
    }


    @GetMapping("/delete")
    @Transactional
    public Result deleteExamNotification(@RequestParam("examID")Integer examID){
        return examService.deleteExamNotification(examID);
    }


    @GetMapping("/exam")
    public Result getExamNotificationByParam(@Nullable @RequestParam("grade")String grade,
                                             @Nullable @RequestParam("dept")Integer deptid,
                                             @Nullable @RequestParam("class")Integer classID){

        if (grade!=null && deptid!=null && classID == null){
            return examService.getDeptExamNotification(grade,deptid);
        }else if (grade != null && deptid == null && classID == null){
            return examService.getGradeExamNotification(grade);
        }else if (classID!=null){
            return examService.getClassExamNotification(classID);
        }else {
            return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"不允许的操作",null);
        }

    }

    @GetMapping("/create")
    public Result createExamNotification(@RequestParam("name")String examName,
                                         @RequestParam("local")String local,
                                         @RequestParam("time")String time,

                                         @Nullable @RequestParam("grade")String grade,
                                         @Nullable @RequestParam("deptid")String deptid,
                                         @Nullable @RequestParam("class")String classID
                                         ){

        if (grade!=null && deptid!=null && classID == null){
//            return examService.createEaxmWithGradeAndDeptid(examName,local,time,grade,deptid);
            return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"API暂停使用",null);
        }else if (grade != null && deptid == null && classID == null){
//            return examService.createEaxmWithGrade(examName,local,time,grade);
            return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"API暂停使用",null);
        }else if (classID!=null){
            return examService.createEaxmWithClassID(examName,local,time,classID);
        }else {
            return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"不允许的操作",null);
        }
    }

    @GetMapping("/getResults")
    public Result getExamResults(@Nullable @RequestParam("studentid")String studentid){
        List<ExamTab> examTabs;
        if (Objects.isNull(studentid)){
            examTabs = examTabMapper.selectAll();
        }else {
            examTabs =  examTabMapper.selectByStudentId(studentid);
        }
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"查询成功",examTabs);
    }

    @PostMapping("/insertResults")
    public Result insertExamResults(@RequestBody ExamTab examTab){
        int examTabs =  examTabMapper.insert(examTab);
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"插入成功",examTabs);
    }

    @PostMapping("/updateResults")
    public Result updateExamResults(@RequestBody ExamTab examTab){
        int examTabs =  examTabMapper.updateByPrimaryKey(examTab);
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"更新成功",examTabs);
    }

    @GetMapping("/deleteResults")
    public Result deleteExamResults(@RequestParam("id") Integer id){
        int examTabs =  examTabMapper.deleteByPrimaryKey(id);
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"删除成功",examTabs);
    }
}
