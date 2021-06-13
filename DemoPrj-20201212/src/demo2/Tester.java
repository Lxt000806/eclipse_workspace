/**
 * 
 */
package demo2;

/**
 * @author HY
 *
 */
public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Book a = new Book();
		a.sell();
		
		Book b = new Book(43);
		b.sell();
		
		Book c = new Book("高等数学");
		c.sell();
		
		Book d = new Book("线性代数",22);
		d.sell();
		
		Book.showInfo();//静态方法尽量用类名来调用

	}

}
