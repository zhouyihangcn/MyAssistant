package com.zyh.wx.assistant.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class MessageStore {
	@Id
	@GeneratedValue
	private Long id;
	private String user;
	private Date createTime;
	private String content;

	public MessageStore() {
	}
	
	public MessageStore(String user, Date createTime, String content) {
		this.user=user;
		this.createTime=createTime;
		this.content=content;
	}

}
