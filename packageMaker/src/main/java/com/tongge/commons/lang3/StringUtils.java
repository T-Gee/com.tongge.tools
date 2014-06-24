package com.tongge.commons.lang3;

public class StringUtils {
    /**
     * @author 佟广恩  tongguangen@ultrapower.com.cn
     * @param str0
     * @param str1
     * @return
     */
    public static String nvl(Object str0, String str1) {
        if(str0 == null || str0.toString().length()==0){
            return str1;
        }
        return str0.toString();
    }
    /**
     * 
     * [类似于Oracle中nvl2方法  字符串a为空的话返回b，否则返回c,校验空使用isBlank方法]
     * @author:佟广恩 tongguangen@ultrapower.com.cn
     * @param a 字符串
     * @param b 参数
     * @param c 参数 
     * @return 如果参数不够是默认返回null
     */
    public static String nvl2(String a, String b, String c) {
        if (org.apache.commons.lang3.StringUtils.isBlank(a)) {
            return b;
        }
        return c;
    }

    /**
     * 
     * [类似于Oracle中decode方法，当 s与args中的第一个参数相等的话，返回args中第二个参数，否则判断args的长度，
     *  如果长度大于等于三那么返回第三个参数，否则判断是否等于第四个参数...]
     * @author:佟广恩 tongguangen@ultrapower.com.cn
     * @param s 字符串
     * @param args 参数 参数个数为奇数
     * @return 如果参数不够是默认返回null
     */
    public static String decode(String s, String... args) {
        int length = args.length;
        if (length <= 1) {
            throw new RuntimeException("方法没有足够的值");
        }
        while ((length = args.length) >= 1) {
            if (length == 1) {
                return args[0];
            } else if (length >= 3 && s.equals(args[0])) {
                return args[1];
            } else {
                args = org.apache.commons.lang3.ArrayUtils.subarray(args, 2, args.length);
            }
        }
        return null;
    }
}
