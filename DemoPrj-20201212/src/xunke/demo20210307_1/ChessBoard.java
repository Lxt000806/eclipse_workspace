package xunke.demo20210307_1;

/**
 * 外部类（out class）,顶级类
 * 一个顶级类的访问修饰符，要么是public，要么是包级别的
 */
public class ChessBoard {



	private String brand;
	private String color="red";
	
	/**
	 * 内部类（inner class）
	 * 方法级别内部类，四种访问修饰符都可使用
	 * 内部类可以访问外部类的所有资源，不受访问修饰符的限制
	 */
	
	//实例内部类
	class Stone{
		
		void run() {
			brand = "abc";
			System.out.println("stone is running"+ChessBoard.this.color);
		}
	}
	
	//静态内部类，只能访问类的静态资源
	public static class M {

		void work() {
			System.out.println("m is invoked now!");
		}
	}
}

