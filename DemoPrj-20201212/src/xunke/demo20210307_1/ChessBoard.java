package xunke.demo20210307_1;

/**
 * �ⲿ�ࣨout class��,������
 * һ��������ķ������η���Ҫô��public��Ҫô�ǰ������
 */
public class ChessBoard {



	private String brand;
	private String color="red";
	
	/**
	 * �ڲ��ࣨinner class��
	 * ���������ڲ��࣬���ַ������η�����ʹ��
	 * �ڲ�����Է����ⲿ���������Դ�����ܷ������η�������
	 */
	
	//ʵ���ڲ���
	class Stone{
		
		void run() {
			brand = "abc";
			System.out.println("stone is running"+ChessBoard.this.color);
		}
	}
	
	//��̬�ڲ��ֻ࣬�ܷ�����ľ�̬��Դ
	public static class M {

		void work() {
			System.out.println("m is invoked now!");
		}
	}
}

