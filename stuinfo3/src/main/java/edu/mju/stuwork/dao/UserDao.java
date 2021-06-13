/**
 * 
 */
package edu.mju.stuwork.dao;

import edu.mju.stuwork.domain.User;

/**
 * @author joeyang ong
 *
 */
public interface UserDao {
	
	User getUserByNo(String userNo);

}
