package top.sleepnano.edusys.eduadminsys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.sleepnano.edusys.eduadminsys.entity.DeptCourse;
import top.sleepnano.edusys.eduadminsys.mapper.DepartmentMapper;
import top.sleepnano.edusys.eduadminsys.mapper.DeptCourseMapper;
import top.sleepnano.edusys.eduadminsys.service.DeptService;
import top.sleepnano.edusys.eduadminsys.util.StatusCodeUtil;
import top.sleepnano.edusys.eduadminsys.util.VoBuilderUtil;
import top.sleepnano.edusys.eduadminsys.vo.Result;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    DeptCourseMapper deptCourseMapper;

    @Override
    public Result createDept(String deptName) {

        Integer insert = departmentMapper.insert(deptName);
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
}
