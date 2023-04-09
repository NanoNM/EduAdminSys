package top.sleepnano.edusys.eduadminsys.util;

import java.util.UUID;

/**
 * 随机数类
 */
public class RandomUtil {
    /**
     * 生成UUID
     * @return UUID
     */
    public static UUID genUUID(){
        return UUID.randomUUID();
    }

    /**
     * 通过盐生成UUID
     * @param salt 盐
     * @return 加盐的UUID
     */
    public static UUID genUUID(String salt){
        String s = System.currentTimeMillis() + salt + salt.hashCode();
        return UUID.fromString(s);
    }
}
