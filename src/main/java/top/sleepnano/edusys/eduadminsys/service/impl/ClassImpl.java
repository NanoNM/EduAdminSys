package top.sleepnano.edusys.eduadminsys.service.impl;

import io.swagger.models.auth.In;
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

import java.util.*;

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
        // 可能有严重的性能问题
        grades.forEach(grade -> {
            resultGrades.add(new ResultGrade(grade,classMapper.selectClassesByGardeId(grade.getId(),(page - 1) * size, size)));
        });
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"搜索成功",resultGrades);
    }

    @Override
    public Result getClassesByName(String gradeName, Integer page, Integer size, Integer deptid) {

        size = 8;
        Grade grade  =  gradeMapper.selectGradeByName(gradeName);

        if (Objects.isNull(page)){
            if (Objects.isNull(deptid)){
                List<Class> classes = classMapper.selectClassesByGardeIdNoPage(grade.getId());
                ResultGrade resultGrade = new ResultGrade(grade,classes);
                Map<String,Object> result = new HashMap<>();
                result.put("data",resultGrade);
                result.put("pageTotal",0);
                return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"查询成功",result);
            }else {
                List<Class> classes = classMapper.selectClassesByGardeIdNoPageWithDept(grade.getId(),deptid);
                ResultGrade resultGrade = new ResultGrade(grade,classes);
                Map<String,Object> result = new HashMap<>();
                result.put("data",resultGrade);
                result.put("pageTotal",0);
                return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"查询成功",result);
            }


        }
        if (Objects.isNull(deptid)) {
            List<Class> classes = classMapper.selectClassesByGardeId(grade.getId(), (page - 1) * size, size);
            ResultGrade resultGrade = new ResultGrade(grade, classes);
            Map<String, Object> result = new HashMap<>();
            result.put("data", resultGrade);
            result.put("pageTotal", classMapper.countClassByGardeId(gradeName));
            return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS, "查询成功", result);
        }else {
            List<Class> classes = classMapper.selectClassesByGardeIdWithDept(grade.getId(),deptid, (page - 1) * size, size);
            ResultGrade resultGrade = new ResultGrade(grade, classes);
            Map<String, Object> result = new HashMap<>();
            result.put("data", resultGrade);
            result.put("pageTotal", classMapper.countClassByGardeId(gradeName));
            return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS, "查询成功", result);
        }
    }
}
