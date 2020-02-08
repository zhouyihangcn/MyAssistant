package com.zyh.wx.assistant.service;

import java.util.Date;

import com.zyh.wx.assistant.entity.MessageStore;

/**
 * @author zhouyihang
 *
 */
public interface AssistantService {

	MessageStore saveMessage(String fromUser, Date createTime, String content);

	String findMessageByUser(String user);

	Iterable<MessageStore> findAllMessage();

	String findMessageByUserAndContentContaining(String user, String toSearch);

}
