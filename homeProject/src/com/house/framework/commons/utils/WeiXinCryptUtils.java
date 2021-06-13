package com.house.framework.commons.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.house.home.client.util.MD5EncryptionMgr;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
 
/**
 * 加密解密工具包
 */
public class WeiXinCryptUtils {
	
	public static String mtToken = "IJG8aC";
	public static String mtEncodingAesKey = "186BA775C2104A97B061E2F5518B4BD94Df3x84G347";
	public static String mtAppId = "YJAPI";
	
    public static void main(String[] args) throws AesException, ParserConfigurationException, SAXException, IOException {
    	String encodingAesKey = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFG";
		String token = "pamtest";
		String timestamp = "1409304348";
		String nonce = "xxxxxx";
		String appId = "wxb11529c136998cb6";
		String replyMsg = "zhangzhirongceshi";

		WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
		String mingwen = pc.encryptMsg(replyMsg, timestamp, nonce);
		System.out.println("加密后: " + mingwen);
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
		dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
		dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
		dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		dbf.setXIncludeAware(false);
		dbf.setExpandEntityReferences(false);

		DocumentBuilder db = dbf.newDocumentBuilder();
		StringReader sr = new StringReader(mingwen);
		InputSource is = new InputSource(sr);
		Document document = db.parse(is);

		Element root = document.getDocumentElement();
		NodeList nodelist1 = root.getElementsByTagName("Encrypt");
		NodeList nodelist2 = root.getElementsByTagName("MsgSignature");

		String encrypt = nodelist1.item(0).getTextContent();
		String msgSignature = nodelist2.item(0).getTextContent();

		String format = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
		String fromXML = String.format(format, encrypt);

		String result2 = pc.decryptMsg(msgSignature, timestamp, nonce, fromXML);
		System.out.println("解密后明文: " + result2);
    }
 
    public static String Decrypt(String token, String encodingAesKey, String appId, String fromXMLStr) throws AesException, ParserConfigurationException, SAXException, IOException{
		WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
	
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
		dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
		dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
		dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		dbf.setXIncludeAware(false);
		dbf.setExpandEntityReferences(false);

		DocumentBuilder db = dbf.newDocumentBuilder();
		StringReader sr = new StringReader(fromXMLStr);
		InputSource is = new InputSource(sr);
		Document document = db.parse(is);

		Element root = document.getDocumentElement();
		NodeList nodelist1 = root.getElementsByTagName("Encrypt");
		NodeList nodelist2 = root.getElementsByTagName("MsgSignature");
		NodeList nodelist3 = root.getElementsByTagName("TimeStamp");
		NodeList nodelist4 = root.getElementsByTagName("Nonce");

		String encrypt = nodelist1.item(0).getTextContent();
		String msgSignature = nodelist2.item(0).getTextContent();
		String timestamp = nodelist3.item(0).getTextContent();
		String nonce = nodelist4.item(0).getTextContent();

		String format = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
		String fromXML = String.format(format, encrypt);

		return pc.decryptMsg(msgSignature, timestamp, nonce, fromXML);
    }
    
    public static String Encrypt(String token, String encodingAesKey, String appId, String data) throws AesException{

		String timestamp = String.valueOf(new Date().getTime()/1000);
		String nonce = MD5EncryptionMgr.md5Encryption(timestamp + "zxjzyjzs").substring(0, 10);

		WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
		return pc.encryptMsg(data, timestamp, nonce);
    }
    
    
}
