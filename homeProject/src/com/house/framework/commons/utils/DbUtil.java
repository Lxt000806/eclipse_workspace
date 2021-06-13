package com.house.framework.commons.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;

public class DbUtil {
	
	public static Connection getConnection(){
		String driverName = "";
		String dbURL = "";
		String userName = "";
		String userPwd = "";
		Connection dbConn=null;
		try {
			Properties properties = PropertiesLoader.loadProperties("conf/db.properties");
			if (!properties.isEmpty()){
				driverName = properties.getProperty("jdbc.connection.driver_class");
				dbURL = properties.getProperty("jdbc.connection.url");
				userName = properties.getProperty("jdbc.connection.username");
//				userPwd = properties.getProperty("jdbc.connection.password");
				userPwd = DesUtils.decode(properties.getProperty("jdbc.connection.password"));
			}
			Class.forName(driverName).newInstance();
			dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
//			System.out.println("连接成功!");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return dbConn;
	}
	
	public static void close(ResultSet rs,CallableStatement call,Connection conn){
		if (rs!=null){
			try{
				rs.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if (call!=null){
			try{
				call.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if (conn!=null){
			try{
				conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static void closePs(ResultSet rs,PreparedStatement ps,Connection conn){
		if (rs!=null){
			try{
				rs.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if (ps!=null){
			try{
				ps.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if (conn!=null){
			try{
				conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static List<Map<String,Object>> getListBySql(String sql, List<Object> list){
		if (StringUtils.isBlank(sql)){
			return null;
		}
		Connection dbConn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map<String,Object>> retList = new ArrayList<Map<String,Object>>();
		try {   
			dbConn = DbUtil.getConnection();
            ps = dbConn.prepareStatement(sql.toLowerCase());
            if (list!=null && list.size()>0){
            	int ii = 1;
            	for (Object obj:list){
            		if (obj instanceof Date){
            			ps.setTimestamp(ii, new java.sql.Timestamp(((Date) obj).getTime()));
            		}else{
            			ps.setObject(ii, obj);
            		}
            		ii++;
            	}
            }
            rs = ps.executeQuery();
            ResultSetMetaData rsme = rs.getMetaData();
            int columnCount = rsme.getColumnCount();
            List<String> colName = new ArrayList<String>();
            List<String> colType = new ArrayList<String>();
            for (int i = 1; i <= columnCount ; i++) {
                colName.add(rsme.getColumnName(i));
                colType.add(rsme.getColumnTypeName(i));
            }
            while (rs.next()) {
            	int j = 1;
            	Map<String,Object> map = new HashMap<String,Object>();
            	for (String str : colName){
            		if (str!=null){
            			map.put(str, rs.getObject(j));
            		}
            		j++;
            	}
            	retList.add(map);
            	map = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
        	closePs(rs, ps, dbConn);
        }
		return retList;
	}
	
	public static Object getObjectByRs(ResultSet rs, String colType, int j){
		Object object = null;
		try{
			if ("datetime".equals(colType)){
				object = rs.getDate(j);
			}else if("int".equals(colType)){
				object = rs.getInt(j);
			}else if("money".equals(colType)){
				object = rs.getDouble(j);
			}else{
				object = rs.getString(j);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return object;
	}

}
