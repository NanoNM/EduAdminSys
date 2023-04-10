package top.sleepnano.edusys.eduadminsys.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.sleepnano.edusys.eduadminsys.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
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

    User selectUserByUserNo(String userNo);

    User selectUserByEmpID(String empID);

    Integer updateLastLoginDate(String userNo);

    @Select("Select name,user_no,employee_id,create_time,modify_time,last_time,role from user where role like 'student:%' limit #{page},#{pageSize}")
    List<User> selectUserByPage(Integer page, Integer pageSize);
    @Select("Select count(id) from user where role like 'student:%'")
    Integer studentCount();
    @Delete("Delete from user where user_no = #{userNo}")
    Integer deleteUserByUserNo(String userNo);
}

