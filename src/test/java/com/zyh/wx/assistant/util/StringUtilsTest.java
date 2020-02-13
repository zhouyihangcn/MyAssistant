package com.zyh.wx.assistant.util;

import org.junit.Test;

public class StringUtilsTest {

	@Test
	public void test() {
        String name = "踩踩踩12411测114测";
        System.out.println("字符串长度:"+name.length());
        System.out.println("字符串byte长度:"+name.getBytes().length);
        System.out.println("截取字符串前10个字节的结果:"+CustomStringUtils.subStr(name,10));
    }
	
}
