package com.house.framework.commons.utils;
import org.apache.commons.io.IOUtils; 
import org.apache.commons.net.ftp.FTPClient; 
import java.io.File; 
import java.io.FileInputStream; 
import java.io.IOException; 
import java.io.FileOutputStream; 

public class FtpTest { 
    public static void main(String[] args) { 
        //testUpload(); 
        testDownload(); 
    } 

    /** 
     * FTP上传单个文件测试 
     */ 
    public static void testUpload() { 
        FTPClient ftpClient = new FTPClient(); 
        FileInputStream fis = null; 

        try { 
            ftpClient.connect("192.168.14.117"); 
            ftpClient.login("admin", "123"); 

            File srcFile = new File("C:\\new.gif"); 
            fis = new FileInputStream(srcFile); 
            //设置上传目录 
            ftpClient.changeWorkingDirectory("/admin/pic"); 
            ftpClient.setBufferSize(1024); 
            ftpClient.setControlEncoding("GBK"); 
            //设置文件类型（二进制） 
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
            ftpClient.storeFile("3.gif", fis); 
        } catch (IOException e) { 
            e.printStackTrace(); 
            throw new RuntimeException("FTP客户端出错！", e); 
        } finally { 
            IOUtils.closeQuietly(fis); 
            try { 
                ftpClient.disconnect(); 
            } catch (IOException e) { 
                e.printStackTrace(); 
                throw new RuntimeException("关闭FTP连接发生异常！", e); 
            } 
        } 
    } 

    /** 
     * FTP下载单个文件测试 
     */ 
    public static void testDownload() { 
        FTPClient ftpClient = new FTPClient(); 
        FileOutputStream fos = null; 
        //ftp://192.168.0.224/AttachFile/0000000335/2016年业绩绩效管理文件.doc
        //FtpUtils ftp = new FtpUtils("192.168.0.224", "21", "innftp", "innftpyj");
        try { 
            ftpClient.connect("192.168.0.224"); 
            ftpClient.login("innftp", "innftpyj"); 

            String remoteFileName = "/AttachFile/0000000335/2016年业绩绩效管理文件.doc"; 
            fos = new FileOutputStream("d:/2016年业绩绩效管理文件.doc"); 

            ftpClient.setBufferSize(1024); 
            //设置文件类型（二进制） 
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
            ftpClient.setControlEncoding("GBK");
            ftpClient.retrieveFile(new String(remoteFileName.getBytes("GBK"),"ISO-8859-1"), fos);
            //ftpClient.retrieveFile(remoteFileName, fos); 
        } catch (IOException e) { 
            e.printStackTrace(); 
            throw new RuntimeException("FTP客户端出错！", e); 
        } finally { 
            IOUtils.closeQuietly(fos); 
            try { 
                ftpClient.disconnect(); 
            } catch (IOException e) { 
                e.printStackTrace(); 
                throw new RuntimeException("关闭FTP连接发生异常！", e); 
            } 
        } 
    } 
} 
