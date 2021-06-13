package com.house.framework.commons.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.house.framework.commons.cache.DictCacheUtil;
import com.house.framework.commons.conf.DictConstant;

/**
 * FTP工具类
 * @author Victor.Chen
 *
 */
public class FtpUtils {
	private static final Logger logger = LoggerFactory.getLogger(FtpUtils.class);

	private FTPClient ftpClient = null; // FTP 客户端代理
	
	private String server = null;
	private String port = null;
	private String user = null;
	private String pwd = null;
	
	/**
	 * 构造函数
	 * 
	 * @param server
	 *            远程IP
	 * @param port
	 *            远程端口
	 * @param user
	 *            远程账户
	 * @param pwd
	 *            远程密码
	 */
	public FtpUtils(String server, String port, String user, String pwd){
		this.server = server;
		this.port = port;
		this.user = user;
		this.pwd = pwd;
		
		this.connectServer();
	}
	
	/**
	 * 通过传抽象字典code值, 在web应用
	 * @param abstractDictFtp
	 */
	public FtpUtils(String abstractDictFtp){
		this.server = DictCacheUtil.getItemValue(abstractDictFtp, DictConstant.DICT_FTP_REMOTE_IP);
		this.port = DictCacheUtil.getItemValue(abstractDictFtp, DictConstant.DICT_FTP_REMOTE_PORT);
		this.user = DictCacheUtil.getItemValue(abstractDictFtp, DictConstant.DICT_FTP_REMOTE_ACCOUNT);
		this.pwd = DictCacheUtil.getItemValue(abstractDictFtp, DictConstant.DICT_FTP_REMOTE_PASSWORD);
		this.connectServer();
	}
	
