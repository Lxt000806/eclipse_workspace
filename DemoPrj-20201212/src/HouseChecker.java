import java.util.Scanner;

/**
 * ĳ����Ա��ʼ��������нN����ϣ�����йش幫����һ��60ƽ�׵ķ��ӣ����ڼ۸���200�򣬼��跿�Ӽ۸���ÿ��ٷ�֮K������
 * ���Ҹó���Աδ����н���䣬�Ҳ��Բ��ȣ����ý�˰��ÿ������N��ȫ�������������ʵڼ����ܹ��������׷��ӣ�����һ����нN�򣬷���200��
 * ����һ�У���������������N��10 <= N <= 50��, K��1 <= K <= 20�����м��õ����ո������
 * �������ڵ�20�����֮ǰ�����������׷��ӣ������һ������M����ʾ������Ҫ�ڵ�M�������£��������Impossible��
 * �������룺50 10
 * ���������8
 */

/**
 * 
 * @author HY
 *
 */
public class HouseChecker {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int salary,rate,deposit;
		int year = 1;
		double price = 200;
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("��������н�ͷ���������(�м��õ����ո����)��");
		salary = scanner.nextInt();
		rate = scanner.nextInt();
		
		if(salary>50 || salary<10) System.out.println("�������н������Χ������");
		else if (rate>20 || rate<1) System.out.println("����ķ��������ʳ�����Χ������");
		else {
			deposit = salary;
			while(deposit<price) {
				//System.out.printf("��%d��,���:%d,����:%f\n",year,deposit,price);
				year++;
				price = price * (1+rate/100.0);
				deposit = deposit + salary;
				if(year>20) {
					System.out.println("Impossible");
					break;
				}
			}
			if(year<=20) System.out.println(year);
		}
		

	}

}
