package com.zyh.wx.assistant.repository;

import org.springframework.data.repository.CrudRepository;
import com.zyh.wx.assistant.entity.MessageStore;

public interface MessageStoreRepository extends CrudRepository<MessageStore, Long> {

	Iterable<MessageStore> findByUserOrderByIdDesc(String user);

	Iterable<MessageStore> findByUserAndContentContainingOrderByIdDesc(String user, String content);
}
