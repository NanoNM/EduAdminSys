package top.sleepnano.edusys.eduadminsys.service;

import org.springframework.stereotype.Service;
import top.sleepnano.edusys.eduadminsys.dto.PostLoginStudent;
import top.sleepnano.edusys.eduadminsys.vo.Result;

@Service
public interface StudentService extends BaseUserService{


    Result userLogin(PostLoginStudent postLoginStudent);

    Result updateStudentAddClassByUserNo(String userNo,String cls);

    Result getMyClassByUserNo(String userNo);

    Result getStudentsByClassID(Integer classid);

    Result getMoreStudentInfo(Integer stid);
}
