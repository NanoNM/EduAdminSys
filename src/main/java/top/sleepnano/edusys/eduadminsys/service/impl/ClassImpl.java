package top.sleepnano.edusys.eduadminsys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.sleepnano.edusys.eduadminsys.dto.ResultGrade;
import top.sleepnano.edusys.eduadminsys.entity.Class;
import top.sleepnano.edusys.eduadminsys.entity.Grade;
import top.sleepnano.edusys.eduadminsys.mapper.ClassMapper;
import top.sleepnano.edusys.eduadminsys.mapper.GradeMapper;
import top.sleepnano.edusys.eduadminsys.service.ClassService;
import top.sleepnano.edusys.eduadminsys.util.StatusCodeUtil;
import top.sleepnano.edusys.eduadminsys.util.VoBuilderUtil;
import top.sleepnano.edusys.eduadminsys.vo.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClassImpl implements ClassService {
    @Autowired
    ClassMapper classMapper;
    @Autowired
    GradeMapper gradeMapper;
    @Override
    public Result getAllClasses(Integer page,Integer size) {
        List<ResultGrade> resultGrades =  new ArrayList<>();
        List<Grade> grades =  gradeMapper.selectGradesByPage(page,size);
        // 有很严重的性能问题
        grades.forEach(grade -> {
            resultGrades.add(new ResultGrade(grade,classMapper.selectClassesByGardeId(grade.getId(),(page - 1) * size, size)));
        });
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"搜索成功",resultGrades);
    }

    @Override
    public Result getClassesByName(String gradeName,Integer page,Integer size) {
        size = 8;
        Grade grade  =  gradeMapper.selectGradeByName(gradeName);
        List<Class> classes = classMapper.selectClassesByGardeId(grade.getId(),(page - 1) * size, size);
        ResultGrade resultGrade = new ResultGrade(grade,classes);
        Map<String,Object> result = new HashMap<>();
        result.put("data",resultGrade);
        result.put("pageTotal",classMapper.countClass());
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"查询成功",result);
    }
}
