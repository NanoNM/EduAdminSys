package top.sleepnano.edusys.eduadminsys.service;

import top.sleepnano.edusys.eduadminsys.dto.PostRegUser;
import top.sleepnano.edusys.eduadminsys.vo.Result;

public interface AdminService extends BaseUserService{
    Result userReg(PostRegUser postRegStudent);
    Result getStudents(Integer num,Integer pageSize);

    Result getTeachers(Integer num,Integer pageSize);

    Result deleteUser(String userNo);

    Result updateStudent(String userNo,String username,String empID,String role);

    Result genTeacherRegCode(Integer nums);

    Result createGrade(Short year, String gradeName);

    Result createClass(String garde, String className, Integer deptId);


    Result getGrades(Integer page, Integer pageSize);

    Result getGradesByStatus(String status);

    Result deleteGrade(String grade);

    Result createCourse(String courseName, Integer courseHour, Integer level);

    Result createCourse(String courseName, Integer courseHour, Integer level, boolean b, Integer deptId);

    Result getCourse();

    Result getTeacherRegCode();
}
