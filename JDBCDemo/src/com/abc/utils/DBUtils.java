/**
 * 
 */
package com.abc.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.print.attribute.standard.PresentationDirection;

/**
 * @author HY
 *
 */
public class DBUtils {
	
	//Á¬½Ó´®
	private static final String CONN_URL = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&useSSL=true&serverTimezone=UTC";
	private static final String USER = "HY";
	private static final String PWD = "123456";

	public static Connection getConn() {
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(CONN_URL,USER,PWD);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public static void releaseRes(Connection conn,PreparedStatement pstmt,ResultSet rest) {
		try {
			if(conn!=null) conn.close();
			if(pstmt!=null) pstmt.close();
			if(rest!=null) rest.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
