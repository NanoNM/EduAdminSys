package top.sleepnano.edusys.eduadminsys.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * edu_admin_notice
 * @author 
 */
@Data
public class EduAdminNotice implements Serializable {
    /**
     * 自增主键
     */
    private Integer id;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告内容
     */
    private String content;

    /**
     * 发布时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}