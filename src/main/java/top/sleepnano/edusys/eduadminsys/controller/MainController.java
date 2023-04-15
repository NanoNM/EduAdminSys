package top.sleepnano.edusys.eduadminsys.controller;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.sleepnano.edusys.eduadminsys.service.impl.AdminServiceImpl;
import top.sleepnano.edusys.eduadminsys.util.StatusCodeUtil;
import top.sleepnano.edusys.eduadminsys.util.VoBuilderUtil;
import top.sleepnano.edusys.eduadminsys.vo.Result;

@RestController
@CrossOrigin

public class MainController {
    @Autowired
    AdminServiceImpl adminService;
    @GetMapping("/test")
    public Result test(){
        throw new NullPointerException("Test Exception");
//        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS, "",null);
    }

    @GetMapping("/error/exthrow")
    public Result errorExthrow(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Exception exception = (Exception) request.getAttribute("filter.error");
        throw exception;
//        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS, "",null);
    }

    @ResponseBody
    @GetMapping("/grades")
    public Result getGrades(@Nullable @RequestParam("page")Integer page,
                            @Nullable @RequestParam("size")Integer size,
                            @Nullable @RequestParam("status")String status){
        if (page==null){
            if (status==null){
                return VoBuilderUtil.failed(StatusCodeUtil.failed.FAILED,"当不指定页数的时候,status参数是必须的",null);
            }
            return adminService.getGradesByStatus(status);
        }
        return adminService.getGrades(page,size);
    }

    @GetMapping("/needLogin")
    public Result needLogin(){
        return VoBuilderUtil.failed(StatusCodeUtil.failed.NEED_LOGIN, "你需要登录来完成以下操作",null);
    }
}
