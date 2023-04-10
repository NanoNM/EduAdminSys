package top.sleepnano.edusys.eduadminsys.util;

import java.util.*;

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

    /**
     * 生成指定长度的随机文本
     * @param len 长度
     * @return 随机文本
     */
    public static String genRandomStr(int len){
        if (len <= 3) {
            throw new RuntimeException("密码的位数不能小于3位！");
        }
        //生成的随机数
        int i;
        //生成的密码的长度
        int count = 0;
        // 密码字典
        char[] str = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        // 大写字母密码字典
        List<Character> bigStrList = Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
        Set<Character> bigStrSet = new HashSet<>(bigStrList);
        // 小写字母的密码字典
        List<Character> upperStrList = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');
        Set<Character> upperStrSet = new HashSet<>(upperStrList);
        // 数字的密码字典
        List<Character> numStrList = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        Set<Character> numStrSet = new HashSet<>(numStrList);

        StringBuilder builder = new StringBuilder();
        Random r = new Random();
        boolean isContainBigChar = false;
        boolean isContainUpperChar = false;
        boolean isContainNumChar = false;
        while (count < len - 3) {
            //生成 0 ~ 密码字典-1之间的随机数
            i = r.nextInt(str.length);
            builder.append(str[i]);
            count++;
            if (!isContainBigChar && bigStrSet.contains(str[i])) {
                isContainBigChar = true;
            }
            if (!isContainUpperChar && upperStrSet.contains(str[i])) {
                isContainUpperChar = true;
            }
            if (!isContainNumChar && numStrSet.contains(str[i])) {
                isContainNumChar = true;
            }
        }
        // 如果不存在的，则加，确保一定会存在数字，大写字母，小写字母
        if (!isContainBigChar) {
            builder.append(bigStrList.get(r.nextInt(bigStrList.size())));
        }
        if (!isContainUpperChar) {
            builder.append(upperStrList.get(r.nextInt(upperStrList.size())));
        }
        if (!isContainNumChar) {
            builder.append(numStrList.get(r.nextInt(numStrList.size())));
        }
        while (builder.length() < len) {
            builder.append(str[r.nextInt(str.length)]);
        }
        return builder.toString();
    }
}
