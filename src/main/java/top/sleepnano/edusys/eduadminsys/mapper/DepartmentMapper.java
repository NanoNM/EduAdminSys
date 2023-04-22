package top.sleepnano.edusys.eduadminsys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.sleepnano.edusys.eduadminsys.entity.Department;

import java.util.List;

public interface DepartmentMapper extends BaseMapper<Department> {
    int insert(String deptName);

    List<Department> selectAllDepts();
}