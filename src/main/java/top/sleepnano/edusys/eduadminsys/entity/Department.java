package top.sleepnano.edusys.eduadminsys.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * department
 * @author 
 */
@Data
public class Department implements Serializable {
    /**
     * 自增主键
     */
    private Integer id;

    /**
     * 院系名称
     */
    private String deptName;

    /**
     * 学制
     */
    private Object eduSys;

    private Date createTiem;

    private Date modifyTime;

    private static final long serialVersionUID = 1L;
}