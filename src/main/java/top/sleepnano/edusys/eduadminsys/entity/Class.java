package top.sleepnano.edusys.eduadminsys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serial;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 班级表
 * </p>
 *
 * @author author
 * @since 2023-04-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("class")
@ApiModel(value="Class对象", description="班级表")
public class Class implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "班级名称")
    private String className;

    @ApiModelProperty(value = "班级年级 对应的是年级表年级id")
    private Integer classYear;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    private LocalDateTime modifyTime;


}
