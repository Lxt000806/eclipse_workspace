/**
 * ��дһ����Ʊ���������������ܹ��������һע36ѡ7��Ʊ���롣
 * ����36ѡ7�� һע���Ǵ�36�������������ȡ7���� ��ѡ��������Щ�����С�������� ʹ��Arrays.sort() ��̬������
 */
package demo20210107;

import java.util.Arrays;
import java.util.Random;

/**
 * @author HY
 *
 */
public class LotteryNumberBuilder {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int a[] = new int[36];
		int b[] = new int[7];
		Random r = new Random();
		
		for(int i=0; i<36; i++) {
			 a[i] = i + 1; //��ʼ����ֵ
		}
		
		for (int i=0; i<7; i++) { //�����ȡ7����
			int index = r.nextInt(36-i); //�������������±�
			b[i] = a[index];
			swaps(a,index,35-i); //���� a[index] �� a[35-i] ,ʹa[index]������ŵ����������,��֤���ظ�ȡֵ	
		}
		
		Arrays.sort(b); // ��������
		
		System.out.println(Arrays.toString(b)); //���������
		
	}
	
	// �����������±�a �� �±�b����ֵ
	public static void swaps(int array[],int a, int b){	
		int temp = array[a];
		array[a] = array[b];
		array[b] = temp;
	}
	

}
