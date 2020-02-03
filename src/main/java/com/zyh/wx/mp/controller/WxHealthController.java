package com.zyh.wx.mp.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zyh.wx.assistant.entity.MessageStore;
import com.zyh.wx.assistant.service.AssistantService;
import com.zyh.wx.mp.utils.JsonUtils;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;


@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/health")
public class WxHealthController {
    private final WxMpService wxService;
	private final AssistantService assistantService;
	
    @GetMapping("/info")
    public String info() {

    	WxMpConfigStorage configs = wxService.getWxMpConfigStorage();
		log.info("\n new info request..." + JsonUtils.toJson(configs ));
        
        return "Good!";
    }

    @GetMapping("/message/all")
    public Iterable<MessageStore> searchAllMessage() {
		log.info("search all message ...");
        return assistantService.findAllMessage();
    }

    @GetMapping("/message/get/{user}")
    public Iterable<MessageStore> searchMessage(@PathVariable String user) {
		log.info("search request..." + user);
        return assistantService.findMessageByUser(user);
    }

    @GetMapping("/message/post/{user}")
    public MessageStore postMessage(@PathVariable String user) {
		log.info("post request..." + user);
		String content = "test posting";
        Date createTime= new Date();
        return assistantService.saveMessage(user, createTime, content);
    }
}
