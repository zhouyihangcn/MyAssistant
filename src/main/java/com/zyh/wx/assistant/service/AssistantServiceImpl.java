package com.zyh.wx.assistant.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.zyh.wx.assistant.entity.MessageStore;
import com.zyh.wx.assistant.repository.MessageStoreRepository;

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
		MessageStore messageStore = new MessageStore(user,createTime,content);
		return assistantRepository.save(messageStore);
	}

	@Override
	public Iterable<MessageStore> findMessageByUser(String user) {
		return assistantRepository.findByUser(user);
	}

	@Override
	public Iterable<MessageStore> findAllMessage() {
		return assistantRepository.findAll();
	}
	

}
