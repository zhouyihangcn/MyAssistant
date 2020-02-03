package com.zyh.wx.mp.handler;

import com.zyh.wx.mp.builder.TextBuilder;
import com.zyh.wx.mp.controller.WxPortalController;
import com.zyh.wx.mp.utils.JsonUtils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.zyh.wx.assistant.service.AssistantService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Slf4j
@AllArgsConstructor
@Component
public class MsgHandler extends AbstractHandler {
	
	private final AssistantService assistantService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {

        if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
            //TODO 可以选择将消息保存到本地
        }

        //当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
        try {
            if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
                && weixinService.getKefuService().kfOnlineList()
                .getKfOnlineList().size() > 0) {
                return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE()
                    .fromUser(wxMessage.getToUser())
                    .toUser(wxMessage.getFromUser()).build();
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        //TODO 组装回复消息
        //String content = "收到信息内容：" + JsonUtils.toJson(wxMessage);
        String response = "";
        if (StringUtils.startsWithAny(wxMessage.getContent(), "查找")) {
        	response = searchMessage(wxMessage);
        } else {
        	response = saveMessage(wxMessage);
        }
        
        return new TextBuilder().build(response, wxMessage, weixinService);

    }

	private String searchMessage(WxMpXmlMessage wxMessage) {
		log.info("search all message..."+wxMessage.getFromUser());
		String content = JsonUtils.toJson(assistantService.findMessageByUser(wxMessage.getFromUser()));
		return "你好"+wxMessage.getFriendUserName()+"，你要查找的信息：" + content;
	}

	private String saveMessage(WxMpXmlMessage wxMessage) {
		String content;
        if (WxConsts.XmlMsgType.TEXT.equals(wxMessage.getMsgType()))
        	content = wxMessage.getContent();
        else {
        	content = wxMessage.getRecognition();
        }
		log.info("save message..."+wxMessage.getFromUser()+","+content);
        Date createTime= new Date(wxMessage.getCreateTime());
        assistantService.saveMessage(wxMessage.getFromUser(), createTime, content);
        return "你好"+wxMessage.getFriendUserName()+"，收到信息内容（已存储）：" + content;
	}


}
