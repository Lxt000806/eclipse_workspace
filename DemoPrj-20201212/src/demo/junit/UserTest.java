package demo.junit;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {
	User user = new User();

	@BeforeEach
	void setUp() throws Exception {
		user.setUserEmail("1728490992@qq.com");
		user.setUserName("LiXinTing");
		user.setUserPhone("15960099851");
	}

	@Test
	void testGetUserEmail() {
		assertEquals(user.getUserEmail(),"1728490992@qq.com"); 
	}

	@Test
	void testSetUserEmail() {
		assertTrue(user.setUserEmail("1728490992@qq.com"));

	}

	@Test
	void testGetUserName() {
		assertEquals(user.getUserName(),"LiXinTing"); 
	}

	@Test
	void testSetUserName() {
		assertTrue(user.setUserName("LiXinTing"));
	}

	@Test
	void testGetUserPhone() {
		assertEquals(user.getUserPhone(),"15960099851"); 
	}

	@Test
	void testSetUserPhone() {
		assertTrue(user.setUserPhone("15960099851"));
	}

}
