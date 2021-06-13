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
	
	//SQLSessionFactory�Ĺ����ɱ��ǳ��ߣ����������������У�����ֻ����һ�����ǵ���ģʽ��
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
	 * �ṩһ��mybatis session
	 * @return
	 */
	public static SqlSession getSession(){
		return sessionFactory.openSession();
	}
	
	/**
	 * �ͷ�һ��mybatis session
	 * @param session
	 */
	public static void closeSession(SqlSession  session){
		session.close();
	}
		

}
