package top.sleepnano.edusys.eduadminsys.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * stu_info
 * @author 
 */
@Data
public class StuInfo implements Serializable {
    /**
     * 自增主键
     */
    private Integer id;

    /**
     * 学生名称
     */
    private String stuName;

    /**
     * 性别
     */
    private String stuGender;

    /**
     * 出生日期
     */
    private LocalDateTime stuBirthday;

    /**
     * 民族
     */
    private String stuNation;

    /**
     * 证件号码
     */
    private String stuId;

    /**
     * 院校
     */
    private String stuSchool;

    /**
     * 层次
     */
    private String levels;

    /**
     * 专业
     */
    private String major;

    private String shoolId;

    /**
     * 入学时间
     */
    private LocalDateTime enrollmentTime;

    /**
     * 学籍状态
     */
    private String status;

    /**
     * 形式
     */
    private String form;

    private static final long serialVersionUID = 1L;
}