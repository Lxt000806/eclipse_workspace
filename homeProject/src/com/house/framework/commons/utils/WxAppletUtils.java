package com.house.framework.commons.utils;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.house.home.client.http.HttpMethod;

public class WxAppletUtils {
	private static final Log logger = LogFactory.getLog(HttpMethod.class);
	
	private static final String APPID = "wx101545fa33d2a1e5";

	private static final String SECRET = "118127f33fed3b6f4246d9b972763faa";
	
	private static final String QRCODEURL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit";  // 获取小程序码的接口头部

	public static void getAccessToken() {
		HttpMethod httpMethod = new HttpMethod();
		JSONObject accessTokenObject = JSONObject.parseObject(new String(httpMethod.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ APPID + "&secret=" + SECRET, "UTF-8")));
		RedisUtil.set(RedisUtil.getRedisConnection(), "access_token",accessTokenObject.get("access_token").toString(), -1L);
	}


	/*
	 * 参数说明：
	 * page:小程序访问路径
	 * scene:传递参数"c=02200&s=1&" 最大长度32
	 * 		czybh:传员工编号或工人编号   
	 * 		source:1,2,3 对应tXTDM RECOMMENDSOURCE
	 * 		city:工人APP或易居通APP登陆时选的城市节点的value 对应tXTCS AppCityValue
	 * width:二维码宽度
	 */
	public static String getWXQrcode(String page, String scene, Integer width, String accessTokenUrl) {
		HttpMethod httpMethod = new HttpMethod();
		JSONObject accessTokenObject = JSONObject.parseObject(new String(httpMethod.get(accessTokenUrl, "UTF-8")));
		String url = QRCODEURL+"?access_token="+accessTokenObject.get("accessToken");  // 拼接完整的URl
		Map<String, Object> requestParam = new HashMap<String,Object>();  // 小程序的参数可查看官方文档
		if(page!= null) requestParam.put("page", page);  // 扫码后需要跳转的页面
		if(scene!= null) requestParam.put("scene", scene);  // 携带的参数
		if(width!= null) requestParam.put("width", width);  // 二维码的宽度
		String param = JSON.toJSONString(requestParam);
		
		HttpURLConnection conn = null;
		BufferedReader bufferedReader = null;
		PrintWriter out = null;
		InputStream in = null;
		ByteArrayOutputStream bos = null;
		String base64String = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			conn = (HttpURLConnection) realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 超时设置,防止 网络异常的情况下,可能会导致程序僵死而不继续往下执行
			conn.setConnectTimeout(3000);
			conn.setReadTimeout(3000);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			
			in = conn.getInputStream();  // 得到图片的二进制内容
			int leng = in.available();  // 获取二进制流的长度，该方法不准确
			if(leng < 1000){  // 出现错误时，获取字符长度就一百不到，图片的话有几万的长度
				// 定义BufferedReader输入流来读取URL的响应
				bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				String line;
				String result = "";
				while ((line = bufferedReader.readLine()) != null) {
					result += line;
				}
				logger.debug("获取access_token出错："+result);
				return result;
			}
			
			// 修改图片的分辨率,分辨率太大打印纸不够大
			//BufferedInputStream in2 = new BufferedInputStream(conn.getInputStream());
			// 将文件二进制流修改为图片流
			Image srcImg = ImageIO.read(in);
			// 构建图片流
			BufferedImage buffImg = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);
			//绘制改变尺寸后的图
			buffImg.getGraphics().drawImage(srcImg.getScaledInstance(width, width, Image.SCALE_SMOOTH), 0, 0, null);
			// 将图片流修改为文件二进制流
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(buffImg, "png", os);
			in = new ByteArrayInputStream(os.toByteArray());
			// 刷新，将重置为类似于首次创建时的状态
			buffImg.flush();
			srcImg.flush();
			// 设null是告诉jvm此资源可以回收
			buffImg = null;  // 该io流不存在关闭函数
			srcImg = null;  // 该io流不存在关闭函数
			os.close();
			
			bos = new ByteArrayOutputStream();
			byte[] b1 = new byte[1024];
			int len = -1;
			while((len = in.read(b1)) != -1) {
				bos.write(b1, 0, len);
			}
			byte[] fileByte = bos.toByteArray();  // 转换为字节数组，方便转换成base64编码
			
			//BASE64Encoder encoder = new BASE64Encoder();   // import sun.misc.BASE64Encoder;  该类包为内部专用以后可能会删除，sun公司
			// 对字节数组转换成Base64字符串
			//base64String = encoder.encode(fileByte);
			
			base64String = Base64.encodeBase64String(fileByte);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//使用finally块来关闭输出流、输入流
		finally{
			try {
				if(bufferedReader != null){
					bufferedReader.close();
				}
				if(out!=null){
					out.close();
				}
				if(in!=null){
					in.close();
				}
				if(bos != null){
					bos.close();
				}
				if(conn !=null){
					conn.disconnect();
					conn = null;
				}
				//让系统回收资源，但不一定是回收刚才设成null的资源，可能是回收其他没用的资源。
				System.gc();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return base64String;  // 将base64格式的图片发送到前端
	}
	
	public static JSONObject wx_login(String Code){
		HttpMethod httpMethod = new HttpMethod();
		JSONObject loginObject = JSONObject.parseObject(new String(httpMethod.get("https://api.weixin.qq.com/sns/jscode2session?appid="+APPID+"&secret="+SECRET+"&js_code="+Code+"&grant_type=authorization_code", "UTF-8")));
		return loginObject;
	}
}
