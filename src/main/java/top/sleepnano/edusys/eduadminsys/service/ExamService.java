package top.sleepnano.edusys.eduadminsys.service;

import org.springframework.stereotype.Service;
import top.sleepnano.edusys.eduadminsys.vo.Result;

@Service
public interface ExamService {
    Result createEaxmWithGradeAndDeptid(String examName, String local, String time, String grade, String deptid);

    Result createEaxmWithGrade(String examName, String local, String time, String grade);

    Result createEaxmWithClassID(String examName, String local, String time, String classID);

    Result getAllExamNotification();

    Result getDeptExamNotification(String grade, Integer deptid);

    Result getGradeExamNotification(String grade);

    Result getClassExamNotification(Integer classID);

    Result deleteExamNotification(Integer examID);
}
