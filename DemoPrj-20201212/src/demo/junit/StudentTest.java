package demo.junit;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudentTest {

	Student st1;
	
	@BeforeEach
	protected void setUp() throws Exception {
		st1 = new Student("уехЩ",22);
	}

	@Test
	void testInitScores() {
		assertNotNull(st1.initScores());
	}

	@Test
	void testGetAge() {
		assertEquals(st1.getAge(),22); 
	}

	@Test
	void testSetAge() {
		assertTrue(st1.setAge(25));
	}

}
