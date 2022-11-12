package com.fastshop.net.utils;

public class CreateCode {
    private static String code = "";
    private static int[] numberArr = new int[]{0,1,2,3,4,5,6,7,8,9};
    
    public static String getOne() {
        int length = 19;
        for (int i = 1; i <= length; i++) {
            int random = (int) Math.floor(Math.random()*numberArr.length + 0);
            code += (i%5==0) ? "-" : numberArr[random];
        }
        return code;
    }
}
