package top.sleepnano.edusys.eduadminsys.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * teacher_class
 * @author 
 */
@Data
public class TeacherClass implements Serializable {
    private Integer id;

    private Integer teacherId;

    private Integer classId;

    private static final long serialVersionUID = 1L;
}