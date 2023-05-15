package top.sleepnano.edusys.eduadminsys.mapper;

import org.apache.ibatis.annotations.Select;
import top.sleepnano.edusys.eduadminsys.entity.CoursePlanning;

import java.util.List;

public interface CoursePlanningMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CoursePlanning record);

    int insertSelective(CoursePlanning record);

    CoursePlanning selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CoursePlanning record);

    int updateByPrimaryKey(CoursePlanning record);


    List<CoursePlanning> selectCoursePlanByClassID(Integer classid);

    List<CoursePlanning> selectCoursePlanByClassIDAndNowWeek(Integer classid, Integer nowweek);
}