package com.zyh.wx.assistant.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;
import org.springframework.stereotype.Service;
import com.zyh.wx.assistant.entity.MessageStore;
import com.zyh.wx.assistant.repository.MessageStoreRepository;
import com.zyh.wx.assistant.util.Constant;
import com.zyh.wx.assistant.util.CustomStringUtils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class AssistantServiceImpl implements AssistantService {
	
	private final MessageStoreRepository assistantRepository;
	private final UserService userService;

	@Override
	public MessageStore saveMessage(String user, Date createTime, String content) {
		log.info("\nsave message：[{}, {}, {}]", user, createTime, content);
//		ZoneId zoneId= ZoneId.of(Constant.ZONE_ID);
//		LocalDate localCreateDate = createTime.toInstant().atZone(zoneId).toLocalDate();
//		LocalTime localCreateTime = createTime.toInstant().atZone(zoneId).toLocalTime();
		ZoneOffset zoneOffset = getZoneOffset(user);
		LocalDate localCreateDate = createTime.toInstant().atOffset(zoneOffset).toLocalDate();
		LocalTime localCreateTime = createTime.toInstant().atOffset(zoneOffset).toLocalTime();
		String strCreateDate = localCreateDate.toString().substring(0,10);
		String strCreateTime = localCreateTime.toString().substring(0,8);
        if (content.length()>Constant.MAX_CONTNET_LENGTH_SAVE) {
        	content=content.substring(0, Constant.MAX_CONTNET_LENGTH_SAVE);
    		log.info("\nmessage trancated first：[{}] bytes", Constant.MAX_CONTNET_LENGTH_SAVE);
        }
		MessageStore messageStore = new MessageStore(user, strCreateDate, strCreateTime, createTime, content);
		return assistantRepository.save(messageStore);
	}

	private ZoneOffset getZoneOffset(String user) {
		return userService.getZoneOffset(user);
	}

	@Override
	public String findMessageByUser(String user) {
		Iterable<MessageStore> messageStores = assistantRepository.findByUserOrderByIdDesc(user);
		//log.info("database: {}", messageStores);
		return formatSearchResult(messageStores);
	}

	@Override
	public Iterable<MessageStore> findAllMessage() {
		return assistantRepository.findAll();
	}

	@Override
	public String findMessageByUserAndContentContaining(String user, String toSearch) {
		Iterable<MessageStore> messageStores = assistantRepository.findByUserAndContentContainingOrderByIdDesc(user, toSearch);
		return formatSearchResult(messageStores);
	}
	

	private String formatSearchResult(Iterable<MessageStore> messageStores) {
		String result="";
		String createDatePrev = null;
		for (MessageStore m :messageStores) {
			String createDateThis = m.getCreateDate();
			if (!createDateThis.equals(createDatePrev)) {
				result=result+"\n日期【"+m.getCreateDate()+"】的记录：\n";
				createDatePrev=createDateThis;
			}
			result=result+"时间【"+m.getCreateTime()+"】内容【"+m.getContent()+"】;\n";
			if (result.getBytes().length > Constant.MAX_CONTNET_LENGTH_WX_RESP) {
				//result = result.substring(0,Constant.MAX_CONTNET_LENGTH_WX_RESP) +
				return CustomStringUtils.subStr(result, Constant.MAX_CONTNET_LENGTH_WX_RESP) +
						"。。。内容太多。请关键字查找。";
			}
		} 
		return result+"。";
	}

}
