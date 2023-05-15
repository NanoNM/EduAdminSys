package top.sleepnano.edusys.eduadminsys.mapper;

import top.sleepnano.edusys.eduadminsys.entity.ExamNotification;

public interface ExamNotificationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ExamNotification record);

    int insertSelective(ExamNotification record);

    ExamNotification selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ExamNotification record);

    int updateByPrimaryKey(ExamNotification record);
}