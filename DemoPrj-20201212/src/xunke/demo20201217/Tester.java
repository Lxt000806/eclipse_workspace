/**
 * 
 */
package xunke.demo20201217;

/**
 * @author HY
 *
 */
public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Cat c = new Cat(); //�ڶ��д�����һ�����ʵ������
		c.furColor = "��";
		c.weight = 5.5;
		
		c.sayHello();
		c.climbTree();
		c.keepFit();
		c.climbTree();
		
		Cat c2 = new Cat();
		c2.furColor = "��";
		c2.weight = 3.6;
		
		c2.sayHello();
		c2.climbTree();
		c2.eat();
		c2.climbTree();


	}

}
