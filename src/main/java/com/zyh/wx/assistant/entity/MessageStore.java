package com.zyh.wx.assistant.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class MessageStore {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Size(max = 30)
	private String user;
	@Size(max = 10)
	private String createDate;
	@Size(max = 8)
	private String createTime;
	private Date createDateTime;
	@Size(max = 1000)
	private String content;

	public MessageStore() {
	}

	public MessageStore(String user, String createDate, String createTime, Date createDateTime, String content) {
		this.user=user;
		this.createDate = createDate;
		this.createTime=createTime;
		this.createDateTime=createDateTime;
		this.content=content;
	}
	
}
