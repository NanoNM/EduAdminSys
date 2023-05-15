package top.sleepnano.edusys.eduadminsys.controller;

import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.sleepnano.edusys.eduadminsys.dto.PostCourse;
import top.sleepnano.edusys.eduadminsys.entity.CoursePlanning;
import top.sleepnano.edusys.eduadminsys.mapper.CoursePlanningMapper;
import top.sleepnano.edusys.eduadminsys.service.CrriculumService;
import top.sleepnano.edusys.eduadminsys.util.StatusCodeUtil;
import top.sleepnano.edusys.eduadminsys.util.VoBuilderUtil;
import top.sleepnano.edusys.eduadminsys.vo.Result;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController implements BaseController{

    @Autowired
    CrriculumService crriculumService;
    @Autowired
    CoursePlanningMapper coursePlanningMapper;

    @GetMapping("/insert")
    public Result insertCourseByClassID(@RequestParam("name")String name,
                                        @RequestParam("week")Integer week,
                                        @RequestParam("sc")Integer startClass,
                                        @RequestParam("ec")Integer endClass,
                                        @RequestParam("local")String local,
                                        @RequestParam("classid")Integer classid,
                                        @RequestParam("weektimes")Integer weekTimes){
        CoursePlanning coursePlanning = new CoursePlanning();
        coursePlanning.setCourseName(name);
        coursePlanning.setWeek(week);
        coursePlanning.setStartClass(startClass);
        coursePlanning.setEndClass(endClass);
        coursePlanning.setLocal(local);
        coursePlanning.setClassId(classid);
        coursePlanning.setWeekTimes(weekTimes);
        int insert = coursePlanningMapper.insert(coursePlanning);
        if (insert>0){
            return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"获取成功",coursePlanning);
        }
        return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"插入失败",null);
    }

    @GetMapping("/get")
    public Result getCourseByClassID(@RequestParam("classid")Integer classid,
                                     @Nullable @RequestParam("nowweek")Integer nowweek){
        List<CoursePlanning> coursePlannings = coursePlanningMapper.selectCoursePlanByClassID(classid);
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"获取成功",coursePlannings);
    }

    @GetMapping("/delete")
    public Result deleteCourseByClassID(@RequestParam("id")Integer id) {
        int coursePlannings = coursePlanningMapper.deleteByPrimaryKey(id);
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"删除成功",coursePlannings);
    }


    @PostMapping("/create")
    public Result createACourse(@RequestBody PostCourse postCourse){
        return crriculumService.createCourse(postCourse);
    }

    @GetMapping("/remove")
    public Result removeACourse(@RequestParam("id")Integer courseID){
        return crriculumService.removeCourse(courseID);
    }

    @PostMapping("/update")
    public Result updateACourse(@RequestBody PostCourse postCourse){
//        System.out.println("postCourse = " + postCourse);
        return crriculumService.updateCourse(postCourse);
//        return null;
    }

}
