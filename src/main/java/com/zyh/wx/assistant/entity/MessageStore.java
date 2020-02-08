package com.zyh.wx.assistant.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class MessageStore {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String user;
	private LocalDate createDate;
	private LocalTime createTime;
	private String content;

	public MessageStore() {
	}

	public MessageStore(String user, LocalDate createDate, LocalTime createTime, String content) {
		this.user=user;
		this.createDate = createDate;
		this.createTime=createTime;
		this.content=content;
	}
	
}