	/**
	 * 设置传输文件的类型[文本文件或者二进制文件]
	 * 
	 * @param fileType--BINARY_FILE_TYPE、ASCII_FILE_TYPE
	 * 
	 */
	public void setFileType(int fileType) {
		try {
			if(ftpClient != null){
				ftpClient.setFileType(fileType);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置编码值 
	 * @param encoding
	 */
	public void setControlEncoding(String encoding){
		if(ftpClient != null){
			this.ftpClient.setControlEncoding(encoding);
		}
	}
	
	public boolean removeDirectory(String path) throws IOException {
		return ftpClient.removeDirectory(path);
	}

	public boolean removeDirectory(String path, boolean isAll)
			throws IOException {

		if (!isAll)
			return removeDirectory(path);

		FTPFile[] ftpFileArr = ftpClient.listFiles(path);
		if (ftpFileArr == null || ftpFileArr.length == 0)
			return removeDirectory(path);

		for (FTPFile ftpFile : ftpFileArr) {
			String name = ftpFile.getName();
			if (ftpFile.isDirectory()) {
				removeDirectory(path + "/" + name, true);
			} else if (ftpFile.isFile()) {
				deleteFile(path + "/" + name);
			} else if (ftpFile.isSymbolicLink()) {

			} else if (ftpFile.isUnknown()) {

			}
		}
		return ftpClient.removeDirectory(path);
	}
	
	public void makeDirectory(String remoteRott){
		if(this.ftpClient != null){
			try {
				ftpClient.makeDirectory(remoteRott);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				e.printStackTrace();
			}
		}
	}

	public boolean existDirectory(String path) throws IOException {
		boolean flag = false;
		FTPFile[] ftpFileArr = ftpClient.listFiles(path);
		for (FTPFile ftpFile : ftpFileArr) {
			if (ftpFile.isDirectory()
					&& ftpFile.getName().equalsIgnoreCase(path)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public List<String> getFileList(String path) throws IOException {
		FTPFile[] ftpFiles = ftpClient.listFiles(path);

		List<String> retList = new ArrayList<String>();
		if (ftpFiles == null || ftpFiles.length == 0)
			return retList;
		for (FTPFile ftpFile : ftpFiles) {
			if (ftpFile.isFile()) {
				retList.add(ftpFile.getName());
			}
		}
		return retList;
	}

	public boolean deleteFile(String pathName) throws IOException {
		return ftpClient.deleteFile(pathName);
	}

	public boolean download(String remoteFileName, String remotePath, String localFileName) {
		boolean flag = false;
		File outfile = new File(localFileName);
		OutputStream oStream = null;
		try {
			oStream = new FileOutputStream(outfile);
			String encoding = "GBK";
			ftpClient.setBufferSize(1024);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setControlEncoding(encoding);
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");
			ftpClient.changeWorkingDirectory(remotePath);//转移到FTP服务器目录
			String fileName = new String(remoteFileName.getBytes(encoding),"ISO-8859-1");
			flag = ftpClient.retrieveFile(fileName, oStream);
			oStream.flush();
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error(ex.getMessage(), ex);
		} finally {
			if (oStream != null) {
				try {
					oStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	public void download(String remoteFileName, String remotePath, String localFileName, HttpServletResponse response) {
		InputStream is = null;
		ServletOutputStream os = null;
		try {
			String encoding = "GBK";
			ftpClient.setBufferSize(1024);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setControlEncoding(encoding);
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");
			ftpClient.changeWorkingDirectory(remotePath);//转移到FTP服务器目录
			String fileName = new String(remoteFileName.getBytes(encoding),"ISO-8859-1");
			is = ftpClient.retrieveFileStream(fileName);
			response.setContentType("application/x-download");// 设置为下载application/x-download
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
			os = response.getOutputStream();
			byte[] content = new byte[1024];
			int length;
			while((length = is.read(content, 0, content.length))!=-1){
				os.write(content,0,length);
			}
			os.flush();
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error(ex.getMessage(), ex);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


	/**
	 * 上传单个文件，并重命名
	 * 
	 * @param localFile
	 *            本地文件路径
	 * @param createRemotePath
	 *           需要创建远程 层级路径 上传可设置为空
	 * @param distFolder 
	 * 			  新的文件名
	 * @return true 上传成功，false 上传失败
	 */
	public boolean uploadFile(File localFile, String createRemotePath, final String destFolder) {
		System.out.println("-------------------------");
		boolean flag = true;
		try {
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
			//this.setControlEncoding("UTF-8");
			
			InputStream input = new FileInputStream(localFile);
			
			String objFolder = destFolder;
			objFolder = StringUtils.isNotBlank(createRemotePath) ? objFolder + File.separator + createRemotePath : objFolder;
			objFolder = objFolder.replaceAll("\\\\", "/");
			
			ftpClient.changeWorkingDirectory("/");
			//ftpClient.makeDirectory(objFolder);
			//ftpClient.changeWorkingDirectory(objFolder);
			
			String[] dirs = objFolder.indexOf("/") == 0 ? objFolder.substring(1).split("/") : objFolder.split("/");
			for(int i = 0; i < dirs.length; i++){
				if(!ftpClient.changeWorkingDirectory(dirs[i])){
					ftpClient.makeDirectory(dirs[i]);
					ftpClient.changeWorkingDirectory(dirs[i]);
				}
			}
			
			System.out.println("本地文件>> : " + localFile.getPath() + ";  FTP目标路径>>" + ftpClient.printWorkingDirectory());
			logger.debug("本地文件>> : " + localFile.getPath() + ";  FTP目标路径>>" + ftpClient.printWorkingDirectory());
			
			flag = ftpClient.storeFile(localFile.getName(), input);
			if (flag) {
				System.out.println("上传文件成功！");
			} else {
				System.out.println("上传文件失败！");
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			// logger.debug("本地文件上传失败！", e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return flag;
	}



	/**
	 * 上传多个文件
	 * 
	 * @param localFile,--本地文件夹路径
	 * @param createRemotePath 需要创建层级路径,
	 * @param distFolder--目标路径
	 * @return true 上传成功，false 上传失败
	 */
	public String uploadManyFile(File localFile, String createRemotePath, String destFolder) {
		System.out.println("-------------------------");
		boolean flag = true;
		StringBuffer strBuf = new StringBuffer();
		try {
			String objFolder = destFolder;
			objFolder = StringUtils.isNotBlank(createRemotePath) ? objFolder + File.separator + createRemotePath : objFolder;
			objFolder = objFolder.replaceAll("\\\\", "/");
			
			ftpClient.makeDirectory(objFolder);
			
			File fileList[] = localFile.listFiles();
			for (File upfile : fileList) {
				if (upfile.isDirectory()) {// 文件夹中还有文件夹
					createRemotePath = StringUtils.isNotBlank(createRemotePath) ? createRemotePath + File.separator + upfile.getName() : upfile.getName();
					uploadManyFile(upfile, createRemotePath, destFolder);
				} else {
					flag = uploadFile(upfile, createRemotePath, destFolder);
				}
				if (!flag) {
					strBuf.append(upfile.getName() + "\r\n");
				}
			}
			
			System.out.println(strBuf.toString());
		} catch (NullPointerException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return strBuf.toString();
	}

	/**
	 * 关闭连接
	 */
	public void closeConnect() {
		try {
			if (ftpClient != null) {
				ftpClient.logout();
				ftpClient.disconnect();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 连接到服务器
	 * 
	 * @return true 连接服务器成功，false 连接服务器失败
	 */
	private void  connectServer() {
		if (ftpClient == null) {
			try {
				ftpClient = new FTPClient();
				ftpClient.connect(server, Integer.parseInt(port));
				ftpClient.login(user, pwd);
				int reply = ftpClient.getReplyCode();
				ftpClient.setDataTimeout(120000);

				if (!FTPReply.isPositiveCompletion(reply)) {
					ftpClient.disconnect();
					System.err.println("FTP server refused connection.");
				}
			} catch (SocketException e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				System.err.println("登录ftp服务器 " + server + " 失败,连接超时！");
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				System.err.println("登录ftp服务器 " + server + " 失败，FTP服务器无法打开！");
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public boolean uploadftp(String localFile, String distFolder) {
		setFileType(FTP.BINARY_FILE_TYPE);// 设置传输二进制文件
		String flag = uploadManyFile(new File(localFile), "", distFolder);
		if ("true".equals(flag.trim()))
			return true;
		else
			return false;
	}
	

	public static void main(String[] args) throws Exception{
		//ftp://192.168.0.224/AttachFile/0000000335/2016年业绩绩效管理文件.doc
		FtpUtils ftp = new FtpUtils("192.168.0.224", "21", "innftp", "innftpyj");
		//ftp.uploadFile(new File("EOMS2TSCircuitService.rar"), "", "/root/123");
		ftp.download("2016年业绩绩效管理文件.doc", "/AttachFile/0000000335/", "d:/2016年业绩绩效管理文件.doc");
		//ftp.download("test.doc", "/AttachFile/0000000335/", "d:/test.doc");
		ftp.closeConnect();// 关闭连接
	}

}
