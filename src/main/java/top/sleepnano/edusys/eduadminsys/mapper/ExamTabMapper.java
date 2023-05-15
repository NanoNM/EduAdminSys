package top.sleepnano.edusys.eduadminsys.mapper;

import top.sleepnano.edusys.eduadminsys.entity.ExamTab;

import java.util.List;

public interface ExamTabMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ExamTab record);

    int insertSelective(ExamTab record);

    ExamTab selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ExamTab record);

    int updateByPrimaryKey(ExamTab record);

    List<ExamTab> selectByStudentId(String studentid);

    List<ExamTab> selectAll();
}