package com.house.framework.commons.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.house.home.entity.basic.Emppic;

/**
 * 数据库导出图片工具测试类
 * @author lenovo
 *
 */
public class ExportImageFromDBUtils {

	public static void main(String[] args) throws Exception{
		
		Connection connection = getDBConnect();
		
		if(connection != null){
			List<Emppic> list = getPicList(connection);
			for(int i = 0;i < list.size();i++){
				String photoName = new Date().getTime()+list.get(i).getNumber().trim()+".jpg";
				savePic("D:/homePhoto/empPic/", photoName, list.get(i).getPic());
				updateEmppic(connection, photoName, list.get(i).getNumber().trim());
			}
			connection.close();
		}
	}
	
	/**
	 * jdbc获取数据库连接
	 * @return
	 * @throws Exception
	 */
	public static Connection getDBConnect() throws Exception{
		// 读取数据库配置文件
		Properties properties = getProperties();
		
		// 创建数据库连接
		Class.forName(properties.getProperty("jdbc.connection.driver_class")); //classLoader,加载对应驱动
		Connection connection = (Connection) DriverManager.getConnection(properties.getProperty("jdbc.connection.url"), properties.getProperty("jdbc.connection.username")
								,properties.getProperty("jdbc.connection.password"));
		if(connection != null){
			System.out.println("数据库连接成功");
			return connection;
		}else{
			System.out.println("数据库连接失败");
			return null;
		}
	}
	
	/**
	 * 读取properties配置文件
	 * @return
	 * @throws Exception
	 */
	public static Properties getProperties() throws Exception{
		Properties properties = new Properties();
		BufferedReader bufferedReader = new BufferedReader(new FileReader("D:/EFixSys/myeclipse/homeProject/src/conf/db.properties"));
		properties.load(bufferedReader);
		System.out.println(properties.getProperty("jdbc.connection.driver_class"));
		System.out.println(properties.getProperty("jdbc.connection.url"));
		System.out.println(properties.getProperty("jdbc.connection.username"));
		System.out.println(properties.getProperty("jdbc.connection.password"));
		return properties;
	}
	
	/**
	 * 获取图片列表
	 * @param connection
	 * @return
	 * @throws Exception
	 */
	public static List<Emppic/*修改部分*/> getPicList(Connection connection) throws Exception{
		List<Emppic/*修改部分*/> list = new ArrayList<Emppic/*修改部分*/>();
		String sql = "select * from tEmpPic where pic is not null and (PhotoName is null or PhotoName = '') "; /*修改部分*/
		System.out.println("获取记录任务开始");
		Date beginDate = new Date();
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			/*修改部分 begin*/
		    Emppic emppic = new Emppic();
			emppic.setPic(resultSet.getBytes("Pic"));
			emppic.setNumber(resultSet.getString("Number"));
			/*修改部分 end*/
			
			list.add(emppic);
        }
		preparedStatement.close();
		Date endDate = new Date();
		System.out.println("获取记录任务结束.总共用时:"+((endDate.getTime()-beginDate.getTime())/1000)+"s");
		return list;
	}
	
	/**
	 * 保存图片到本地
	 * @param savePath
	 * @param fileName
	 * @param picByte
	 * @throws Exception
	 */
	public static void savePic(String savePath, String fileName, byte[] picByte) throws Exception{
		Date beginDate = new Date();
		File file = new File(savePath+fileName);
		OutputStream outputStream = new FileOutputStream(file);
		try {
			outputStream.write(picByte);
		}catch(IOException e) {
			e.printStackTrace();
		}finally{
			if(outputStream != null){
				outputStream.close();
			}
			Date endDate = new Date();
			System.out.println(fileName+"转换结束,共用时:"+(endDate.getTime()-beginDate.getTime())+"ms");
		}
	}
	
	/**
	 * 更新员工图片信息表记录
	 * @param connection
	 * @param photoName
	 * @param number
	 * @throws Exception
	 */
	public static void updateEmppic(Connection connection, String photoName, String number) throws Exception{
		Date beginDate = new Date();
		String sql="update tEmpPic set PhotoName=? where Number=? ";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, photoName);
		preparedStatement.setString(2, number);
		preparedStatement.execute();
		preparedStatement.close();
		Date endDate = new Date();
		System.out.println("更新"+number+"记录任务结束.总共用时:"+(endDate.getTime()-beginDate.getTime())+"ms");
	}
}
