/**
 * 
 */
package demo1;

import java.util.Random;

/**
 * @author HY
 *
 */
public class People {
	
	String name;
	double score;
	
	Random r = new Random();
	
	void exam() {
		if(score>=60)
			System.out.printf("%s��ο���%.2f�֣��ɼ�������������\n",name,score);
		else
			System.out.printf("%s��ο���%.2f�֣��ҿ���\n",name,score);
	}
	
	void study() {
		double delta = r.nextInt(10);
		score = score + delta;
		System.out.printf("%sѧϰһ�Σ��ɼ��������%.2f�֣�����%.2f��\n",name,delta,score);
	}
	
	void playGame() {
		double delta = r.nextInt(10);
		score = score - delta;
		System.out.printf("%s����һ����Ϸ���ɼ�������%.2f�֣�����%.2f��\n",name,delta,score);
	}

}
