import java.util.Scanner;

/**
 * �ڱ���У԰��,û�����г�,�Ͽΰ��»�ܲ�����.��ʵ����,����ȥ���κ����鶼���ﳵ��,
 * ��Ϊ�ﳵ��Ҫ�ҳ���������ͣ����������,��Ҫ����һЩʱ��.�����ҵ����г�,��������
 * �����г���ʱ��Ϊ27��;ͣ��������ʱ��Ϊ23��;����ÿ������1.2��,�ﳵÿ������3.0�ס�
 * ���ж��߲�ͬ�ľ���ȥ����,���ﳵ�컹����·�졣
 * ����:
 * ����һ�У�����һ����������ʾһ�ΰ���Ҫ���ߵľ���,��λΪ�ס�
 * ���:
 * ���һ��,����ﳵ��,���һ��"Bike";�����·��,���һ��"Walk";���һ����,���һ��"All"��
 */

/**
 * 
 * @author HY
 *
 */
public class BikeWalk {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int startBike = 27;
		int closeBike = 23;
		double bikeV = 3.0;
		double walkV = 1.2;
		double bikeTime;
		double walkTime;
		int distance;
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("������Ҫ���ߵľ��룺");
		distance = scanner.nextInt();
		
		bikeTime = distance/bikeV + startBike + closeBike;
		walkTime = distance/walkV;
		if(bikeTime<walkTime)
			System.out.println("Bike");
		else if(bikeTime>walkTime)
			System.out.println("Walk");
		else if(bikeTime==walkTime)
			System.out.println("All");

	}

}
