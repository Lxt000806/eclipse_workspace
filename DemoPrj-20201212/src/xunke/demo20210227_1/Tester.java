/**
 * 
 */
package xunke.demo20210227_1;

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

		Shape s = new Circle(2.0);
		showShapeInfo(s);
		
		s = new Rectangle(1.2,1.8);
		showShapeInfo(s);
	}

	private static void showShapeInfo(Shape shape) {
		System.out.println(shape.getArea());
		System.out.println(shape.getZC());
	}
}
