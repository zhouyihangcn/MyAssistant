package com.zyh.wx.assistant.service;

import java.util.Date;

import com.zyh.wx.assistant.entity.MessageStore;

/**
 * @author zhouyihang
 *
 */
public interface AssistantService {

	MessageStore saveMessage(String fromUser, Date createTime, String content);

	Iterable<MessageStore> findMessageByUser(String user);

	Iterable<MessageStore> findAllMessage();

}
