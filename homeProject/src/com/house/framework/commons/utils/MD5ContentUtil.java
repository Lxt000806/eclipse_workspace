package com.house.framework.commons.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.net.util.Base64;

public class MD5ContentUtil {
	public static void main(String[] args) {
        System.out.println("结果：" + getStringContentMD5("D:/erp修改20201025厦门.pdf"));
    }

    /***
    * 计算字符串的Content-MD5
    * @param str 文件路径
    * @return
    */
    public static String getStringContentMD5(String str) {
        // 获取文件MD5的二进制数组（128位）
        byte[] bytes = getFileMD5Bytes1282(str);
        // 对文件MD5的二进制数组进行base64编码
        return new String(Base64.encodeBase64(bytes));
    }

    /***
     * 获取文件MD5-二进制数组（128位）
     * 
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] getFileMD5Bytes1282(String filePath) {
        FileInputStream fis = null;
        byte[] md5Bytes = null;
        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md5.update(buffer, 0, length);
            }
            md5Bytes = md5.digest();
            fis.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return md5Bytes;
    }
    
    /*public static byte[] fileToByte(File file) {
        byte[] byteArray = null;
        InputStream is;
        ByteArrayOutputStream baos;
        try {
            is = new FileInputStream(file);

            baos = new ByteArrayOutputStream(1024 * 1024);
            byte[] buf = new byte[1024 * 1024];
            int len;
            while ((len = is.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
            is.close();
            baos.flush();
            baos.close();
            byteArray = baos.toByteArray();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArray;
    }*/
}
