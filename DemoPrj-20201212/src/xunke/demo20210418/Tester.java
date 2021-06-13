/**
 * 
 */
package xunke.demo20210418;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * @author HY
 *
 */
public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Workable w = new A();
		w.method1();
		w.method2();
		w.method3();

		
		Workable w1 = new WorkableAdapter() {			
		};
		
		w1.method1();
		
		ArrayList al;
	}

}
