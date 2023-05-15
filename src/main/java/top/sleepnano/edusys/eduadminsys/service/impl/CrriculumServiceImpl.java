package top.sleepnano.edusys.eduadminsys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.sleepnano.edusys.eduadminsys.dto.PostCourse;
import top.sleepnano.edusys.eduadminsys.entity.Curriculum;
import top.sleepnano.edusys.eduadminsys.entity.DeptCourse;
import top.sleepnano.edusys.eduadminsys.mapper.CurriculumMapper;
import top.sleepnano.edusys.eduadminsys.mapper.DeptCourseMapper;
import top.sleepnano.edusys.eduadminsys.service.CrriculumService;
import top.sleepnano.edusys.eduadminsys.util.StatusCodeUtil;
import top.sleepnano.edusys.eduadminsys.util.VoBuilderUtil;
import top.sleepnano.edusys.eduadminsys.vo.Result;

@Service
public class CrriculumServiceImpl implements CrriculumService {

    @Autowired
    CurriculumMapper curriculumMapper;
    @Autowired
    DeptCourseMapper deptCourseMapper;
    @Override
    @Transactional
    public Result createCourse(PostCourse postCourse) {
        Curriculum curriculum = postCourse.getCurriculum();
        int insert = curriculumMapper.insert(curriculum);
        if (insert<1){
            return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"课程创建失败",insert);
        }
        Integer[] deptid = postCourse.getDeptids();

        for (Integer integer : deptid) {
            DeptCourse deptCourse = new DeptCourse();
            deptCourse.setCourseId(curriculum.getId());
            deptCourse.setDeptId(integer);
            deptCourseMapper.insert(deptCourse);
        }

        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"新建课程成功",curriculum);
    }

    @Override
    @Transactional
    public Result removeCourse(Integer courseID) {
        Integer din = deptCourseMapper.deleteBycourseID(courseID);
        int i = curriculumMapper.deleteByPrimaryKey(courseID);
        if (i<1){
            return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"尝试删除失败",i);
        }
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"删除成功",i);
    }

    @Override
    @Transactional
    public Result updateCourse(PostCourse postCourse) {
        Curriculum curriculum = postCourse.getCurriculum();
        Integer[] deptid = postCourse.getDeptids();
        Integer din = deptCourseMapper.deleteBycourseID(curriculum.getId());
        for (Integer integer : deptid) {
            DeptCourse deptCourse = new DeptCourse();
            deptCourse.setCourseId(curriculum.getId());
            deptCourse.setDeptId(integer);
            deptCourseMapper.insert(deptCourse);
        }
        int i = curriculumMapper.updateByPrimaryKey(curriculum);
        if (i>0){
            return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"更新成功",curriculum);
        }
        return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"更新失败",curriculum);
    }
}
