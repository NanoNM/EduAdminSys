package top.sleepnano.edusys.eduadminsys.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * exam_grade
 * @author 
 */
@Data
public class ExamGrade implements Serializable {
    /**
     * 考试信息id
     */
    private Integer examId;

    /**
     * 年级id
     */
    private String gradeId;

    private static final long serialVersionUID = 1L;
}