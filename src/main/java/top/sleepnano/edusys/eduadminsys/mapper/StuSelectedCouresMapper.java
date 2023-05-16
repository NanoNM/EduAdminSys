package top.sleepnano.edusys.eduadminsys.mapper;

import top.sleepnano.edusys.eduadminsys.entity.StuSelectedCoures;

import java.util.List;

public interface StuSelectedCouresMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StuSelectedCoures record);

    int insertSelective(StuSelectedCoures record);

    StuSelectedCoures selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StuSelectedCoures record);

    int updateByPrimaryKey(StuSelectedCoures record);

    List<StuSelectedCoures> selectByStuid(Integer stuid);

    Integer deleteRejectedByStuid(Integer stuid);
}