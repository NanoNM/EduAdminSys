package top.sleepnano.edusys.eduadminsys.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.sleepnano.edusys.eduadminsys.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.UUID;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2023-04-08
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    Integer InsertIntoUser(String name, String encodePass, String uuid, String employeeID, String role);

    User selectUserByUserName(String username);
}
