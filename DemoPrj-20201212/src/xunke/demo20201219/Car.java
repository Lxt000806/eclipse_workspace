/**
 * 
 */
package xunke.demo20201219;

/**
 * �γ�
 * @author HY
 *
 */
public class Car {

	/**����Ʒ��*/
	String brand; //ʵ���������ڶ���,ÿ��������һ�ݿ���
	
	/**�����۸�*/
	int price;
	
	/**
	 * ��̬����,�����౾��,Ҳ�������,����������֮��,��ͱ�������
	 * ��ֻ��һ�ݿ�����������������ʵ��������
	 * ��̬�����Ĺ�������ʵ������
	 * */
	static int count = 0;
	
	//���췽���Ķ�����,������ʹ��Car���ʱ����ӷ�������------> ���췽��������
	//������췽��:���췽�����ù��췽��
	
	//�޲ι��췽��
	public Car() {
		this("����",120000);
	}
	
	public Car(String brand) {
		this(brand,120000);
	}
	
	public Car(int price) {
		this("����",price);
	}
	
	/**
	 * ȫ�ι��췽��
	 * @param brand
	 * @param price
	 */
	public Car(String brand,int price) {
		this.brand = "("+brand+")";
		this.price = price;
		Car.count++; //���Է��ʾ�̬����
	}
	
	//ʵ������,ֻ��ͨ�����ñ������ܵ���,��Ϊʵ���������ڶ���
	void run() {
		System.out.println("һ��"+this.brand+"�Ƶļ�ֵΪ"+this.price+"�Ľγ�����ʻ");
	}
	
	void addOil() {
		
	}
	
	//��̬����������,ֻҪ�౻������,�÷������ܱ�������
	public static void showInfo() {
		System.out.println("���ܹ�������"+Car.count+"������");
		//��̬�������ܷ������зǾ�̬��Ա(ʵ����Ա)
	}

}
