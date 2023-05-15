package top.sleepnano.edusys.eduadminsys.service;

import org.springframework.stereotype.Service;
import top.sleepnano.edusys.eduadminsys.dto.PostCourse;
import top.sleepnano.edusys.eduadminsys.vo.Result;

@Service
public interface CrriculumService {

    Result createCourse(PostCourse postCourse);

    Result removeCourse(Integer courseID);

    Result updateCourse(PostCourse postCourse);
}
