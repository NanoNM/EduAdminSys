package top.sleepnano.edusys.eduadminsys.service.impl;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.sleepnano.edusys.eduadminsys.entity.Curriculum;
import top.sleepnano.edusys.eduadminsys.mapper.CurriculumMapper;
import top.sleepnano.edusys.eduadminsys.util.StatusCodeUtil;
import top.sleepnano.edusys.eduadminsys.util.VoBuilderUtil;
import top.sleepnano.edusys.eduadminsys.vo.Result;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceImplTest {

    @Autowired
    CurriculumMapper curriculumMapper;
    @Autowired
    AdminServiceImpl adminService;

    @Test
    void createCourse() {

    }

    @Test
    void testCreateCourse() {
    }
}