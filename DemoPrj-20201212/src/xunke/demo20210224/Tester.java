/**
 * 
 */
package xunke.demo20210224;

/**
 * @author HY
 *
 */
public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		B b = new B();
		b.method3();
		
		Abstractdemo a = new B();
		a.method3();
		
		C c = new C();
		c.method3();
		
		a = new C();
		a.method3();
	}

}
