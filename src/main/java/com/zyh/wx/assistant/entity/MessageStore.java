package com.zyh.wx.assistant.entity;

import java.sql.Date;
import java.sql.Time;

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
	private Date createDate;
	private Time createTime;
	private String content;

	public MessageStore() {
	}

	public MessageStore(String user, Date createDate, Time createTime, String content) {
		this.user=user;
		this.createDate = createDate;
		this.createTime=createTime;
		this.content=content;
	}
	
}
