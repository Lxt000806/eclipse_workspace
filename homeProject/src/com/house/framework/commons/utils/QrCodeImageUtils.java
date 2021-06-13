package com.house.framework.commons.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;


import javax.imageio.ImageIO;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class QrCodeImageUtils {

	private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;
    
    public static byte[] buildQuickMark(String content) throws Exception {
        try {
            BitMatrix byteMatrix = new MultiFormatWriter().encode(new String(content.getBytes(), "iso-8859-1"),
                    BarcodeFormat.QR_CODE, 200, 200);
            BufferedImage image = toBufferedImage(byteMatrix);
            ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
            ImageIO.write(image, "jpg", baos);  //经测试转换的图片是格式这里就什么格式，否则会失真 
            byte[] bytes = baos.toByteArray(); 
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

}
