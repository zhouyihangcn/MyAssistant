package com.zyh.wx.assistant.service;

/*
# # ToUserName  开发者微信号
# # FromUserName    发送方帐号（一个OpenID）
# # CreateTime  消息创建时间 （整型）
# # MsgType 消息类型
# # Content 文本消息内容
# # MsgId   消息id，64位整型      

# # PicUrl  图片链接
# # MediaId 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
# # MediaId 语音消息媒体id，可以调用多媒体文件下载接口拉取数据。
# # Format  语音格式，如amr，speex等
# # MediaId 视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
# # ThumbMediaId    视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
# # MediaId 视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
# # ThumbMediaId    视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
# # Location_X  地理位置维度
# # Location_Y  地理位置经度
# # Scale   地图缩放大小
# # Label   地理位置信息
# # Title   消息标题
# # Description 消息描述
# # Url 消息链接
*/
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;

public interface WxHandlerService {

	String proceedLocationMessage(WxMpXmlMessage wxMessage);

}
