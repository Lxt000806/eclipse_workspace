/**
 * 
 */
package com.abc.hrmis.dao;

import java.util.List;

import com.abc.hrmis.domain.User;

/**
 * @author HY
 *
 */
public interface UserDao {
	List<User> loadUsers();
	User getUserByNo(String userNo);
	void AddUser(User user);
	void updateUser(User newUser);
}
