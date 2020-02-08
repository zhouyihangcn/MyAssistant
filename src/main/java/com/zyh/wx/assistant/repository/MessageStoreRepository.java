package com.zyh.wx.assistant.repository;

import org.springframework.data.repository.CrudRepository;
import com.zyh.wx.assistant.entity.MessageStore;

public interface MessageStoreRepository extends CrudRepository<MessageStore, Long> {

	Iterable<MessageStore> findByUser(String user);

	Iterable<MessageStore> findByUserAndContentContaining(String user, String content);
}
