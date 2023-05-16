package top.sleepnano.edusys.eduadminsys.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * stu_selected_coures
 * @author 
 */
@Data
public class StuSelectedCoures implements Serializable {
    /**
     * 自增主键
     */
    private Integer id;

    private Integer studentId;

    /**
     * 课程id
     */
    private Integer courseId;

    /**
     * 状态
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}