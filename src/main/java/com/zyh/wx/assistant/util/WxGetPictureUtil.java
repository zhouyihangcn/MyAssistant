package com.zyh.wx.assistant.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
 
public class WxGetPictureUtil {
	/**
	 * 说明：根据指定URL将文件下载到指定目标位置
	 * 
	 * @param urlPath
	 *            下载路径
	 * @return 返回下载文件
	 */
	public static byte[] downloadFile(String urlPath) {
            BufferedInputStream in = null;
            try {
    			// 统一资源
    			URL url = new URL(urlPath);
    			// 连接类的父类，抽象类
    			URLConnection urlConnection = url.openConnection();
    			// http的连接类
    			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
    			//设置超时
    			httpURLConnection.setConnectTimeout(1000*5);
    			//设置请求方式，默认是GET
    			httpURLConnection.setRequestMethod("GET");
    			// 设置字符编码
    			httpURLConnection.setRequestProperty("Charset", "UTF-8");
    			// 打开到此 URL引用的资源的通信链接（如果尚未建立这样的连接）。
    			httpURLConnection.connect();
    			// 文件大小
    			int fileLength = httpURLConnection.getContentLength();

                ByteArrayOutputStream bos = new ByteArrayOutputStream((int) fileLength);
    			// 控制台打印文件大小
    			System.out.println("您要下载的文件大小为:" + fileLength / 1024 + "kB");
     
                in = new BufferedInputStream(httpURLConnection.getInputStream());
                short bufSize = 1024;
                byte[] buffer = new byte[bufSize];
                int len1;
                while (-1 != (len1 = in.read(buffer, 0, bufSize))) {
                    bos.write(buffer, 0, len1);
                }
 
                byte[] var7 = bos.toByteArray();
                bos.close();
                return var7;

	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println("文件下载失败！");
	}
            finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException var14) {
                    var14.printStackTrace();
                }
            }
            return null;
	}

}
