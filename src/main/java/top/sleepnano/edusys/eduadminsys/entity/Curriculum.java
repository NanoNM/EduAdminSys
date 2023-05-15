package top.sleepnano.edusys.eduadminsys.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * curriculum
 * @author 
 */
@Data
public class Curriculum implements Serializable {
    /**
     * 自增主键
     */
    private Integer id;

    private String courseName;

    /**
     * 总课时
     */
    private Integer classHour;

    /**
     * 对应年级
     */
    private Integer level;

    /**
     * 是否为公共必修 0为不是
     */
    private Integer publicRequired;

    private Integer credit;

    /**
     * 从属系id
     */
    private Integer deptId;

    private static final long serialVersionUID = 1L;
}