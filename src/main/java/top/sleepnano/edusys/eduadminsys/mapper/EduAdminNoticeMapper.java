package top.sleepnano.edusys.eduadminsys.mapper;

import top.sleepnano.edusys.eduadminsys.entity.EduAdminNotice;

import java.util.List;

public interface EduAdminNoticeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EduAdminNotice record);

    int insertSelective(EduAdminNotice record);

    EduAdminNotice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EduAdminNotice record);

    int updateByPrimaryKey(EduAdminNotice record);

    List<EduAdminNotice> selectAll();
}