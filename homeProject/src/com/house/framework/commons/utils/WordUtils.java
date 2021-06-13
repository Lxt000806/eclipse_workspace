package com.house.framework.commons.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class WordUtils {

	private static final int wdFormatPDF = 17; // 另存为PDF格式
	
	/**
	 * 获取Word.Application
	 * @return
	 */
	private static ActiveXComponent getApp() {
		ComThread.InitMTA();
		ActiveXComponent app = new ActiveXComponent("Word.Application");
		Variant sssString  = new Variant(false);
		app.setProperty("Visible", new Variant(false));
		return app;
	}
	
	/**
	 * 关闭Word.Application
	 * @param app
	 */
	private static void closeApp(ActiveXComponent app) {
		if (app != null) {
			app.invoke("Quit", new Variant[] {});
		}
		ComThread.Release();
	}
	
	/**
	 * 打开word文档
	 * @param app
	 * @param filename
	 * @return
	 */
	private static Dispatch getDoc(ActiveXComponent app, String filename) {
		Dispatch docs = app.getProperty("Documents").toDispatch();
		Dispatch doc = Dispatch.call(docs, "Open", filename).toDispatch();
		return doc;
	}
	
	/**
	 * 关闭word文档
	 * @param doc
	 */
	private static void closeDoc(Dispatch doc) {
		Dispatch.call(doc, "Close", new Variant(false));
	}
	
	/**
	 * 向word文档填充数据
	 * @param doc
	 * @param data
	 */
	private static void fillData(Dispatch doc, Map<String, Object> data) {
		Dispatch bookmarks = Dispatch.call(doc, "Bookmarks").toDispatch();
		int bookmarkCount = Dispatch.get(bookmarks, "Count").getInt();  
		
		Dispatch bookmark = null;
		String key = null;
		Dispatch bookmarkRange = null;
		for (int i = bookmarkCount; i >= 1; i--) {
			bookmark = Dispatch.call(bookmarks, "Item", new Variant(i)).toDispatch();
			bookmarkRange = Dispatch.get(bookmark, "Range").toDispatch();
			key = String.valueOf(Dispatch.get(bookmark, "Name").getString()).replaceAll("null", "");
			Dispatch.put(bookmarkRange, "Text", new Variant(data.get(key)));
		}	
	}
	
	/**
	 * word转成pdf文件
	 * @param doc
	 * @param pdfFileName
	 */
	private static void convertToPdf(Dispatch doc, String pdfFileName) {
		Dispatch.call(doc, "SaveAs", pdfFileName, wdFormatPDF);
	}
	
	/**
	 * 向word文档填充数据并转成pdf文件
	 * @param wordFileName
	 * @param pdfFileName
	 * @param data
	 */
	public static void fillDataAndConvertToPdf(String wordFileName, String pdfFileName, 
		Map<String, Object> data) {
		
		ActiveXComponent app = null;
		Dispatch doc = null;
		
		try {
			System.out.println();
			app = getApp();
			doc = getDoc(app, wordFileName);
			fillData(doc, data);
			convertToPdf(doc, pdfFileName);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDoc(doc);
			closeApp(app);
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("address", "天空之城");
		data.put("area", "75");
		File file = new File("D:/developer/apache-tomcat-7.0.11/temp/conPdf/");
		if(!file.exists())
			file.mkdir();
		//fillDataAndConvertToPdf("D:/homePhoto/custContractTemp/1606288117182.doc", "D:/developer/apache-tomcat-7.0.11/temp/conPdf/1606288762596.pdf", data);
		fillDataAndConvertToPdf("D:/homePhoto/custContractTemp/1606212624905.doc", "D:/developer/apache-tomcat-7.0.11/temp/conPdf/1606288762596.pdf", data);
	}
	
}
