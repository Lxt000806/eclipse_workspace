package xunke.demo20210314;

public class Annoymous {

	void work() {
		
		//�ֲ����������ڲ��࣬���������֣��Ϳ��Դ������ʵ��
		class M{
			public void showInfo() {
				System.out.println("m is working now!");
			}
		}
		
		//�ɴ������ʵ��
		M a = new M();
		M a1 = new M();
		
		
		//������һ��һ����ʹ�õ��࣬������Object�����࣬����û�����֣���ֻ����һ��
		new Object() {
			public void run() {
				System.out.println("��������ִ��");
			}
		}.run(); //ֱ�ӵ����ڲ���run()����
		
		new B() {//new class ??? extends B()
			public void show() {
				System.out.println("show��������д��");
			}
		}.show();
		
		
		
	}
}

class B{
	public void show() {
		System.out.println("class b is showing");
	}
}