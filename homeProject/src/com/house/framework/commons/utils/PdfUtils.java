package com.house.framework.commons.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

public class PdfUtils {
	
	/**
	 * 多个pdf文件合并成一个输出流
	 * @param srcPdfs
	 * @param out
	 */
	public static void merge(List<InputStream> srcPdfs, OutputStream out) {
		
		List<PdfReader> readers = new ArrayList<PdfReader>();
		Document document = null;
		try {
			for (InputStream srcPdf : srcPdfs) {
				readers.add(new PdfReader(srcPdf));
			}
			
			document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, out);
			
			document.open();
			PdfContentByte cb = writer.getDirectContent();
			
			int pageOfCurrentReaderPDF = 0;
			for (PdfReader pdfReader : readers) {
				while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
					document.newPage();
					pageOfCurrentReaderPDF++;
					PdfImportedPage page = writer.getImportedPage(pdfReader, pageOfCurrentReaderPDF);
					cb.addTemplate(page, 0, 0);
				}
				pageOfCurrentReaderPDF = 0;
			}
			
			out.flush();  
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			document.close();  
		}
	}
	
	public static boolean mergePdfFiles(String[] files, String newfile) {  
		boolean retValue = false;  
		Document document = null;  
		try {  
			document = new Document(new PdfReader(files[0]).getPageSize(1));  
			PdfCopy copy = new PdfCopy(document, new FileOutputStream(newfile));  
			document.open();  
			for (int i = 0; i < files.length; i++) {  
				PdfReader reader = new PdfReader(files[i]);  
				int n = reader.getNumberOfPages();  
				for (int j = 1; j <= n; j++) {  
					document.newPage();  
					PdfImportedPage page = copy.getImportedPage(reader, j);  
					copy.addPage(page);  
				}  
			}  
			retValue = true;  
		} catch (Exception e) {  
			e.printStackTrace();  
		} finally {  
			document.close();  
		}  
		return retValue;  
	}
	
	/**
	 * 获取pdf文件页数
	 * @param filename
	 * @return
	 */
	public static int getNumberOfPages(String filename) {
		File file = new File(filename);
	    PdfReader pdfReader;
		try {
			pdfReader = new PdfReader(new FileInputStream(file));
			return pdfReader.getNumberOfPages();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("无法获取PDF文件页数：" + filename);
		}
	}
 
	public static void main(String[] args) throws Exception {
//		List<InputStream> pdfs = new ArrayList<InputStream>();
//		InputStream in = new FileInputStream("D:/pdf/劳动合同.pdf");
//		InputStream in2 = new FileInputStream("D:/pdf/劳动合同2.pdf");
//		OutputStream out = new FileOutputStream("D:/pdf/" + "mergePDF8.pdf");
//		pdfs.add(in);
//		pdfs.add(in2);
//		merge(pdfs, out);
//		in.close();
//		in2.close();
//		out.close();
		System.out.println(getNumberOfPages("d:/1607912853098.pdf"));
	}
	
}
