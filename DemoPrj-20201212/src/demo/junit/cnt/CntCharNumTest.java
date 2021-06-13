package demo.junit.cnt;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CntCharNumTest {

	CntCharNum ccn = new CntCharNum();
	Map<String,Integer> map = ccn.getArray("Hello Hello aaa aa a");
	@BeforeEach
	void setUp() throws Exception {		
	}

	@Test
	void testGetEntry() {
		
		assertNotNull(ccn.getEntry());
	}

	@Test
	void testGetArray() {
		String str = "Hello Hello aaa aa a";
		assertNotNull(ccn.getArray(str));
	}

	@Test
	void testPrintResult() {
		
		ccn.printResult(map);
	}

}
