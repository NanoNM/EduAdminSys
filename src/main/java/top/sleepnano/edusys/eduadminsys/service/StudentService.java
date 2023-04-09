package top.sleepnano.edusys.eduadminsys.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import top.sleepnano.edusys.eduadminsys.dto.PostLoginStudent;
import top.sleepnano.edusys.eduadminsys.dto.PostRegStudent;
import top.sleepnano.edusys.eduadminsys.util.StatusCodeUtil;
import top.sleepnano.edusys.eduadminsys.vo.Result;

@Service
public interface StudentService extends BaseUserService{
    Result userReg(PostRegStudent postRegStudent);

    Result userLogin(PostLoginStudent postLoginStudent);


}
