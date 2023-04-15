package top.sleepnano.edusys.eduadminsys.mapper;

import org.apache.ibatis.annotations.Select;
import top.sleepnano.edusys.eduadminsys.entity.Class;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 班级表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2023-04-15
 */
public interface ClassMapper extends BaseMapper<Class> {

    Integer insertClassByGardName(String gardeName,String className);

    List<Class> selectClassesByGardeId(Integer gardeID,Integer page,Integer pageSize);

    @Select("SELECT COUNT(id) from class")
    Object countClass();
}
