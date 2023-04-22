package top.sleepnano.edusys.eduadminsys.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * dept_course
 * @author 
 */
@Data
public class DeptCourse implements Serializable {
    /**
     * 自增主键
     */
    private Integer id;

    /**
     * 系id
     */
    private Integer deptId;

    /**
     * 学科id
     */
    private Integer courseId;

    private static final long serialVersionUID = 1L;
}