package com.zyh.wx.assistant.util;

public class Constant {
	public static final String ZONE_ID = "Asia/Shanghai";
	public static final int DEFAULT_OFFSET_HOURS = 8;
	public static final int DEFAULT_OFFSET_MINUTES = 0;
	public static final int MAX_CONTNET_LENGTH_SAVE = 1000;
	public static final int MAX_CONTNET_LENGTH_WX_RESP = 1950;
	
	public static final String KEYWORK_SEARCH = "查找";
	public static final String KEYWORK_SETZONE = "时区";
	public static final String KEYWORK_HELP = "help";
	public static final String KEYWORK_HELP_CHINESE = "帮助";
	
	public static String helpContent() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n***欢迎使用，这是类似记事本***");
		builder.append("\n输入文字：系统保存，最长"+Constant.MAX_CONTNET_LENGTH_SAVE+"字节。");
		builder.append("\n输入语音：系统转换成文字保存，最长"+Constant.MAX_CONTNET_LENGTH_SAVE+"字节。");
		builder.append("\n发送位置：系统保存地址");
		builder.append("\n查找已存内容：关键字【"+KEYWORK_SEARCH+"】:"+"如:【"+KEYWORK_SEARCH+"】,或者【"+KEYWORK_SEARCH+"位置】");
		builder.append("\n设定时区：关键字【"+KEYWORK_SETZONE+"】:"+"如:【"+KEYWORK_SETZONE+"-5:00】");
		builder.append("\n******");
		return builder.toString();
	}
}
