package top.sleepnano.edusys.eduadminsys.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.sleepnano.edusys.eduadminsys.util.StatusCodeUtil;
import top.sleepnano.edusys.eduadminsys.util.VoBuilderUtil;
import top.sleepnano.edusys.eduadminsys.vo.Result;

@RestController
public class MainController {
    @GetMapping("/test")
    public Result test(){
        throw new NullPointerException("Test Exception");
//        return VoBuilderUtil.ok(StatusCodeUtil.success.SUCCESS, "",null);
    }

    @GetMapping("/needLogin")
    public Result needLogin(){
        return VoBuilderUtil.failed(StatusCodeUtil.failed.NEED_LOGIN, "你需要登录来完成以下操作",null);
    }
}
