package com.zyh.wx.mp.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zyh.wx.assistant.entity.MessageStore;
import com.zyh.wx.assistant.service.AssistantService;
import com.zyh.wx.assistant.service.UserService;
import com.zyh.wx.assistant.service.out.YoutuService;
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
	private final UserService userService;
	private final YoutuService youtuService;
	
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
    public String searchMessage(@PathVariable String user) {
		log.info("search request..." + user);
        return assistantService.findMessageByUser(user);
    }

    @GetMapping("/message/get/{user}/{content}")
    public String searchMessageLike(@PathVariable String user, @PathVariable String content) {
		log.info("search request..." + user+";" +content);
        return assistantService.findMessageByUserAndContentContaining(user, content);
    }

    @GetMapping("/message/set/{user}/{content}")
    public MessageStore postMessage(@PathVariable String user, @PathVariable String content) {
		log.info("post request..." + user+";" +content);
//		String content = "test posting";
        Date createTime= new Date();
        return assistantService.saveMessage(user, createTime, content);
    }

    @GetMapping("/setting/get/{user}")
    public ZoneOffset getSetting(@PathVariable String user) {
		log.info("get request..." + user);
        return userService.getZoneOffset(user);
    }

    @GetMapping("/setting/set/{user}/{content}")
    public String setSetting(@PathVariable String user, @PathVariable String content) {
		log.info("set request..." + user+";" +content);
        Date createTime= new Date();		
        return userService.setZoneOffset(user, content, createTime);
    }

    @GetMapping("/youtu/get")
    public String testYoutu() {
		log.info("test Youtu ...");
        String picUrl="https://n.sinaimg.cn/mil/transform/330/w198h132/20200214/058f-ipmxpvz7868495.png";
		return youtuService.getWordFromPicture(picUrl);
    }
}
