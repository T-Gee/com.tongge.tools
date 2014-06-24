package com.tongge.commons.lang3;

public class StringUtils {
    /**
     * @author ١���  tongguangen@ultrapower.com.cn
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
     * [������Oracle��nvl2����  �ַ���aΪ�յĻ�����b�����򷵻�c,У���ʹ��isBlank����]
     * @author:١��� tongguangen@ultrapower.com.cn
     * @param a �ַ���
     * @param b ����
     * @param c ���� 
     * @return �������������Ĭ�Ϸ���null
     */
    public static String nvl2(String a, String b, String c) {
        if (org.apache.commons.lang3.StringUtils.isBlank(a)) {
            return b;
        }
        return c;
    }

    /**
     * 
     * [������Oracle��decode�������� s��args�еĵ�һ��������ȵĻ�������args�еڶ��������������ж�args�ĳ��ȣ�
     *  ������ȴ��ڵ�������ô���ص����������������ж��Ƿ���ڵ��ĸ�����...]
     * @author:١��� tongguangen@ultrapower.com.cn
     * @param s �ַ���
     * @param args ���� ��������Ϊ����
     * @return �������������Ĭ�Ϸ���null
     */
    public static String decode(String s, String... args) {
        int length = args.length;
        if (length <= 1) {
            throw new RuntimeException("����û���㹻��ֵ");
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
