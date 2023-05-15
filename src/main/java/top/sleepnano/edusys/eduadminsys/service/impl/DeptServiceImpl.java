package top.sleepnano.edusys.eduadminsys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.sleepnano.edusys.eduadminsys.entity.Class;
import top.sleepnano.edusys.eduadminsys.entity.DeptCourse;
import top.sleepnano.edusys.eduadminsys.mapper.ClassMapper;
import top.sleepnano.edusys.eduadminsys.mapper.DepartmentMapper;
import top.sleepnano.edusys.eduadminsys.mapper.DeptCourseMapper;
import top.sleepnano.edusys.eduadminsys.service.DeptService;
import top.sleepnano.edusys.eduadminsys.util.StatusCodeUtil;
import top.sleepnano.edusys.eduadminsys.util.VoBuilderUtil;
import top.sleepnano.edusys.eduadminsys.vo.Result;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    DeptCourseMapper deptCourseMapper;
    @Autowired
    ClassMapper classMapper;

    @Override
    public Result createDept(String deptName, Integer edusys) {

        Integer insert = departmentMapper.insert(deptName,edusys);
        if (insert>0){
            return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"院系创建成功",deptName);
        }
        return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"院系创建失败",deptName);
    }

    @Override
    public Result getAllDepts() {

        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"查询成功", departmentMapper.selectAllDepts());
    }

    @Override
    public Result deptBindCourse(Integer deptid, Integer courid) {
        DeptCourse deptCourse = new DeptCourse();
        deptCourse.setDeptId(deptid);
        deptCourse.setCourseId(courid);
        int insert = deptCourseMapper.insert(deptCourse);
        if (insert>0){
            return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"系绑定学科成功",insert);
        }
        return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"系绑定学科失败",insert);
    }

    @Override
    public Result deleteDept(Integer id) {
        Integer integer1 = deptCourseMapper.deleteByDeptID(id);
        List<Class> classes = classMapper.selectClassesByDeptID(id);
        classes.forEach(it->{
            Integer i = classMapper.updateSetIdNULLByID(it.getId());
        });
        Integer integer = departmentMapper.deleteByDeptID(id);
        if (integer>0){
            return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"" +
                            "删除成功 有 "+ classes.size() +" " +
                            "各班级失去了它的系，有 "+ integer1 +" 个课程失去了它的系"
                    ,id);
        }else {
            return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"删除失败"
                    ,id);
        }

    }
}
