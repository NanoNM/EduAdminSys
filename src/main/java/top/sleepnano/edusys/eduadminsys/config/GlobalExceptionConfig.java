package top.sleepnano.edusys.eduadminsys.config;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import top.sleepnano.edusys.eduadminsys.util.StatusCodeUtil;
import top.sleepnano.edusys.eduadminsys.util.VoBuilderUtil;
import top.sleepnano.edusys.eduadminsys.vo.Result;


/**
 * 统一异常处理
 */
@Slf4j
@RestController
@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionConfig {
    /**
     * 处理其他异常
     * @param req 引发异常的请求
     * @param e 引发的异常
     * @return vo包装类
     */
    @ExceptionHandler(value=Exception.class)
    public Result exceptionHandler(HttpServletRequest req, Exception e, HttpServletResponse response){
        log.error("未知Exception！原因是:");
        e.printStackTrace();
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return VoBuilderUtil.error(StatusCodeUtil.error.ERROR,"请联系服务器管理员并提交下面信息::::异常! 来自:" +req.getRequestURI()+ " 原因: "+e.getLocalizedMessage(),null);
    }

    @ExceptionHandler(value= ExpiredJwtException.class)
    public Result expiredJwtExceptionHandler(HttpServletRequest req, Exception e, HttpServletResponse response){
        log.error("无效登录！");
        e.printStackTrace();
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return VoBuilderUtil.error(StatusCodeUtil.error.INVALID_LOGIN,"无效的登录! 来自:" +req.getRequestURI()+ " 原因: "+e.getLocalizedMessage(),null);
    }

}
