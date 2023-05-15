package top.sleepnano.edusys.eduadminsys.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * exam_class
 * @author 
 */
@Data
public class ExamClass implements Serializable {
    private Integer examId;

    private Integer classesId;

    private static final long serialVersionUID = 1L;
}