package top.sleepnano.edusys.eduadminsys.service;

import org.springframework.stereotype.Service;
import top.sleepnano.edusys.eduadminsys.vo.Result;

@Service
public interface ClassService {
    Result getAllClasses(Integer page,Integer size);

    Result getClassesByName(String grade,Integer page,Integer size,Integer deptid);
}
