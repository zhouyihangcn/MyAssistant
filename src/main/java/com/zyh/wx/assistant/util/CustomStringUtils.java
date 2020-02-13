package com.zyh.wx.assistant.util;

public class CustomStringUtils {

	public static String subStr(String str, int subSLength)    {
        String  subStr ="";
        try {
           if (str == null) return "";   
           else{  
                int tempSubLength = subSLength;//截取字节数 
                 subStr = str.substring(0, str.length()<subSLength ? str.length() : subSLength);//截取的子串   
                int subStrByetsL = subStr.getBytes("GBK").length;//截取子串的字节长度    
                // 说明截取的字符串中包含有汉字   
                while (subStrByetsL > tempSubLength){   
                    int subSLengthTemp = --subSLength; 
                    subStr = str.substring(0, subSLengthTemp>str.length() ? str.length() : subSLengthTemp);   
                    subStrByetsL = subStr.getBytes("GBK").length;   
                }   
            } 
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return subStr;  
     }
}
