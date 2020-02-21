package com.zyh.wx.tencent.youtu;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
 
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.google.gson.Gson;
import com.zyh.wx.tencent.youtu.dto.YoutuResponse;
 
/**
 * http 工具类
 */
public class HttpUtilYoutu {
	 private static class TrustAnyTrustManager implements X509TrustManager {
		 
	        public void checkClientTrusted(X509Certificate[] chain, String authType)
	                throws CertificateException {
	        }
	 
	        public void checkServerTrusted(X509Certificate[] chain, String authType)
	                throws CertificateException {
	        }
	 
	        public X509Certificate[] getAcceptedIssuers() {
	            return new X509Certificate[] {};
	        }
	    }
	 
	    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
	        public boolean verify(String hostname, SSLSession session) {
	            return true;
	        }
	    }
	 
	    /**
	     * post方式请求服务器(https协议)
	     * 
	     * @param url
	     *            请求地址
	     * @param content
	     *            参数
	     * @param charset
	     *            编码
	     * @return
	     * @throws NoSuchAlgorithmException
	     * @throws KeyManagementException
	     * @throws IOException
	     */
	    public static String post(String url, String content,String charset,String sign)
	            throws NoSuchAlgorithmException, KeyManagementException,
	            IOException {
	        SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
	                new java.security.SecureRandom());
	        URL console = new URL(url);
	        Integer length = content.length();
	        HttpURLConnection conn = (HttpURLConnection) console.openConnection();
	        //文档要求填写的Header参数                
                conn.setRequestProperty("Host", "api.youtu.qq.com");
	        conn.setRequestProperty("Content-Length",length.toString());
	        conn.setRequestProperty("Content-Type", "text/json");
	        conn.setRequestProperty("Authorization", sign);
                //文档要求填写的Header参数
            conn.setDoOutput(true);
	        conn.connect();
	        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
	        out.write(content.getBytes(charset));
	        // 刷新、关闭
	        out.flush();
	        out.close();
	        BufferedReader in = null;
	        in = new BufferedReader(
	                new InputStreamReader(conn.getInputStream(), charset));
	        String result = "";
	        String getLine;
	        while ((getLine = in.readLine()) != null) {
	            result += getLine;
	        }
	        in.close();
	        System.out.println("result:" + result);
	        return result;
	    }
 

	    public static YoutuResponse postJson(String url, String content,String charset,String sign) 
	            throws NoSuchAlgorithmException, KeyManagementException,
	            IOException {
	    	String srtResp = post(url, content, charset, sign);
	    	return new Gson().fromJson(srtResp, YoutuResponse.class);
	    }
}