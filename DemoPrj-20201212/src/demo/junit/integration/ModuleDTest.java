package demo.junit.integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModuleDTest {

	ModuleD moduleD = new ModuleD();
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testSetModuleC() {
		moduleD.setModuleC(new ModuleC());
	}

	@Test
	void testOperate() {
		moduleD.operate("111aaa", "111");
		moduleD.operate("111", "111aaa");
	}

}
