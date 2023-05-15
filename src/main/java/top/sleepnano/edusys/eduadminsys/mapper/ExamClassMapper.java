package top.sleepnano.edusys.eduadminsys.mapper;

import top.sleepnano.edusys.eduadminsys.entity.ExamClass;

public interface ExamClassMapper {
    int insert(ExamClass record);

    int insertSelective(ExamClass record);

    void deleteByExamID(Integer examID);
}