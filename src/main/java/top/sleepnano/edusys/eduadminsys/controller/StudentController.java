package top.sleepnano.edusys.eduadminsys.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import top.sleepnano.edusys.eduadminsys.dto.PostRegUser;
import top.sleepnano.edusys.eduadminsys.entity.StuInfo;
import top.sleepnano.edusys.eduadminsys.entity.StuSelectedCoures;
import top.sleepnano.edusys.eduadminsys.entity.User;
import top.sleepnano.edusys.eduadminsys.mapper.CurriculumMapper;
import top.sleepnano.edusys.eduadminsys.mapper.StuInfoMapper;
import top.sleepnano.edusys.eduadminsys.mapper.StuSelectedCouresMapper;
import top.sleepnano.edusys.eduadminsys.service.StudentService;
import top.sleepnano.edusys.eduadminsys.util.JwtUtil;
import top.sleepnano.edusys.eduadminsys.util.StatusCodeUtil;
import top.sleepnano.edusys.eduadminsys.util.VoBuilderUtil;
import top.sleepnano.edusys.eduadminsys.vo.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
@CrossOrigin
public class StudentController extends UserBaseController {
    @Autowired
    StudentService studentService;
    @Autowired
    StuInfoMapper stuInfoMapper;
    @Autowired
    StuSelectedCouresMapper stuSelectedCouresMapper;
    @Autowired
    CurriculumMapper curriculumMapper;

    @PostMapping("/reg")
    public Result studentRegister(@RequestBody PostRegUser postRegStudent){
        return studentService.userReg(postRegStudent);
    }

    @GetMapping("/exam/main")
    public Result studentGetExam(){
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"ok",null);
    }


    @GetMapping("/getclass")
    public Result getMyClasses(HttpServletRequest request) throws Exception {
        String token = request.getHeader("token");
        String userNo = null;
        userNo = JwtUtil.parseJWT(token).getSubject();
        return studentService.getMyClassByUserNo(userNo);
    }

    @GetMapping("/getStudents")
    public Result getStudentByClassID(@RequestParam("classid") Integer classid){
        return studentService.getStudentsByClassID(classid);
    }

    @GetMapping("/moreInfo")
    public Result getMoreStudentInfo(@RequestParam("stid") Integer stid){
        return studentService.getMoreStudentInfo(stid);
    }

    @PostMapping ("/moreInfo/create")
    public Result insertMoreStudentInfo(@RequestBody StuInfo stuInfo){
        int insert = stuInfoMapper.insert(stuInfo);
        if (insert>0){
            return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"插入成功",stuInfo);
        }
        return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"插入失败",stuInfo);
    }

    @PostMapping ("/moreInfo/update")
    public Result updateMoreStudentInfo(@RequestBody StuInfo stuInfo){
        int i = stuInfoMapper.updateByPrimaryKey(stuInfo);
        if (i>0){
            return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"更新成功",stuInfo);
        }
        return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"更新失败",stuInfo);
    }

    @GetMapping ("/selected/create")
    public Result createStudentSelect(@RequestParam("stu")Integer stuid,@RequestParam("course")Integer[] cid){
        int i = 0;
        for (Integer integer : cid) {
            if (integer!=null){
                StuSelectedCoures stuSelectedCoures = new StuSelectedCoures();
                stuSelectedCoures.setStudentId(stuid);
                stuSelectedCoures.setCourseId(integer);
                stuSelectedCoures.setStatus(0);
                int insert = stuSelectedCouresMapper.insert(stuSelectedCoures);
                i+=insert;
            }

        }

        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"创建成功",i);
    }
    @GetMapping ("/selected/get")
    public Result getStudentSelect(@RequestParam("stu")Integer stuid){
        List<StuSelectedCoures> stuSelectedCoures =  stuSelectedCouresMapper.selectByStuid(stuid);
        List<Map<String,Object>> result = new ArrayList<>();
        stuSelectedCoures.forEach(it->{
            Map<String,Object> map = new HashMap<>();
            map.put("course",curriculumMapper.selectByPrimaryKey(it.getCourseId()));
            map.put("status",it.getStatus());
            map.put("id",it.getId());


            result.add(map);
        });
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"查询成功",result);
    }

    @GetMapping ("/selected/getempid")
    public Result getStudentSelectByEmpid(@RequestParam("empid")String empid){
        User user = userMapper.selectUserByEmpID(empid);
        List<StuSelectedCoures> stuSelectedCoures =  stuSelectedCouresMapper.selectByStuid(user.getId());
        List<Map<String,Object>> result = new ArrayList<>();
        stuSelectedCoures.forEach(it->{
            Map<String,Object> map = new HashMap<>();
            map.put("course",curriculumMapper.selectByPrimaryKey(it.getCourseId()));
            map.put("status",it.getStatus());
            map.put("id",it.getId());

            result.add(map);
        });
        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"查询成功",result);
    }

    @GetMapping ("/selected/rejected/remove")
    public Result removeRejectedStudentSelect(@RequestParam("stu")Integer stuid){
        Integer integer = stuSelectedCouresMapper.deleteRejectedByStuid(stuid);
        if (integer > 0 ){
            return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"删除成功",integer);

        }
        return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"删除失败",integer);

    }

    @GetMapping ("/selected/reject")
    public Result selectedReject(@RequestParam("id")Integer id){
        StuSelectedCoures stuSelectedCoures = stuSelectedCouresMapper.selectByPrimaryKey(id);
        stuSelectedCoures.setStatus(1);
        int i = stuSelectedCouresMapper.updateByPrimaryKey(stuSelectedCoures);
        if (i>0){
            return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"驳回成功",i);
        }
        return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"驳回失败",i);

    }

    @GetMapping ("/selected/pass")
    public Result selectedPass(@RequestParam("id")Integer id){
        StuSelectedCoures stuSelectedCoures = stuSelectedCouresMapper.selectByPrimaryKey(id);
        stuSelectedCoures.setStatus(2);
        int i = stuSelectedCouresMapper.updateByPrimaryKey(stuSelectedCoures);
        if (i>0){
            return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS,"通过成功",i);
        }
        return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"通过成功",i);
    }
}
