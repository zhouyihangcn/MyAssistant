package com.zyh.wx.assistant.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.stereotype.Service;
import com.zyh.wx.assistant.entity.MessageStore;
import com.zyh.wx.assistant.repository.MessageStoreRepository;
import com.zyh.wx.assistant.util.Constant;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class AssistantServiceImpl implements AssistantService {
	
	private final MessageStoreRepository assistantRepository;

	@Override
	public MessageStore saveMessage(String user, Date createTime, String content) {
		log.info("\nsave message：[{}, {}, {}, {}]", user, createTime, content);
		ZoneId zoneId= ZoneId.of(Constant.ZONE_ID);
		LocalDate sqlCreateDate = createTime.toInstant().atZone(zoneId).toLocalDate();
		LocalTime sqlCreateTime = createTime.toInstant().atZone(zoneId).toLocalTime();
		MessageStore messageStore = new MessageStore(user, sqlCreateDate, sqlCreateTime,content);
		return assistantRepository.save(messageStore);
	}

	@Override
	public String findMessageByUser(String user) {
		Iterable<MessageStore> messageStores = assistantRepository.findByUser(user);
		String result="";
		LocalDate createDatePrev = null;
		for (MessageStore m :messageStores) {
			LocalDate createDateThis = m.getCreateDate();
			if (!createDateThis.equals(createDatePrev)) {
				result=result+"\n日期【"+m.getCreateDate()+"】的记录：\n";
				createDatePrev=createDateThis;
			}
			result=result+"时间【"+m.getCreateTime()+"】内容【"+m.getContent()+"】;\n";
		}
		result=result+"。";
		return result;
	}

	@Override
	public Iterable<MessageStore> findAllMessage() {
		return assistantRepository.findAll();
	}
	

}
