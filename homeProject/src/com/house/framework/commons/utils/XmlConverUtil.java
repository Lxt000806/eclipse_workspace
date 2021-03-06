package com.house.framework.commons.utils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import com.house.framework.bean.Result;

/**
 * common xml conver utility
 * @url http://blog.csdn.net/arjick/article/details/6251777
 */
public class XmlConverUtil {
	/**
	 * map to xml xml <node><key label="key1">value1</key><key
	 * label="key2">value2</key>......</node>
	 * 
	 * @param map
	 * @return
	 */
	
	@SuppressWarnings("rawtypes")
	public static String mapToXml(Map map) {
		Document document = DocumentHelper.createDocument();
		Element nodeElement = document.addElement("node");
		for (Object obj : map.keySet()) {
			Element keyElement = nodeElement.addElement("key");
			keyElement.addAttribute("label", String.valueOf(obj));
			keyElement.setText(String.valueOf(map.get(obj)));
		}
		return doc2String(document);
	}

	/**
	 * list to xml xml <nodes><node><key label="key1">value1</key><key
	 * label="key2">value2</key>......</node><node><key
	 * label="key1">value1</key><key
	 * label="key2">value2</key>......</node></nodes>
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String listToXml(List list) throws Exception {
		Document document = DocumentHelper.createDocument();
		Element nodesElement = document.addElement("nodes");
		int i = 0;
		for (Object o : list) {
			Element nodeElement = nodesElement.addElement("node");
			if (o instanceof Map) {
				for (Object obj : ((Map) o).keySet()) {
					Element keyElement = nodeElement.addElement("key");
					keyElement.addAttribute("label", String.valueOf(obj));
					keyElement.setText(String.valueOf(((Map) o).get(obj)));
				}
			} else {
				Element keyElement = nodeElement.addElement("key");
				keyElement.addAttribute("label", String.valueOf(i));
				keyElement.setText(String.valueOf(o));
			}
			i++;
		}
		return doc2String(document);
	}

	/**
	 * json?????????xml???<o class="object"><name type="string">json</name><flag type="boolean">true</flag></o>
	 * json?????????xml???<a><e class="object"><name type="string">json</name><flag type="boolean">true</flag></e></a>
	 * @param json
	 * @return
	 */
	public static String jsonToXml(String json) {
		if (StringUtils.isBlank(json)){
			return null;
		}
		try {
			XMLSerializer serializer = new XMLSerializer();
			JSON jsonObject = JSONSerializer.toJSON(json);
			String str = serializer.write(jsonObject);
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * json to xml {"node":{"key":{"@label":"key1","#text":"value1"}}} conver
	 * <o><node class="object"><key class="object"
	 * label="key1">value1</key></node></o>
	 * 
	 * @param json
	 * @return
	 */
	public static String jsonToXmlNoHead(String json) {
		if (StringUtils.isBlank(json)){
			return null;
		}
		try {
			//???????????????UTF-8???controller????????????;???????????????????????????
			//???GBK???controller???????????????;?????????????????????
			String chatSet = "GBK";
			XMLSerializer serializer = new XMLSerializer();
			if (json.indexOf("[") != 0){
				json = "["+json+"]";
			}
			JSONArray array = JSONArray.fromObject(json);
			JSON jsonObject = JSONSerializer.toJSON(array);
			 
			String str = serializer.write(jsonObject,chatSet);
			String head = "<?xml version=\"1.0\" encoding=\""+chatSet+"\"?>";
			if (StringUtils.isNotBlank(str)){
				str = str.replace(head, "");
			}
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * xml to map xml <node><key label="key1">value1</key><key
	 * label="key2">value2</key>......</node>
	 * 
	 * @param xml
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map xmlToMap(String xml) {
		try {
			Map map = new HashMap();
			Document document = DocumentHelper.parseText(xml);
			Element nodeElement = document.getRootElement();
			List node = nodeElement.elements();
			for (Iterator it = node.iterator(); it.hasNext();) {
				Element elm = (Element) it.next();
				map.put(elm.attributeValue("label"), elm.getText());
				elm = null;
			}
			node = null;
			nodeElement = null;
			document = null;
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * xml to list xml <nodes><node><key label="key1">value1</key><key
	 * label="key2">value2</key>......</node><node><key
	 * label="key1">value1</key><key
	 * label="key2">value2</key>......</node></nodes>
	 * 
	 * @param xml
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public static List xmlToList(String xml) {
		try {
			List<Map> list = new ArrayList<Map>();
			Document document = DocumentHelper.parseText(xml);
			Element nodesElement = document.getRootElement();
			List nodes = nodesElement.elements();
			for (Iterator its = nodes.iterator(); its.hasNext();) {
				Element nodeElement = (Element) its.next();
				Map map = xmlToMap(nodeElement.asXML());
				list.add(map);
				map = null;
			}
			nodes = null;
			nodesElement = null;
			document = null;
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * xml to json <node><key label="key1">value1</key></node> ?????????
	 * {"key":{"@label":"key1","#text":"value1"}}
	 * 
	 * @param xml
	 * @return
	 */
	public static String xmlToJson(String xml) {
		XMLSerializer xmlSerializer = new XMLSerializer();
		return xmlSerializer.read(xml).toString();
	}

	/**
	 * 
	 * @param document
	 * @return
	 */
	public static String doc2String(Document document) {
		String s = "";
		try {
			// ??????????????????????????????
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			// ??????UTF-8??????
			OutputFormat format = new OutputFormat("   ", true, "UTF-8");
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(document);
			s = out.toString("UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return s;
	}
	
	public static void main(String[] args) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("a", 1);
		map.put("b", "kkk");
		//System.out.println(mapToXml(map));
//		listToXml,jsonToXml,xmlToMap,xmlToList,xmlToJson
//		String str = {}
		Result result = new Result();
		result.setCode("0001");
		result.setInfo("??????");
		//String jsonString = JSONObject.toJSONString(result);
//		String jsonString = "{\"code\":\"0001\",\"info\":\"??????\",\"success\":false}";
		String jsonString = "[{\"remarks\":\"??????\"}]";
		System.out.println("???????????????"+System.getProperty("file.encoding"));
		System.out.println(jsonString);
		System.out.println(jsonToXml(jsonString));
		System.out.println(jsonToXmlNoHead(jsonString));
		//System.out.println(xmlToJson(jsonToXml(jsonString)));
		
	}
}
