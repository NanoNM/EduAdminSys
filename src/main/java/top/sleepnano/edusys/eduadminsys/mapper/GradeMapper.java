package top.sleepnano.edusys.eduadminsys.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import top.sleepnano.edusys.eduadminsys.entity.Grade;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 年级表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2023-04-15
 */
public interface GradeMapper extends BaseMapper<Grade> {

    Integer insertGrade(Short year, String gradeName);

    List<Grade> selectGrades();

    @Select("SELECT id,grade_name,grade_year,create_time,modify_time,status FROM grade WHERE grade_name = #{gradeName}")
    Grade selectGradeByName(String gradeName);

    List<Grade> selectGradesByPage(Integer page, Integer pageSize);

    @Select("SELECT COUNT(id) FROM grade")
    Integer gradeCount();

    @Select("SELECT id,grade_name,grade_year,create_time,modify_time,status FROM grade WHERE status=#{status} ORDER BY grade_name DESC")
    List<Grade> selectGradesByStatus(String status);

    Integer deleteGradeLink(String grade);
}
