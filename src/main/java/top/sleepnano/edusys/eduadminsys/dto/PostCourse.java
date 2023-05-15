package top.sleepnano.edusys.eduadminsys.dto;

import lombok.Data;
import top.sleepnano.edusys.eduadminsys.entity.Curriculum;

@Data
public class PostCourse {
    Curriculum curriculum;

    Integer[] deptids;
}
