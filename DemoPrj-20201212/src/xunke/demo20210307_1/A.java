package xunke.demo20210307_1;

public class A {

	//ʵ�����Եļ򵥳�ʼ��
	int x = 20;
	static int y = 30;
	
	static {
		/**
		 * ��̬��û�����֣�������ص�ʱ���Զ�����,��ɾ�̬�����ĸ��ӳ�ʼ��
		 * ��̬��ֻ������һ�Σ���Ϊ��ֻ�����һ��
		 * ��̬�����޷�����ʵ�����ԣ�ֻ�ܵ��þ�̬���Ժͷ���
		 */
		y++;
		System.out.println("static block is invoked now!");
	}
	
	static {
		/**
		 * һ����ľ�̬������ж�������౻����ʱ��������������
		 */
		System.out.println("static block is invoked now!");
	}
	
	public A(){
		/**
		 * ���췽����ÿ�δ��������ʱ��Ҫ����һ��
		 */
		//ʵ�����Եĸ��ӳ�ʼ��,�ڹ��췽������д
		x++;
		x--;
		x = x-2;
		System.out.println("construct A is invoked now��");
	}
	
}
