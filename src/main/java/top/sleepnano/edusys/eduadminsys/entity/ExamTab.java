package top.sleepnano.edusys.eduadminsys.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * exam_tab
 * @author 
 */
@Data
public class ExamTab implements Serializable {
    /**
     * 唯一主键
     */
    private Integer id;

    /**
     * 考试名
     */
    private String name;

    /**
     * 成绩
     */
    private Double score;

    /**
     * 学生id
     */
    private Integer studentId;

    /**
     * 阅卷老师
     */
    private String examMarker;

    private static final long serialVersionUID = 1L;
}