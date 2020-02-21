package com.zyh.wx.tencent.youtu.dto;

import java.util.List;

import lombok.Data;

@Data
public class YoutuResponse {

		  private int angle;
		  private String errormsg;
		  private int errorcode;
		  private List<YoutuResponseItem> items;
}
