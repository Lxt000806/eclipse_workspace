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
		
		Book c = new Book("�ߵ���ѧ");
		c.sell();
		
		Book d = new Book("���Դ���",22);
		d.sell();
		
		Book.showInfo();//��̬��������������������

	}

}
