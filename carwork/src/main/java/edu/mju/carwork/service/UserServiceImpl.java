/**
 * 
 */
package edu.mju.carwork.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mju.carwork.dao.UserDao;
import edu.mju.carwork.domain.User;

/**
 * @author joeyang ong
 *
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;

	/* (non-Javadoc)
	 * @see edu.mju.stuwork.service.UserService#getUserByNo(java.lang.String)
	 */
	@Override
	public User getUserByNo(String userNo) {
	   return userDao.getUserByNo(userNo);	
	}

}
