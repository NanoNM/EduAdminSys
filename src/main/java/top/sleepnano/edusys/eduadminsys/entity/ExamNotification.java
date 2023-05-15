package top.sleepnano.edusys.eduadminsys.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

/**
 * exam_notification
 * @author 
 */
@Data
public class ExamNotification implements Serializable {
    /**
     * 唯一主键
     */
    private Integer id;

    /**
     * 相关考试名称
     */
    private String name;

    /**
     * 考试位置
     */
    private String examLocal;

    /**
     * 考试开始时间
     */
    private LocalDateTime startTime;

    private static final long serialVersionUID = 1L;
}