/**
 * 
 */
package xunke.demo20210227_1;

/**
 * @author HY
 *
 */
public class Rectangle implements Shape {

	private double width,height;
	
	
	public Rectangle(double width, double height) {
		super();
		this.width = width;
		this.height = height;
	}

	/* (non-Javadoc)
	 * @see xunke.demo20210227_1.Shape#getArea()
	 */
	@Override
	public double getArea() {
		// TODO Auto-generated method stub
		return this.width*this.height;
	}

	/* (non-Javadoc)
	 * @see xunke.demo20210227_1.Shape#getZC()
	 */
	@Override
	public double getZC() {
		// TODO Auto-generated method stub
		return (this.width+this.height)*2;
	}

}
