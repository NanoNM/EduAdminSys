package top.sleepnano.edusys.eduadminsys.util;

import org.springframework.stereotype.Component;
import top.sleepnano.edusys.eduadminsys.vo.Result;

/**
 * VO类包装类
 */
@Component
public class VoBuilderUtil {

    public static Result ok(StatusCodeUtil.success status, String message, Object data){
        return new Result("OK",status,message,data);
    }

    public static Result warring(StatusCodeUtil.warring status, String message,Object data){
        return new Result("WARRING",status,message,data);
    }

    public static Result failed(StatusCodeUtil.failed status,String message,Object data){
        return new Result("FAILED",status,message,data);
    }

    public static Result error(StatusCodeUtil.error status,String message,Object data){
        return new Result("ERROR",status,message,data);
    }
}
