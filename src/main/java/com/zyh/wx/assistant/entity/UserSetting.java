package com.zyh.wx.assistant.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

@Data
@Entity
public class UserSetting {
	@Id
	@Size(max = 30)
	private String user;
	private Integer zoneOffsetHours = 0;
	private Integer zoneOffsetMinutes = 0;
	@LastModifiedDate
	private Date lastUpdateDate;
	
	public UserSetting() {
	}
	
	public UserSetting(@Size(max = 30) String user) {
		this.user = user;
	}
	
}