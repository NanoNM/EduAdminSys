package top.sleepnano.edusys.eduadminsys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.sleepnano.edusys.eduadminsys.entity.ExamClass;
import top.sleepnano.edusys.eduadminsys.entity.ExamDept;
import top.sleepnano.edusys.eduadminsys.entity.ExamGrade;
import top.sleepnano.edusys.eduadminsys.entity.ExamNotification;
import top.sleepnano.edusys.eduadminsys.mapper.ExamClassMapper;
import top.sleepnano.edusys.eduadminsys.mapper.ExamDeptMapper;
import top.sleepnano.edusys.eduadminsys.mapper.ExamGradeMapper;
import top.sleepnano.edusys.eduadminsys.mapper.ExamNotificationMapper;
import top.sleepnano.edusys.eduadminsys.service.ExamService;
import top.sleepnano.edusys.eduadminsys.util.StatusCodeUtil;
import top.sleepnano.edusys.eduadminsys.util.VoBuilderUtil;
import top.sleepnano.edusys.eduadminsys.vo.Result;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    ExamNotificationMapper examNotificationMapper;
    @Autowired
    ExamDeptMapper examDeptMapper;
    @Autowired
    ExamGradeMapper examGradeMapper;

    @Autowired
    ExamClassMapper examClassMapper;

    @Override
    public Result createEaxmWithGradeAndDeptid(String examName, String local, String time, String grade, String deptid) {
        ExamNotification examNotification = genExamNotification(examName, local, time);
        ExamDept examDept = new ExamDept();
        examDept.setExamId(examNotification.getId());
        examDept.setDeptId(Integer.valueOf(deptid));
        examDept.setGradeId(grade);
        examDeptMapper.insert(examDept);

        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"创建系级别考试通知成功！",null);
    }

    @Override
    public Result createEaxmWithGrade(String examName, String local, String time, String grade) {
        ExamNotification examNotification = genExamNotification(examName, local, time);
        ExamGrade examGrade = new ExamGrade();
        examGrade.setExamId(examNotification.getId());
        examGrade.setGradeId(grade);
        examGradeMapper.insert(examGrade);
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"创建年级级别考试通知成功！",null);
    }

    @Override
    public Result createEaxmWithClassID(String examName, String local, String time, String classID) {
        ExamNotification examNotification = genExamNotification(examName, local, time);
        ExamClass examClass = new ExamClass();
        examClass.setExamId(examNotification.getId());
        examClass.setClassesId(Integer.valueOf(classID));
        examClassMapper.insert(examClass);

        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"创建班级级别考试通知成功！",null);
    }

    private ExamNotification genExamNotification(String examName, String local, String time) {
        ExamNotification examNotification = new ExamNotification();
        examNotification.setName(examName);
        examNotification.setExamLocal(local);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date newTime = format.parse(time);
            Instant instant = newTime.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();

            LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
            examNotification.setStartTime(localDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        examNotificationMapper.insert(examNotification);
        return examNotification;
    }

    @Override
    public Result getAllExamNotification() {
        List<ExamNotification> examNotifications = examDeptMapper.selectAllExam();
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"查询成功",examNotifications);
    }

    @Override
    public Result getDeptExamNotification(String grade, Integer deptid) {
        List<ExamDept> examDepts = examDeptMapper.selectDeptExam(grade,deptid);
        List<ExamNotification> examNotifications = new ArrayList<>();
        examDepts.forEach(examDept -> {
            ExamNotification examNotification = examNotificationMapper.selectByPrimaryKey(examDept.getExamId());
            examNotifications.add(examNotification);
        });
//
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"系级别考试查询成功",examNotifications);
    }

    @Override
    public Result getGradeExamNotification(String grade) {
        List<ExamDept> examDepts = examDeptMapper.selectGradeExam(grade);
        List<ExamNotification> examNotifications = new ArrayList<>();
        examDepts.forEach(examDept -> {
            ExamNotification examNotification = examNotificationMapper.selectByPrimaryKey(examDept.getExamId());
            examNotifications.add(examNotification);
        });
//
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"年级级别考试查询成功",examNotifications);
    }

    @Override
    public Result getClassExamNotification(Integer classID) {
        List<ExamDept> examDepts = examDeptMapper.selectClassIDExam(classID);
        List<ExamNotification> examNotifications = new ArrayList<>();
        examDepts.forEach(examDept -> {
            ExamNotification examNotification = examNotificationMapper.selectByPrimaryKey(examDept.getExamId());
            examNotifications.add(examNotification);
        });
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"班级级别考试查询成功",examNotifications);
    }

    @Override
    public Result deleteExamNotification(Integer examID) {
        deleteFormTable(examID);
        int i = examNotificationMapper.deleteByPrimaryKey(examID);
        if (i > 0) {
            return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"尝试删除成功", i);
        }
        return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"尝试删除失败", i);
    }

    @Transactional
    void deleteFormTable(Integer examID){
        examClassMapper.deleteByExamID(examID);
        examGradeMapper.deleteByExamID(examID);
        examDeptMapper. deleteByExamID(examID);
    }
}
