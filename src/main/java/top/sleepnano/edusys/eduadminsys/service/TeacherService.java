package top.sleepnano.edusys.eduadminsys.service;

import top.sleepnano.edusys.eduadminsys.vo.Result;

public interface TeacherService extends BaseUserService{
    Result teacherBindClass(String userNo, String classid);

    Result getMyClassByUserNo(String userNo);

    Result joinClass(String userNo, String classid);

    Result toBeCounselor(String userNo, String classid);

    Result retiredCounselor(String userNo, String classid);

    Result deleteClass(String userNo, String classid);
}
