package com.house.framework.commons.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;
import com.house.framework.bean.Result;

public class ImageUtil {

    private static final Log logger = LogFactory.getLog(ImageUtil.class);

    /**
     * 分析上件文件的格式,和大小是否适合要求
     *
     * @param uploadFile     上传的文件
     * @param uploadFileName 上传的文件名
     * @param pattern        文件格式  例如:jpg或者jpg|gif|...|bmp
     * @param fileSize       文件大小 单位为KB
     * @return Result     000000是成功.
     *         000001是参数错误
     *         000002是格式类型错误
     *         000003是文件大小超出要求
     *         000004是文件格式不符合要求
     */
    public static Result analyseFile(File uploadFile, String uploadFileName, String pattern, int maxSize) {
        //条件是否满足要求
        if (uploadFile == null || !uploadFile.isFile() || StringUtil.isEmpty(uploadFileName) || StringUtil.isEmpty(pattern)) {
            return new Result("000001", "参数错误");
        }
		//如果格式是否适合要求
		if(!pattern.matches("[|]?\\w+([|]\\w+)*[|]?")){
			return new Result("000002", "格式类型错误");
		}
        // 检查文件的大小
        if (uploadFile.length() > 1024L * maxSize) {
            //文件超出要求
            return new Result("000003", "图片大小超出要求");
        }

        //获取文件格式
        String extendName = uploadFileName.substring(uploadFileName.lastIndexOf('.') + 1, uploadFileName.length());
        extendName = extendName.toLowerCase();
        String patternStr[] = StringUtil.splitString(pattern, "|");
        for (String str : patternStr) {
            if (extendName.equals(str)) {
                return new Result("000000");
            }
        }

        return new Result("000003", "图片格式不符合要求");
    }


    /**
     * 上传文件
     *
     * @param uploadFile   上传的文件
     * @param destFilePath 上传文件存放的完全路径目录
     * @param destFileName 上传的文件名  如:main.txt
     * @return Result     000000是成功.
     *         000001是参数错误
     *         100002是创建文件目录错误
     *         100003是创建文件错误
     *         100004是在上传临时文件存到目标目录时出错误
     *         200001是错误未定义
     */
    public static Result uploadFileToServer(File uploadFile, String destFilePath, String destFileName) {
    	logger.debug("destFilePath:" + destFilePath);
    	//if (uploadFile == null || !uploadFile.isFile() || StringUtil.isEmpty(destFilePath) || StringUtil.isEmpty(destFileName)) {
        if (uploadFile == null || StringUtil.isEmpty(destFilePath) || StringUtil.isEmpty(destFileName)) {
            return new Result("000001", "参数错误");
        }
        
        try {
            //创建文件目录
            File dir = new File(destFilePath);
            
            //目录不存在，创建目录
            if (!dir.isDirectory()) {
                if (!dir.mkdirs())
                    return new Result("100002", "创建文件目录错误");
            }
            //创建目标文件对象
            File targetFile = new File(dir + "/" + destFileName);
            //存在同名文件，则删除文件
            if (targetFile.exists() && targetFile.isFile()) {
                if (!targetFile.delete())
                    return new Result("100003", "创建文件错误");
            }
            //将上传临时目录中的文件存到目标文件目录
            logger.debug("targetFile:"+targetFile.getAbsolutePath());
            logger.debug("uploadFile:"+uploadFile.getAbsolutePath());
            BufferedInputStream input = null;
            BufferedOutputStream out = null;
            byte[] bytes = new byte[1024];
            int size = 0;
            try{
            	input =new BufferedInputStream(new FileInputStream(uploadFile));
            	out = new BufferedOutputStream(new FileOutputStream(targetFile));
            	while ((size=input.read(bytes)) > 0) {
            		out.write(bytes, 0, size);
				}
//            	isUpload = uploadFile.renameTo(targetFile);
            } catch (Exception e) {
				e.printStackTrace();
				logger.debug("在上传临时图片存到目标目录时出错误,请确定磁盘是否已满了");
                return new Result("100004", "在上传图片出现错误");
			}finally{
				if (input !=null){
					input.close();
				}
				if (out != null){
					out.close();
				}
	        } 
        } catch (Exception e) {
//        	e.printStackTrace();
            return new Result("200001", "错误未定义");
        }
        return new Result("000000");
    }
    
    
    /**
     * 上传文件
     *
     * @param uploadFile   上传的文件
     * @param destFilePath 上传文件存放的完全路径目录
     * @param destFileName 上传的文件名  如:main.txt
     * @return Result     000000是成功.
     *         000001是参数错误
     *         100002是创建文件目录错误
     *         100003是创建文件错误
     *         100004是在上传临时文件存到目标目录时出错误
     *         200001是错误未定义
     */
    public static Result uploadFileToServer(InputStream inputStream, String destFilePath, String destFileName) {
    	logger.debug("destFilePath:" + destFilePath);
        if (inputStream == null || StringUtil.isEmpty(destFilePath) || StringUtil.isEmpty(destFileName)) {
            return new Result("000001", "参数错误");
        }
        
        if(!destFileName.matches("\\w+[.]\\w+")){
        	 return new Result("000001", "参数错误");
        }
        try {
            //创建文件目录
            File dir = new File(destFilePath);
            
            //目录不存在，创建目录
            if (!dir.isDirectory()) {
                if (!dir.mkdirs())
                    return new Result("100002", "创建文件目录错误");
            }
            //创建目标文件对象
            File targetFile = new File(dir + "/" + destFileName);
            //存在同名文件，则删除文件
            if (targetFile.exists() && targetFile.isFile()) {
                if (!targetFile.delete())
                    return new Result("100003", "创建文件错误");
            }
            //将上传临时目录中的文件存到目标文件目录
            logger.debug("targetFile:"+targetFile.getAbsolutePath());
            BufferedInputStream input = null;
            BufferedOutputStream out = null;
            byte[] bytes = new byte[1024];
            int size = 0;
            try{
            	input =new BufferedInputStream(inputStream);
            	out = new BufferedOutputStream(new FileOutputStream(targetFile));
            	while ((size=input.read(bytes)) > 0) {
            		out.write(bytes, 0, size);
				}
//            	isUpload = uploadFile.renameTo(targetFile);
            } catch (Exception e) {
				e.printStackTrace();
				logger.debug("在上传临时图片存到目标目录时出错误,请确定磁盘是否已满了");
                return new Result("100004", "在上传图片出现错误");
			}finally{
				if (input !=null){
					input.close();
				}
				if (out != null){
					out.close();
				}
	        } 
        } catch (Exception e) {
//        	e.printStackTrace();
            return new Result("200001", "错误未定义");
        }
        return new Result("000000");
    }


