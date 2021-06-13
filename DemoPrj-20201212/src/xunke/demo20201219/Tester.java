/**
 * 
 */
package xunke.demo20201219;

/**
 * @author HY
 *
 */
public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Car c = new Car();
		c.run();
		
		Car c2 = new Car("¸£ÌØ",130000);
		c2.run();
		
		Car c3 = new Car(30000);
		c3.run();
		
		Car c4 = new Car("ÂêÉ¯À­µÙ");
		c3.run();
		
		Car.showInfo();
	}

}
