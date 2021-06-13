/**
 * 
 */
package xunke.demo20210410_1;

/**
 * ��ӡ������
 * @author HY
 *
 */
public class Singleton_PrinterFactory {
	
	private static final Singleton_PrinterFactory FACTORY = new Singleton_PrinterFactory();
	
	public static Singleton_PrinterFactory getInstance() {
		return FACTORY;
	}

	//������Ĺ��췽����Ϊ˽�е�
	private Singleton_PrinterFactory() {
		System.out.print("������ӡ������.......");
		try {
			Thread.sleep(3000);//�ӳ�3��
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("....ok!");
	}
}
