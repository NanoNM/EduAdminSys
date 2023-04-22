package top.sleepnano.edusys.eduadminsys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2023-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
@ApiModel(value="User对象", description="")
public class AllUserInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户名称")
    private String name;

    private String password;

    @ApiModelProperty(value = "用户随机编号")
    private String userNo;

    @ApiModelProperty(value = "职位编号 学生则为学号")
    private String employeeId;

    private String regKey;

    @ApiModelProperty(value = "用户创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "用户修改时间")
    private LocalDateTime modifyTime;

    @ApiModelProperty(value = "最后登录时间")
    private LocalDateTime lastTime;

    @ApiModelProperty(value = "用户权限组")
    private String role;

    @ApiModelProperty(value = "班级id 用于绑定班级")
    private Integer classId;

    private String className;

    private String deptName;

    private String gradeName;


}
