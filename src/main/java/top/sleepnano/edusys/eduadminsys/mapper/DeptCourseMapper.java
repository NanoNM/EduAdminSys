package top.sleepnano.edusys.eduadminsys.mapper;

import top.sleepnano.edusys.eduadminsys.entity.DeptCourse;

import java.util.List;

public interface DeptCourseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeptCourse record);

    int insertSelective(DeptCourse record);

    DeptCourse selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeptCourse record);

    int updateByPrimaryKey(DeptCourse record);

    List<DeptCourse> selectByCurriculumId(Integer id);

    Integer deleteBycourseID(Integer ID);

    Integer deleteByDeptID(Integer id);
}