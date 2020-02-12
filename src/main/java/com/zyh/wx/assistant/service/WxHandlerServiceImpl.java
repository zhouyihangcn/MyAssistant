package com.zyh.wx.assistant.service;

import java.util.Date;
import org.springframework.stereotype.Service;

import com.zyh.wx.assistant.entity.MessageLocation;
import com.zyh.wx.assistant.repository.MessageLocationRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;

@Slf4j
@AllArgsConstructor
@Service
public class WxHandlerServiceImpl implements WxHandlerService {

	private final AssistantService assistantService;
	private final MessageLocationRepository messageLocationRepository;

	@Override
	public String proceedLocationMessage(WxMpXmlMessage wxMessage) {
		return proceedMessage(wxMessage);
	}


	private String proceedMessage(WxMpXmlMessage wxMessage) {
//		String content = "Name: "+ wxMessage.getLocationName()+	//null
//						",Id: "+ wxMessage.getLocationId()+	//null
//						",Laber: "+ wxMessage.getLabel()+
//						",纬度 : "+ wxMessage.getLatitude()+ //null
//						",经度 :"+ wxMessage.getLongitude()+ //null
//						",精度 :"+ String.valueOf(wxMessage.getPrecision()); //null
		String content = "位置：Laber: "+ wxMessage.getLabel() +
						",city:" + wxMessage.getCity()+
						",country:" + wxMessage.getCountry() ;
		return saveMessage(wxMessage, content);
		
	}

	private String saveMessage(WxMpXmlMessage wxMessage, String content) {
		log.info("save location message..." + wxMessage.getFromUser() + "," + content);
		Date createTime = new Date(wxMessage.getCreateTime() * 1000L);
		MessageLocation location = new MessageLocation();
		location.setCreateDateTime(createTime);
		location.setLocationX(wxMessage.getLocationX());
		location.setLocationY(wxMessage.getLocationY());
		location.setDetails(content);
		messageLocationRepository.save(location);
		assistantService.saveMessage(wxMessage.getFromUser(), createTime, content);
		return "地理位置已存储：" + content;
	}

}
