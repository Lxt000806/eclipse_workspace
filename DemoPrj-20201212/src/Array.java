import java.util.Arrays;
import java.util.Scanner;

/**
 * �Ʋ�����������ָ����������: ���еĵ�һ���͵ڶ�������Ϊ1��������ÿ����������ǰ��2����֮�͡�
 * ����һ��������k��Ҫ��Ʋ����������е�k�����Ƕ��١�
 * ����
 * ����һ�У�����һ��������k����1 <= k <= 46��
 * ���
 * ���һ�У�����һ������������ʾ�Ʋ����������е�k�����Ĵ�С
 * �������룺19
 * ���������4181
 */

/**
 * @author HY
 *
 */
public class Array {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int arr[] = new int[46];
		int k;
		
		Arrays.fill(arr, 0,2,1); //��fill()�������arr[0]��arr[2]֮�������Ԫ��,��ֵΪ1
		System.out.println("��������Ҫ����ĵ�k������1 <= k <= 46����");
		Scanner scanner = new Scanner(System.in);
		k = scanner.nextInt();
		
		for(int i=2;i<=k-1;i++) {
			arr[i] = arr[i-2] + arr[i-1]; //����ÿ��������ǰ������֮��
		}
		
		System.out.println(arr[k-1]); //���������±�0��ʼ,��k�����±�Ϊk-1

	}

}
