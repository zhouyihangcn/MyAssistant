package com.zyh.wx.assistant.service.out;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.zyh.wx.assistant.util.WxGetPictureUtil;
import com.zyh.wx.tencent.youtu.Base64Util;
import com.zyh.wx.tencent.youtu.HttpUtilYoutu;
import com.zyh.wx.tencent.youtu.Sign;
import com.zyh.wx.tencent.youtu.config.YoutuProperties;
import com.zyh.wx.tencent.youtu.dto.YoutuResponse;
import com.zyh.wx.tencent.youtu.dto.YoutuResponseItem;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
@EnableConfigurationProperties(YoutuProperties.class)
@Service
public class YoutuService {
	private static String GENERAL_OCR_URL = "https://api.youtu.qq.com/youtu/ocrapi/generalocr";
	
	private final YoutuProperties youtuProperties;
	
	public String getWordFromPicture(String picUrl) {
		String appID = youtuProperties.getAppId();
		String secretID = youtuProperties.getSecretId();
		String secretKey = youtuProperties.getSecretKey();
		String userQQ = youtuProperties.getUserQQ();
		try {
			return postGeneralOcr(appID, secretID, secretKey, userQQ, picUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 检测图中人脸信息
	 * @param url 图片路径
	 * @return
	 * @throws Exception
	 */
	private String postGeneralOcr(String appID, String secretID, String secretKey, String userQQ, String url) throws Exception {
		//得到Authorization
		String sign = Sign.getSign(userQQ, appID, secretID, secretKey);
		byte[] imgData = WxGetPictureUtil.downloadFile(url);
		String image =  Base64Util.encode(imgData);
		String param = "{\"app_id\":\""+appID+"\",\"image\":\""+image+"\"}";
//		String result = HttpUtilYoutu.post(GENERAL_OCR_URL, param, "UTF-8",sign);
//		System.out.println(result);
		YoutuResponse resultObj = HttpUtilYoutu.postJson(GENERAL_OCR_URL, param, "UTF-8",sign);
		String wordResult = "";
		for (YoutuResponseItem a:resultObj.getItems()) {
			wordResult=wordResult+a.getItemstring();
		}
//		System.out.println(wordResult);
		return wordResult;
	}
}
