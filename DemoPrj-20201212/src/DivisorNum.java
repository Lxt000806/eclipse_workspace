import java.util.Scanner;

/**
 * 
 */

/**
 * @author HY
 *
 */
public class DivisorNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("��������������a��b:");
		int a = scanner.nextInt();
		int b = scanner.nextInt();
		
		if(a>b) {
			divide(a,b);
		}else {
			divide(b,a);
		}

	}
	
	public static void divide(int a,int b) {
		while(b!=0)
		{
			int r = a%b;//rΪa��bȡ��
			a = b;
			b = r;
		}
		System.out.println("a��b�����Լ��Ϊ"+a);
	}

}
