package top.sleepnano.edusys.eduadminsys.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * course_planning
 * @author 
 */
@Data
public class CoursePlanning implements Serializable {
    /**
     * 自增主键
     */
    private Integer id;

    /**
     * 学科名
     */
    private String courseName;

    /**
     * 周几上课
     */
    private Integer week;

    /**
     * 开始于
     */
    private Integer startClass;

    /**
     * 结束于
     */
    private Integer endClass;

    /**
     * 上课位置
     */
    private String local;

    /**
     * 上课的班级
     */
    private Integer classId;

    /**
     * 第几周的课
     */
    private Integer weekTimes;

    private static final long serialVersionUID = 1L;
}