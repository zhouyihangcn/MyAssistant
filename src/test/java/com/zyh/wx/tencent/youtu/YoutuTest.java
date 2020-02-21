package com.zyh.wx.tencent.youtu;


public class YoutuTest {

	// appid, secretid secretkey请到http://open.youtu.qq.com/获取
	// 请正确填写把下面的APP_ID、SECRET_ID和SECRET_KEY
	public static final String APP_ID = "10115898";
	public static final String SECRET_ID = "AKIDAGMMGM6K6h6PYXTuKYgeQIKG1a7SJvBK";
	public static final String SECRET_KEY = "CD8tT1TnwyqbFhx68yd6PWSGtLbfYFvR";
	public static final String USER_ID = "10328051";
	private static String imagePath = "https://n.sinaimg.cn/mil/transform/330/w198h132/20200214/058f-ipmxpvz7868495.png";
	
	public static void main(String[] args) {

		try {
			YoutuCustom faceYoutu = new YoutuCustom(APP_ID, SECRET_ID, SECRET_KEY,USER_ID);
			String respose = faceYoutu.postGeneralOcr(imagePath);
			System.out.println(respose);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
