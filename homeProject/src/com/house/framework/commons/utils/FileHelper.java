package com.house.framework.commons.utils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.house.framework.commons.conf.SystemConfig;

public class FileHelper {
	public static void main(String args[]) throws IOException {
//		String url1 = "D:/homePhoto/prjProg"; // 源文件夹
//		String url2 = "D:/homePhoto/prjProgNew"; // 目标文件夹
		String prjProg = SystemConfig.getProperty("prjProg", "", "photo");
		String url1 = prjProg.substring(0,prjProg.length()-1);
		String url2 = url1.replace("prjProg", "prjProgNew");
		File fileTag = new File(url2);
		if (!fileTag.exists()){
			fileTag.mkdir();
		}
		File[] file = (new File(url1)).listFiles(); // 获取源文件夹当前下的文件或目录
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()){ // 复制文件
//				String type = file[i].getName().substring(
//						file[i].getName().lastIndexOf(".") + 1);
				String fileName = file[i].getName();
				if (fileName.length()>5){
					copyFile(file[i], new File(url2+"/"+fileName.substring(0,5)+"/" + file[i].getName()));
				}
			}
			if (file[i].isDirectory()){ // 复制目录
				String sourceDir = url1 + File.separator + file[i].getName();
				String targetDir = url2 + File.separator + file[i].getName();
				copyDirectiory(sourceDir, targetDir);
			}
		}
		System.out.println("复制文件成功！");
	}

	// 复制文件
	public static void copyFile(File sourceFile, File targetFile)
			throws IOException {
		String fileName = targetFile.getName();
		String filePath = targetFile.getAbsolutePath();
		if (fileName!=null && fileName.length()>5){
			String str = filePath.replace(fileName, "");
			File file = new File(str);
			if (!file.exists()){
				file.mkdir();
			}
			BufferedInputStream inBuff = null;
			BufferedOutputStream outBuff = null;
			try {
				inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
				outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
				byte[] b = new byte[1024 * 5];
				int len;
				while ((len = inBuff.read(b)) != -1) {
					outBuff.write(b, 0, len);
				}
				outBuff.flush();
			} finally {
				if (inBuff != null)
					inBuff.close();
				if (outBuff != null)
					outBuff.close();
			}
		}
	}

	// 复制文件夹
	public static void copyDirectiory(String sourceDir, String targetDir)
			throws IOException {
		(new File(targetDir)).mkdirs();
		File[] file = (new File(sourceDir)).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				File sourceFile = file[i];
				File targetFile = new File(
						new File(targetDir).getAbsolutePath() + File.separator
								+ file[i].getName());
				copyFile(sourceFile, targetFile);// 递归调用
			}
			if (file[i].isDirectory()) {
				String dir1 = sourceDir + "/" + file[i].getName();
				String dir2 = targetDir + "/" + file[i].getName();
				copyDirectiory(dir1, dir2);
			}
		}
	}
	/**
	 * 功能：Java读取txt文件的内容 步骤：1：先获得文件句柄 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
	 * 3：读取到输入流后，需要读取生成字节流 4：一行一行的输出。readline()。 备注：需要考虑的是异常情况
	 * 
	 * @param filePath
	 */
	public static String readTxtFile(String filePath) {
		StringBuffer str = new StringBuffer("");
		try {
			String encoding = "GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					str.append(lineTxt);
				}
				read.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str.toString();
	}

	/**
	 * 传入文件名以及字符串, 将字符串信息保存到文件中
	 * 
	 * @param strFilename
	 * @param strBuffer
	 */
	public static boolean strToFile(String filePath, String fileName, String str) {
		boolean flag = true;
		try {
			File f = new File(filePath);
			if (!f.exists()) {
				f.mkdirs();
			}
			File file = new File(filePath+fileName);
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(str);
			fileWriter.close();
		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 获取某个目录下所有直接下级文件,返回文件流
	 * @param path
	 * @return
	 */
    public static List<InputStream> getFilesAsInputStreams(String path) {
        List<InputStream> inputStreams = new ArrayList<InputStream>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        try {
	        for (int i = 0; i < tempList.length; i++) {
	            if (tempList[i].isFile()) {
	            	InputStream inputStream = new FileInputStream(tempList[i]);
					inputStreams.add(new FileInputStream(tempList[i]));
					inputStream.close();
	            }
	        }
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return inputStreams;
    }
    
    /**
	 * 获取某个目录下所有直接下级文件
	 * @param path
	 * @return
	 */
    public static List<File> getFiles(String path) {
        List<File> files = new ArrayList<File>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        try {
	        for (int i = 0; i < tempList.length; i++) {
	            if (tempList[i].isFile()) {
	            	files.add(tempList[i]);
	            }
	        }
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return files;
    }
    
    /**
	 * 删除指定的目录以及目录下的所有子文件
	 * @param pathname 目录路径
	 * @return true or false 成功返回true，失败返回false
	 */
    public static boolean delFile(String fileName) {
    	File file = new File(fileName);
        if (!file.exists()) {
            return false;
        }

        if (file.isFile()) {
            return file.delete();
        } else {
            File[] files = file.listFiles();
            for (File f : files) {
            	f.delete();
            }
            return file.delete();
        }
    }
    
}
