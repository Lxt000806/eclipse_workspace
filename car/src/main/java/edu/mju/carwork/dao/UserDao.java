/**
 * 
 */
package edu.mju.carwork.dao;

import edu.mju.carwork.domain.User;

/**
 * @author joeyang ong
 *
 */
public interface UserDao {
	
	User getUserByNo(String userNo);

}
