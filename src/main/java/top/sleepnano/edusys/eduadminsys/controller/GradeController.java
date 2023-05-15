package top.sleepnano.edusys.eduadminsys.controller;

import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.sleepnano.edusys.eduadminsys.entity.Grade;
import top.sleepnano.edusys.eduadminsys.mapper.GradeMapper;
import top.sleepnano.edusys.eduadminsys.util.StatusCodeUtil;
import top.sleepnano.edusys.eduadminsys.util.VoBuilderUtil;
import top.sleepnano.edusys.eduadminsys.vo.Result;

import java.time.LocalDateTime;
import java.util.Objects;

@RestController
@RequestMapping("/grade")
public class GradeController implements BaseController{
    @Autowired
    GradeMapper gradeMapper;

    @GetMapping("/optime")
    public Result getOpTime(@Nullable @RequestParam("grade")String gradeName,
                            @Nullable @RequestParam("gradeid")Integer gradeID){
        if (Objects.isNull(gradeName)){
            Grade grade = gradeMapper.selectGradeByID(gradeID);
            LocalDateTime startingDate = grade.getStartingDate();
            String[] strings = {String.valueOf(startingDate.getYear()),String.valueOf(startingDate.getMonthValue()),String.valueOf(startingDate.getDayOfMonth())};
            return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"查询成功", strings);
        }
        Grade grade = gradeMapper.selectGradeByName(gradeName);
        LocalDateTime startingDate = grade.getStartingDate();
        String[] strings = {String.valueOf(startingDate.getYear()),String.valueOf(startingDate.getMonthValue()),String.valueOf(startingDate.getDayOfMonth())};
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"查询成功", strings);
    }

    @PostMapping("/update")
    public Result updateGrade(@RequestBody Grade grade){
        grade.setGradeYear(grade.getGradeYear());
        int i = gradeMapper.updateById(grade);
        if (i>0){
            return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"更新成功", grade);
        }
        return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"更新失败", grade);
    }




}
