package top.sleepnano.edusys.eduadminsys.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import top.sleepnano.edusys.eduadminsys.entity.AllUserInfo;
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
    Integer InsertIntoUser(String name, String encodePass, String uuid, String employeeID,String regKey, String role);

    User selectUserByUserName(String username);

    User selectUserByUserNo(String userNo);

    User selectUserByEmpID(String empID);

    Integer updateLastLoginDate(String userNo);

    @Select("Select name,user_no,employee_id,reg_key,create_time,modify_time,last_time,role,class_id from user where role like 'student:%' limit #{page},#{pageSize}")
    List<User> selectUserByPage(Integer page, Integer pageSize);
    @Select("Select count(id) from user where role like 'student:%'")
    Integer studentCount();

    @Select("Select count(id) from user where role like #{role} and employee_id = #{empID}")
    Integer userCountByRoleAndEmpID(String role,String empID);
    @Delete("Delete from user where user_no = #{userNo}")
    Integer deleteUserByUserNo(String userNo);

    @Update("UPDATE user SET name=#{username},employee_id=#{empID},role=#{role} WHERE user_no = #{userNo}")
    Integer updateUserByUserNo(String userNo, String username, String empID, String role);

    @Select("Select name,user_no,employee_id,reg_key,create_time,modify_time,last_time,role,class_id from user where role like 'teacher:%' limit #{page},#{pageSize}")
    List<User> selectTeacherByPage(Integer page, Integer pageSize);

    @Select("Select count(id) from user where role like 'teacher:%'")
    Integer teacherCount();

    Integer updateStudentAddClassByUserNo(String userNo,String cls);

    void updateLastModifyByUserNo(String userNo);

    AllUserInfo selectAllUserInfoByUserName(String name);
}

