/**
 * 
 */
package edu.mju.stuwork.utils;

import java.io.IOException;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * @author joeyang ong
 *
 */
public class MybatisUtils {
	
	//SQLSessionFactory的构建成本非常高，所以在整个程序中，我们只构建一个，是单例模式。
	private static SqlSessionFactory sessionFactory = null;
	
	static{
		
		Reader reader;
		
		try {
			reader = Resources.getResourceAsReader("edu/mju/stuwork/config/mybatis-config.xml");
			sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("mybatis sqlsessionfactory is created ok!");
		
	}
	
	
	/**
	 * 提供一个mybatis session
	 * @return
	 */
	public static SqlSession getSession(){
		return sessionFactory.openSession();
	}
	
	/**
	 * 释放一个mybatis session
	 * @param session
	 */
	public static void closeSession(SqlSession  session){
		session.close();
	}
		

}
