package com.zyh.wx.assistant.service;

import java.time.ZoneOffset;
import java.util.Date;

public interface UserService {

	ZoneOffset getZoneOffset(String user);

	String setZoneOffset(String user, String inputOffset, Date createTime);

}