    /**
     * 生成随机文件名 格式：yyyyMMddhhmmss+用户UID+四位随机数.扩展名
     *
     * @param userId     用户ID
     * @param extendName 扩展名
     * @return String    随机文件名
     */
    public static String createFileName(String userId, String extendName) {

        String fileName = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());
        fileName += userId;
        int r = 0;
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            r = random.nextInt(9);
            fileName += r;
        }
        return fileName + "." + extendName;
    }


    /**
     * 缩放图像
     *
     * @param scrFilePath  原图片路径
     * @param destFilePath 新图片路径
     * @param pattern      图片格式
     * @param zoom         缩放比例
     * @return Result     000000是成功.
     *         000001是参数错误
     *         000002是参数的文件路径不是文件
     *         000003是保存图片时错误
     *         000004是图片处理错误,可能上传图片格式错误
     */
    public static Result zoomImage(String scrFilePath, String destFilePath, String pattern, double zoom) {

        if (StringUtil.isEmpty(scrFilePath) || StringUtil.isEmpty(destFilePath) || StringUtil.isEmpty(pattern) || zoom == 0) {
            return new Result("000001", "参数错误");
        }
        
        boolean isException = false;

        BufferedImage bufferedImage = null;
        Image image = null;
        int heigth = 0;    //图片高度
        int width = 0;    //图片宽度


        try {
            File scrFile = new File(scrFilePath);
            File destDir = new File(new File(destFilePath).getParent());
            
            logger.debug("scrFile exits :" + scrFile.exists());
            logger.debug("scrFileAbsolutePath " + scrFile.getAbsolutePath());
            
            if (!scrFile.isFile()) {
                return new Result("000002", "参数的文件路径不是图片");
            }
            //目的文件目录不存在,则建立目的文件的目录
            if (!destDir.isDirectory()) {
                boolean rDir = destDir.mkdirs();
                if (!rDir) {
                    return new Result("000002", "参数的文件路径不是文件");
                }
            }

            bufferedImage = ImageIO.read(scrFile);
            heigth = Integer.valueOf(Math.round(Double.valueOf(bufferedImage.getHeight()+"") * Double.valueOf(zoom+"")) + "");    //获取图片高度
            width = Integer.valueOf(Math.round(Double.valueOf(bufferedImage.getWidth()+"") * Double.valueOf(zoom+"")) + "");    //获取图片宽度

            //缩放图像
            Result result = zoomImage(scrFilePath, destFilePath, pattern, width, heigth);
            if(!result.isSuccess()){
            	return result;
            } 
        } catch (Exception ex) {
//			ex.printStackTrace();
        	
        	isException = true;
        	logger.debug("缩放图像 fail " + ex.toString());

        } finally {
        	try{
                if (bufferedImage != null) {
                    bufferedImage.flush();        //清除缓存
                }
                if (image != null) {
                    image.flush();    //清除缓存
                }
        	}catch (Exception e) {
				logger.debug("清除缓存 fail " + e.toString());
			}
        }
        if(isException){
            return new Result("000004", "图片处理错误,可能上传图片格式错误");
        }
        return new Result("000000");
    }
    
    
    /**
     * 缩放图像
     *
     * @param scrFilePath  原图片路径
     * @param destFilePath 新图片路径
     * @param pattern      图片格式
     * @param zoom         缩放比例
     * @return Result     000000是成功.
     *         000001是参数错误
     *         000002是参数的文件路径不是文件
     *         000003是保存图片时错误
     *         000004是图片处理错误,可能上传图片格式错误
     */
    @SuppressWarnings("static-access")
	public static Result zoomImage(String scrFilePath, String destFilePath, String pattern, int width, int heigth) {

        if (StringUtil.isEmpty(scrFilePath) || StringUtil.isEmpty(destFilePath) || StringUtil.isEmpty(pattern) || width == 0 || heigth==0) {
            return new Result("000001", "参数错误");
        }
        
        boolean isException = false;

        BufferedImage bufferedImage = null;
        Image image = null;

        try {
            File scrFile = new File(scrFilePath);
            File destDir = new File(new File(destFilePath).getParent());
            
            if (!scrFile.isFile()) {
                return new Result("000002", "参数的文件路径不是图片");
            }
            //目的文件目录不存在,则建立目的文件的目录
            if (!destDir.isDirectory()) {
                boolean rDir = destDir.mkdirs();
                if (!rDir) {
                    return new Result("000002", "参数的文件路径不是文件");
                }
            }

            Image srcImage = ImageIO.read(scrFile);
            
            //生成空白图片
            BufferedImage backGroundImage = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_RGB);  
            
            /*   
             * Image.SCALE_SMOOTH 的缩略算法  生成缩略图片的平滑度的   
             * 优先级比速度高 生成的图片质量比较好 但速度慢   
             */ 
            image = srcImage.getScaledInstance(width, heigth, bufferedImage.SCALE_SMOOTH);
              
            backGroundImage.getGraphics().drawImage(image, 0, 0, null);    

            //保存新图片
            ImageIO.write(backGroundImage, pattern, new File(destFilePath));
            
        } catch (Exception ex) {
//			ex.printStackTrace();
        	
        	isException = true;
        	logger.debug("缩放图像 fail " + ex.toString());

        } finally {
        	try{
                if (bufferedImage != null) {
                    bufferedImage.flush();        //清除缓存
                }
                if (image != null) {
                    image.flush();    //清除缓存
                }
        	}catch (Exception e) {
				logger.debug("清除缓存 fail " + e.toString());
			}
        }
        if(isException){
            return new Result("000004", "图片处理错误,可能上传图片格式错误");
        }
        return new Result("000000");
    }


    /**
     * 生成一个空白图片
     *
     * @param subPath 空白图片存放位置
     * @param width   空白图片宽度
     * @param height  空白图片高度
     * @param pattern 空白图片格式
     * @return Result  000000是成功
     *         000001是参数错误
     *         000002是生成空白图片失败
     */
    public static Result makeWhiteImage(String subPath, int width, int height, String pattern) {
        if (StringUtil.isEmpty(subPath) || StringUtil.isEmpty(pattern)) {
            return new Result("000001", "参数错误");
        }
        
        boolean isException = false;
        
        BufferedImage bi = null;
        
        try {
            // 生成空白图片
            // 创建BufferedImage对象
            bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            // 创建Graphics2D对象
            Graphics2D g2 = (Graphics2D) bi.getGraphics();
            // 填充背景为白色
            g2.setBackground(Color.WHITE);
            g2.clearRect(0, 0, width, height);
            // 保存空白图像
            ImageIO.write(bi, pattern, new File(subPath));
            
        } catch (Exception e) {
//			e.printStackTrace();
        	isException = true;
        	logger.debug("生成一个空白图片 fail " + e.toString());
            
        }finally{
            if (bi != null) {
                bi.flush();
            }
        }
        if(isException){
        	return new Result("000002", "生成空白图片失败");
        }
        return new Result("000000", "成功");
    }


    /**
     * 图像裁剪方法
     * 对图片裁剪，并把裁剪完蛋新图片保存 。
     *
     * @param srcpath 源图片路径名称
     * @param subpath 剪切图片存放路径名称
     * @param x       剪切点x坐标
     * @param y       剪切点y坐标
     * @param width   剪切点宽度
     * @param height  剪切点高度
     * @param pattern 图像裁剪格式
     * @return Result   000000是成功
     *         000001是参数错误
     *         000002是参数的文件路径不是文件
     *         000003是图片裁剪失败
     */
    public static Result cutImage(String srcFilePath, String destFilePath, int x, int y, int width, int height, String pattern) {

        if (StringUtil.isEmpty(srcFilePath) || StringUtil.isEmpty(destFilePath) || width == 0 || height == 0 || StringUtil.isEmpty(pattern)) {
            return new Result("000001", "参数错误");
        }
        
        boolean isException = false;

        int sWidth = width;  //图片实际裁剪宽度
        int sHeight = height;//图片实际裁剪高度
        BufferedImage bufferedImage = null;
        FileInputStream fileInputStream = null;
        ImageInputStream imageInputStream = null;
       

        try {
            ////////////////////////////////////初始化截取图片参数/////////////////////////////
            File scrFile = new File(srcFilePath);
            File destDir = new File(new File(destFilePath).getParent());
            int imageHeight = 0; // 图片实际高度
            int imageWidth = 0; // 图片实际宽度
            if (!scrFile.isFile()) {
                return new Result("000002", "参数的文件路径不是文件");
            }
            //目的文件目录不存在,则建立目的文件的目录
            if (!destDir.isDirectory()) {
                boolean rDir = destDir.mkdirs();
                if (!rDir) {
                    return new Result("000002", "参数的文件路径不是文件");
                }
            }
            bufferedImage = ImageIO.read(scrFile);
            imageHeight = bufferedImage.getHeight(); // 获取原始图片高度
            imageWidth = bufferedImage.getWidth(); // 获取原始图片宽度
            // X坐标小于0时
            if (x <= 0) {
                if (Math.abs(x) >= width)// 当截取位置大于等于剪切点宽度时
                {
                    // 真接生成空白图片
                    Result result = makeWhiteImage(destFilePath, width, height, pattern);
                    if (result.isSuccess()) {
                        return new Result("000000");
                    } else {
                        return new Result("000003", "图片裁剪失败");
                    }
                }
                // 当截取位置小于剪切点宽度时
                else {
                    if (width >= imageWidth - x) {
                        sWidth = imageWidth;
                    } else {
                        sWidth = sWidth + x;
                    }
                    x = 0;
                }
            }
            // X坐标大于0时
            else {
                if (x >= imageWidth)// 当截取位置大于等于图片宽度时
                {
                    // 真接生成空白图片
                    Result result = makeWhiteImage(destFilePath, width, height, pattern);
                    if (result.isSuccess()) {
                        return new Result("000000");
                    } else {
                        return new Result("000003", "图片裁剪失败");
                    }
                }
                //当截取位置小于图片宽度时
                else {
                    if (width >= imageWidth - x) {
                        sWidth = imageWidth - x;
                    }
                }
            }
            // y坐标小于0时
            if (y <= 0) {
                if (Math.abs(y) >= height)// 当截取位置大于等于剪切点高度时
                {
                    // 真接生成空白图片
                    Result result = makeWhiteImage(destFilePath, width, height, pattern);
                    if (result.isSuccess()) {
                        return new Result("000000");
                    } else {
                        return new Result("000003", "图片裁剪失败");
                    }
                }
                //当截取位置小于剪切点高度时
                else {
                    if (height > imageHeight - y) {
                        sHeight = imageHeight;
                    } else {
                        sHeight = sHeight + y;
                    }
                    y = 0;
                }
            }
            // y坐标大于0时
            else {
                if (y >= imageHeight)// 当截取位置大于等于图片宽度时
                {
                    // 真接生成空白图片
                    Result result = makeWhiteImage(destFilePath, width, height, pattern);
                    if (result.isSuccess()) {
                        return new Result("000000");
                    } else {
                        return new Result("000003", "图片裁剪失败");
                    }
                }
                //当截取位置小于图片宽度时
                else {
                    if (height > imageHeight - y) {
                        sHeight = imageHeight - y;
                    }
                }
            }
            ////////////////////////////////////初始化截取图片参数/////////////////////////////


            ///////////////////////////////////截取图片//////////////////////////////////////
            //读取图片文件
            fileInputStream = new FileInputStream(srcFilePath);

             /**//*
             * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader 
             * 声称能够解码指定格式。 参数：formatName - 包含非正式格式名称 .
             *（例如 "jpeg" 或 "tiff"）等 。 
             */
            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(pattern);
            ImageReader reader = it.next();
            //获取图片流 
            imageInputStream = ImageIO.createImageInputStream(fileInputStream);

             /**//*
             * <p>imageInputStream:读取源.true:只向前搜索 </p>.将它标记为 ‘只向前搜索’。
             * 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader
             *  避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。
             */
            reader.setInput(imageInputStream, true);

             /**//*
             * <p>描述如何对流进行解码的类<p>.用于指定如何在输入时从 Java Image I/O 
             * 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件
             * 将从其 ImageReader 实现的 getDefaultReadParam 方法中返回 
             * ImageReadParam 的实例。  
             */
            ImageReadParam param = reader.getDefaultReadParam();

             /**//*
             * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象
             * 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。 
             */
            Rectangle rect = new Rectangle(x, y, sWidth, sHeight);


            //提供一个 BufferedImage，将其用作解码像素数据的目标。 
            param.setSourceRegion(rect);

             /**//*
             * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将
             * 它作为一个完整的 BufferedImage 返回。
             */
            bufferedImage = reader.read(0, param);

            //调用图像后期处理,使生成适合要求的宽度与高度
            if (sWidth < width || sHeight < height) {

                BufferedImage imageBi = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
                imageBi = bufferedImage;

                // 创建空白图片
                BufferedImage whiteBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
                Graphics2D g2 = (Graphics2D) whiteBufferedImage.getGraphics();
                // 填充背景为白色
                g2.setBackground(Color.WHITE);
                g2.clearRect(0, 0, width, height);
                // 填充图片
                g2.drawImage(imageBi, Math.abs((width - sWidth) / 2), Math.abs((height - sHeight) / 2), null);
                //保存新图片
                ImageIO.write(whiteBufferedImage, pattern, new File(destFilePath));
            } else {
                //保存新图片
                ImageIO.write(bufferedImage, pattern, new File(destFilePath));
            }
            ///////////////////////////////////截取图片//////////////////////////////////////

        } catch (Exception ex) {
//        	ex.printStackTrace();	
        	isException = true;
            logger.debug("图像裁剪方法 fail " + ex.toString());
            //throw new IOException("cut file error!!!"+ex.getMessage());
        }finally{
            try {
                if (bufferedImage != null) {
                    bufferedImage.flush();
                }
                if (fileInputStream != null) {
                	fileInputStream.close();
                }
                if (imageInputStream != null) {
                    imageInputStream.close();
                }
			} catch (IOException e) {
				logger.debug("cutImage fail " + e.toString());
			}
        }
        if(isException){
        	return new Result("000003", "图片裁剪失败");
        }
        return new Result("000000");
    }
    
    /**
	 * 判定图片是否适合所指定的长度和宽度
	 * 
	 *@param imagePath
	 *@param width
	 *@param height
	 *@return boolean
	 */
	public static boolean isImageFormat(String imagePath, int width, int height){
		if(StringUtil.isEmpty(imagePath)
				|| width < 0
				|| height < 0){
			return false;
		}
		File image = new File(imagePath);
		if(!image.isFile()){
			return false;
		}
		try {
			BufferedImage bImage = ImageIO.read(image);
			if(bImage.getWidth() != width
					|| bImage.getHeight() != height){
				return false;
			}
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	//图片大小是否要相等（flagEqual：true要相等，false小于）
	public static Result checkImg(MultipartFile multipartFile,Integer maxSize,Integer Width,Integer Height,boolean flagEqual) throws IOException {
		// 获取文件格式
		String name = multipartFile.getOriginalFilename();
		String pattern = "jpg|jpeg|bmp|gif|png";
		String extendName = name.substring(name.lastIndexOf(".") + 1);
		extendName = extendName.toLowerCase();
		if(!pattern.contains(extendName)){
			return new Result("000001", "格式错误,请上传jpg、jpeg、bmp、gif、png类型图片");
		}
		BufferedImage sourceImg = null;
		try {
			sourceImg = javax.imageio.ImageIO.read(multipartFile.getInputStream()); 
		} catch (Exception e) {
			 //文件超出要求
            return new Result("000004", "图片格式错误，请上传jpg、jpeg、bmp、gif、png类型图片");
		}
		 if(Width>0&&Height>0){
		 	if (flagEqual){
		 		if(sourceImg.getWidth()!=Width || sourceImg.getHeight()!=Height){
	            	 //文件超出要求
	                return new Result("000003", "图片尺寸必须是"+Width+"x"+Height+"像素");
	            }
		 	}else{
		 		if(sourceImg.getWidth()>Width || sourceImg.getHeight()>Height){
	            	 //文件超出要求
	                return new Result("000003", "图片尺寸超出"+Width+"x"+Height+"像素");
	            }
		 	}
	     }
		   // 检查文件的大小
        if (multipartFile.getSize() > 1024L * maxSize) {
            //文件超出要求
            return new Result("000003", "图片不能大于"+maxSize+"K");
        }
    	return new Result("000000");
	}
		
	public static Result checkImg(MultipartFile multipartFile,Integer maxSize,Integer Width,Integer Height) throws IOException {
		return checkImg(multipartFile,maxSize,Width,Height,false);
	}

//    public static void main(String agrs[]) throws Exception{
////    	File uploadFile = new File("D:/ab/200810100134441231781.jpg");
////    	String pattern = "jpg";
////    	ImageUtil util = new ImageUtil();
////    	System.out.println(util.analyseFile(uploadFile, pattern, 4444444));
////    	System.out.println(util.upload(123L, "D:/bc", file));
////        String name = "//192.168.2.101/vssfile/uploadrootdir/imagelib/sns/head_photo/13799362586/u=3807662805,1709694852&fm=0&gp=28.jpg";
//////        ImageUtil.zoomImage(name, "jpg", 10);
////        ImageUtil.cutImage(name, "E:/12.jpg", 1, 1, 200, 200, "jpg");
//    }

		/**
		 * 判断图片长宽是否符合一定比例
		 * @param multipartFile
		 * @param zoomWidth
		 * @param zoomHeight
		 * @return
		 * @throws IOException
		 */
		public static Result checkImg(MultipartFile multipartFile,Integer zoomWidth,Integer zoomHeight) throws IOException {
			// 获取文件格式
			String name = multipartFile.getOriginalFilename();
			String pattern = "jpg|jpeg|bmp|gif|png";
			String extendName = name.substring(name.lastIndexOf(".") + 1);
			extendName = extendName.toLowerCase();
			if(!pattern.contains(extendName)){
				return new Result("000001", "格式错误,请上传jpg、jpeg、bmp、gif、png类型图片");
			}
			BufferedImage sourceImg = null;
			try {
				sourceImg = javax.imageio.ImageIO.read(multipartFile.getInputStream()); 
			} catch (Exception e) {
				 //文件超出要求
	            return new Result("000004", "图片格式错误，请上传jpg、jpeg、bmp、gif、png类型图片");
			}
			if(zoomWidth != null && zoomHeight != null) {
				if(sourceImg.getWidth()*zoomHeight != sourceImg.getHeight()*zoomWidth) {
					return new Result("000005", "图片长宽不符合要求");
				}
			}else {
				throw new RuntimeException("比例不能为空");
			}
	    	return new Result("000000");
		}
}

