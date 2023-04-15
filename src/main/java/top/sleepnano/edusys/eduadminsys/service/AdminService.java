package top.sleepnano.edusys.eduadminsys.service;

import top.sleepnano.edusys.eduadminsys.dto.PostRegUser;
import top.sleepnano.edusys.eduadminsys.vo.Result;

public interface AdminService extends BaseUserService{
    Result userReg(PostRegUser postRegStudent);
    Result getStudents(Integer num,Integer pageSize);

    Result deleteStudent(String userNo);

    Result updateStudent(String userNo,String username,String empID,String role);

    Result genTeacherRegCode(Integer nums);
}
