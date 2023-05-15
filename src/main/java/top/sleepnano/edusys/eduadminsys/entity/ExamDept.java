package top.sleepnano.edusys.eduadminsys.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * exam_dept
 * @author 
 */
@Data
public class ExamDept implements Serializable {
    private Integer examId;

    private Integer deptId;

    private String gradeId;

    private static final long serialVersionUID = 1L;
}