package top.sleepnano.edusys.eduadminsys.util;

import org.springframework.stereotype.Component;

/**
 * 错误代码工具
 * 应该能看的懂 注释不多写了
 */
@Component
public interface StatusCodeUtil {
    enum success implements StatusCodeUtil{
        SUCCESS,
        LOGIN_SUCCESS,
        LOGIN_OUT_SUCCESS,
        REGISTER_SUCCESS
    }

    enum warring implements StatusCodeUtil{
        WARRING,
    }

    enum failed implements StatusCodeUtil{
        FAILED,
        REGISTER_FAILED,
        LOGIN_FAILED,
        NEED_LOGIN
    }

    enum error implements StatusCodeUtil{
        ERROR,
        REGISTER_ERROR,

        INVALID_LOGIN
    }
}
