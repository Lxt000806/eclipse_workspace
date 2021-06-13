/**
 * 
 */
package xunke.demo20210227_1;

/**
 * @author HY
 *
 */
public class Circle implements Shape {

	private double radius;
	
	public Circle(double radius) {
		super();
		this.radius = radius;
	}

	/* (non-Javadoc)
	 * @see xunke.demo20210227_1.Shape#getArea()
	 */
	@Override
	public double getArea() {
		// TODO Auto-generated method stub
		return Math.PI*this.radius*this.radius;
	}

	/* (non-Javadoc)
	 * @see xunke.demo20210227_1.Shape#getZC()
	 */
	@Override
	public double getZC() {
		// TODO Auto-generated method stub
		return 2.0*Math.PI*this.radius;
	}

}
