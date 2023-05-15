package top.sleepnano.edusys.eduadminsys.mapper;

import top.sleepnano.edusys.eduadminsys.entity.ExamDept;
import top.sleepnano.edusys.eduadminsys.entity.ExamNotification;

import java.util.List;

public interface ExamDeptMapper {
    int insert(ExamDept record);

    int insertSelective(ExamDept record);

    List<ExamNotification> selectAllExam();

    List<ExamDept> selectDeptExam(String grade, Integer deptid);

    List<ExamDept> selectGradeExam(String grade);

    List<ExamDept> selectClassIDExam(Integer classID);

    void deleteByExamID(Integer examID);
}