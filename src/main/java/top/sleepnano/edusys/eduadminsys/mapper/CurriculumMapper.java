package top.sleepnano.edusys.eduadminsys.mapper;

import top.sleepnano.edusys.eduadminsys.entity.Curriculum;

import java.util.List;

public interface CurriculumMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Curriculum record);

    int insertSelective(Curriculum record);

    Curriculum selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Curriculum record);

    int updateByPrimaryKey(Curriculum record);

    List<Curriculum> selectAll();
}