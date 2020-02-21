package com.zyh.wx.tencent.youtu;

import com.zyh.wx.assistant.util.WxGetPictureUtil;
import com.zyh.wx.tencent.youtu.dto.YoutuResponse;
import com.zyh.wx.tencent.youtu.dto.YoutuResponseItem;

public class YoutuCustom {
	private static String GENERAL_OCR_URL = "https://api.youtu.qq.com/youtu/ocrapi/generalocr";
	private final String AppID;
	private final String SecretID;
	private final String SecretKey;
	private final String userQQ;

	public YoutuCustom(String appID, String secretID, String secretKey, String userQQ) {
		AppID = appID;
		SecretID = secretID;
		SecretKey = secretKey;
		this.userQQ = userQQ;
	}

	/**
	 * 检测图中人脸信息
	 * @param url 图片路径
	 * @return
	 * @throws Exception
	 */
	public String postGeneralOcr(String url) throws Exception {
		//得到Authorization
		String sign = Sign.getSign(this.userQQ, this.AppID, this.SecretID, this.SecretKey);
		byte[] imgData = WxGetPictureUtil.downloadFile(url);
		String image =  Base64Util.encode(imgData);
		String param = "{\"app_id\":\""+this.AppID+"\",\"image\":\""+image+"\"}";
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