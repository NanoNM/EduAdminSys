package top.sleepnano.edusys.eduadminsys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.sleepnano.edusys.eduadminsys.dto.PostLoginStudent;
import top.sleepnano.edusys.eduadminsys.service.StudentService;
import top.sleepnano.edusys.eduadminsys.util.StatusCodeUtil;
import top.sleepnano.edusys.eduadminsys.util.VoBuilderUtil;
import top.sleepnano.edusys.eduadminsys.vo.Result;

@RestController
public class UserBaseController implements BaseController{
}
