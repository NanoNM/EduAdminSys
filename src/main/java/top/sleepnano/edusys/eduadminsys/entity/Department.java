package top.sleepnano.edusys.eduadminsys.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *  department
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

    private Date createTiem;

    private Date modifyTime;

    private static final long serialVersionUID = 1L;
}