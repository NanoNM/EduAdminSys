package top.sleepnano.edusys.eduadminsys.service;

import org.springframework.stereotype.Service;
import top.sleepnano.edusys.eduadminsys.vo.Result;

@Service
public interface DeptService {
    Result createDept(String deptName);

    Result getAllDepts();

    Result deptBindCourse(Integer deptid, Integer courid);
}
