package com.zyh.wx.mp.handler;

import com.zyh.wx.mp.builder.TextBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.zyh.wx.assistant.service.AssistantService;
import com.zyh.wx.assistant.service.UserService;

import me.chanjar.weixin.common.api.WxConsts;
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
	
	private static final int CONTENT_LENGTH = 2500;
	private static final String KEYWORK_SEARCH = "查找";
	private static final String KEYWORK_SETZONE = "时区";
	private static final String KEYWORK_HELP = "help";
	private static final String KEYWORK_HELP_CHINESE = "帮助";
	private final AssistantService assistantService;
	private final UserService userService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {

        if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
            //TODO 可以选择将消息保存到本地
        }

        //当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
//        try {
//            if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
//                && weixinService.getKefuService().kfOnlineList()
//                .getKfOnlineList().size() > 0) {
//                return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE()
//                    .fromUser(wxMessage.getToUser())
//                    .toUser(wxMessage.getFromUser()).build();
//            }
//        } catch (WxErrorException e) {
//            e.printStackTrace();
//        }

        //TODO 组装回复消息
        //String content = "收到信息内容：" + JsonUtils.toJson(wxMessage);
        String response = proceedMessage(wxMessage);
        return new TextBuilder().build(response, wxMessage, weixinService);

    }

	private String proceedMessage(WxMpXmlMessage wxMessage) {
		String response;
		String content = determineContent(wxMessage);
		if (content == null) {
			return "信息格式未支持。";
		}
		String trimContent = content.trim();
		if (StringUtils.startsWithIgnoreCase(trimContent, KEYWORK_HELP) || 
				StringUtils.startsWithAny(trimContent, KEYWORK_HELP_CHINESE)) {
			return helpContent();
		}
		if (StringUtils.startsWithIgnoreCase(trimContent, KEYWORK_SETZONE)) {
			int keyWordLen = KEYWORK_SETZONE.length();
			int contentLen = trimContent.length();
			if (keyWordLen == contentLen) {
				return "输入为空。";
			}
			String setting = StringUtils.substring(trimContent, keyWordLen);
			return userSetting(wxMessage, setting);
		}
		
		if (StringUtils.startsWithAny(trimContent, KEYWORK_SEARCH)) {
			log.info("search message..." + trimContent + ";length:" + trimContent.length());
			int keyWordLen = KEYWORK_SEARCH.length();
			int contentLen = trimContent.length();
			if (keyWordLen == contentLen) {
				response = searchMessageAll(wxMessage);
			} else {
				String toSearch = StringUtils.substring(trimContent, keyWordLen);
				response = searchMessageLike(wxMessage, toSearch);
			}
		} else {
			response = saveMessage(wxMessage, content);
		}
		return response;
	}

	private String userSetting(WxMpXmlMessage wxMessage, String setting) {
		Date createTime= new Date(wxMessage.getCreateTime()*1000L);
		return userService.setZoneOffset(wxMessage.getFromUser(), setting, createTime);
	}

	private String determineContent(WxMpXmlMessage wxMessage) {
		String content;
        if (WxConsts.XmlMsgType.TEXT.equals(wxMessage.getMsgType()))
        	content = wxMessage.getContent();
        else if (WxConsts.XmlMsgType.VOICE.equals(wxMessage.getMsgType())){
        	content = wxMessage.getRecognition();
        } else {
        	return null;
        }
        return content;
	}

	private String searchMessageLike(WxMpXmlMessage wxMessage, String toSearch) {
		log.info("search message like..."+toSearch);
		String content = assistantService.findMessageByUserAndContentContaining(wxMessage.getFromUser(), toSearch);
		return "你要查找【"+toSearch+"】的信息：" + content;
	}

	private String searchMessageAll(WxMpXmlMessage wxMessage) {
		log.info("search all message..."+wxMessage.getFromUser());
		String content = assistantService.findMessageByUser(wxMessage.getFromUser());
		return "你要查找的信息：" + content;
	}

	private String saveMessage(WxMpXmlMessage wxMessage, String content) {
//        if (WxConsts.XmlMsgType.TEXT.equals(wxMessage.getMsgType()))
//        	content = wxMessage.getContent();
//        else if (WxConsts.XmlMsgType.VOICE.equals(wxMessage.getMsgType())){
//        	content = wxMessage.getRecognition();
//        } else {
//        	return "信息格式未支持。";
//        }
		log.info("save message..."+wxMessage.getFromUser()+","+content);
        Date createTime= new Date(wxMessage.getCreateTime()*1000L);

        if (content.length()>CONTENT_LENGTH) {
        	content=content.substring(0, CONTENT_LENGTH);
        }
        assistantService.saveMessage(wxMessage.getFromUser(), createTime, content);
        return "信息已存储：" + content;
	}

	private String helpContent() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n关键字【"+KEYWORK_SEARCH+"】:"+"如:"+KEYWORK_SEARCH+",或者"+KEYWORK_SEARCH+"你要找的内容");
		builder.append("\n关键字【"+KEYWORK_SETZONE+"】:"+"如:"+KEYWORK_SETZONE+"5:00 / -6:30");
		builder.append("\n其它字：系统保存，最长2500字节。");
		return builder.toString();
	}


}
