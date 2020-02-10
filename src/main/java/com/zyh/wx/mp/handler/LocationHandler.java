package com.zyh.wx.mp.handler;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import com.zyh.wx.assistant.service.AssistantService;
import com.zyh.wx.mp.builder.TextBuilder;

import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@AllArgsConstructor
@Component
public class LocationHandler extends AbstractHandler {

	private final AssistantService assistantService;
	
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
        if (wxMessage.getMsgType().equals(XmlMsgType.LOCATION)) {
            //TODO 接收处理用户发送的地理位置消息
        	String content = proceedMessage(wxMessage);
            try {
                //String content = "感谢反馈，您的的地理位置已收到！";
                return new TextBuilder().build(content, wxMessage, null);
            } catch (Exception e) {
                this.logger.error("位置消息接收处理失败", e);
                return null;
            }
        }

        //上报地理位置事件
        this.logger.info("上报地理位置，纬度 : {}，经度 : {}，精度 : {}",
            wxMessage.getLatitude(), wxMessage.getLongitude(), String.valueOf(wxMessage.getPrecision()));

        //TODO  可以将用户地理位置信息保存到本地数据库，以便以后使用

        return null;
    }
    
	private String proceedMessage(WxMpXmlMessage wxMessage) {
//		String content = "Name: "+ wxMessage.getLocationName()+	//null
//						",Id: "+ wxMessage.getLocationId()+	//null
//						",Laber: "+ wxMessage.getLabel()+
//						",纬度 : "+ wxMessage.getLatitude()+ //null
//						",经度 :"+ wxMessage.getLongitude()+ //null
//						",精度 :"+ String.valueOf(wxMessage.getPrecision()); //null
		String content = "Laber: "+ wxMessage.getLabel() +
						",city:" + wxMessage.getCity()+
						",country:" + wxMessage.getCountry() ;
		return saveMessage(wxMessage, content);
		
	}

	private String saveMessage(WxMpXmlMessage wxMessage, String content) {
	  this.logger.info("save location message..."+wxMessage.getFromUser()+","+content);
      Date createTime= new Date(wxMessage.getCreateTime()*1000L);
      assistantService.saveMessage(wxMessage.getFromUser(), createTime, content);
      return "地理位置已存储：" + content;
	}

}
