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
//        Curriculum curriculum = new Curriculum();
//        curriculum.setCourseName("courseName");
//        curriculum.setClassHour(50);
//        curriculum.setLevel(1);
//        curriculum.setPublicRequired(0);
//        int insert = curriculumMapper.insert(curriculum);
//        System.out.println("curriculum.getId() = " + curriculum.getId());
        List<Curriculum> curricula = curriculumMapper.selectAll();
        for (Curriculum curriculum : curricula) {
            System.out.println("curriculum = " + curriculum);
        }
    }

    @Test
    void testCreateCourse() {
        Result course = adminService.createCourse(
                "测试", 50, 2, false, 1
        );
        System.out.println(course);
    }
}