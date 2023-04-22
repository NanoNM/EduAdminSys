package top.sleepnano.edusys.eduadminsys.mapper;

import top.sleepnano.edusys.eduadminsys.entity.Class;
import top.sleepnano.edusys.eduadminsys.entity.TeacherClass;

import java.util.List;

public interface TeacherClassMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TeacherClass record);

    int insertSelective(TeacherClass record);

    TeacherClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TeacherClass record);

    int updateByPrimaryKey(TeacherClass record);

    List<Class> selectTeacherClassesByTeacherId(Integer id);

    Integer deleteByUserIdAndClassid(Integer id, String classid);
}