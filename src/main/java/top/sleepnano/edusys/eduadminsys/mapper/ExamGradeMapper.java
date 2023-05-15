package top.sleepnano.edusys.eduadminsys.mapper;

import top.sleepnano.edusys.eduadminsys.entity.ExamGrade;

public interface ExamGradeMapper {
    int insert(ExamGrade record);

    int insertSelective(ExamGrade record);

    void deleteByExamID(Integer examID);
}