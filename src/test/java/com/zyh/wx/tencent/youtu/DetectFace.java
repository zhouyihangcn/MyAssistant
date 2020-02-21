//package com.zyh.wx.tencent.youtu;
//
//import com.zyh.wx.assistant.util.WxGetPictureUtil;
//import com.zyh.wx.tencent.youtu.dao.YoutuResponse;
//import com.zyh.wx.tencent.youtu.dao.YoutuResponseItem;
//
//public class DetectFace {
//	//人脸检测接口
//	public static String DETECTFACE_URL="http://api.youtu.qq.com/youtu/api/detectface";
//	public static String GENERAL_OCR_URL = "https://api.youtu.qq.com/youtu/ocrapi/generalocr";
//	public static void main(String[] args) throws Exception {
//		//String imagePath = "D:/WorkSpace/MyProject/PythonTest/src/aaa.jpg"; //D:\WorkSpace\DownloadProject\Tencent-YouTu-java-sdk
//		//String imagePath = "https://n.sinaimg.cn/tech/transform/330/w198h132/20200216/b1ce-iprtayy3683353.jpg";
//		String imagePath = "https://n.sinaimg.cn/mil/transform/330/w198h132/20200214/058f-ipmxpvz7868495.png";
//		getDetectFace(imagePath);
//		
//	}
//	/**
//	 * 检测图中人脸信息
//	 * @param imagePath 图片路径
//	 * @return
//	 * @throws Exception
//	 */
//	public static String getDetectFace(String imagePath) throws Exception{
//		//得到Authorization
//		String sign = Sign.getSign(YouTuAppContants.userQQ,
//				YouTuAppContants.AppID, YouTuAppContants.SecretID,
//				YouTuAppContants.SecretKey);
////		byte[] imgData = FileUtil.readFileByBytes(imagePath);
//		byte[] imgData = WxGetPictureUtil.downloadFile(imagePath);
//		String image =  Base64Util.encode(imgData);
//		String param = "{\"app_id\":\""+YouTuAppContants.AppID+"\",\"image\":\""+image+"\"}";
//		String result = HttpUtilYoutu.post(GENERAL_OCR_URL, param, "UTF-8",sign);
//		System.out.println(result);
//		YoutuResponse resultObj = HttpUtilYoutu.postJson(GENERAL_OCR_URL, param, "UTF-8",sign);
//		String wordResult = "";
//		for (YoutuResponseItem a:resultObj.getItems()) {
//			wordResult=wordResult+a.getItemstring();
//		}
//		System.out.println(wordResult);
//		return result;
//	}
//}