package top.sleepnano.edusys.eduadminsys.mapper;

import top.sleepnano.edusys.eduadminsys.entity.DeptCourse;

public interface DeptCourseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeptCourse record);

    int insertSelective(DeptCourse record);

    DeptCourse selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeptCourse record);

    int updateByPrimaryKey(DeptCourse record);
}