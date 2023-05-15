package top.sleepnano.edusys.eduadminsys.mapper;

import io.swagger.models.auth.In;
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

    Integer insertClassByGardName(String gardeName,String className, Integer deptid);


    @Select("SELECT COUNT(id) from class")
    Object countClass();

    Integer countClassByGardeId(String name);

    List<Class> selectClassesByGardeIdNoPage(Integer gardeID);

    List<Class> selectClassesByGardeIdNoPageWithDept(Integer id, Integer deptid);

    List<Class> selectClassesByGardeId(Integer gardeID,Integer page,Integer pageSize);

    List<Class> selectClassesByGardeIdWithDept(Integer id, Integer deptid, int i, Integer size);

    Class selectClassesByClassID(Integer cls);

    Integer updateToBeCounselorByClassid(Integer id, Integer classid);

    Integer updateRetiredCounselorByClassid(Integer classid);

    Integer deleteByID(Integer classes);

    List<Class> selectClassesByDeptID(Integer id);

    Integer updateSetIdNULLByID(Integer id);
}
