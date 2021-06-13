package demo.junit.cnt;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InverseWordsTest {
	InverseWords ive = new InverseWords();

	@BeforeEach
	void setUp() throws Exception {
	}


	@Test
	void testGetEntry() {
		assertNotNull(ive.getEntry());
	}

	@Test
	void testPrintInverseArray() {
		String str = "Hello World mary";
		ive.printInverseArray(str);
	}

}
