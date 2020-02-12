package com.zyh.wx.assistant.service;

import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;

public interface WxHandlerService {

	String proceedLocationMessage(WxMpXmlMessage wxMessage);

}
