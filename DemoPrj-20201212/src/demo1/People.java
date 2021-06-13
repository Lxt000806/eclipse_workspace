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
			System.out.printf("%s这次考试%.2f分，成绩还不错，及格了\n",name,score);
		else
			System.out.printf("%s这次考试%.2f分，挂科了\n",name,score);
	}
	
	void study() {
		double delta = r.nextInt(10);
		score = score + delta;
		System.out.printf("%s学习一次，成绩提高了了%.2f分，现在%.2f分\n",name,delta,score);
	}
	
	void playGame() {
		double delta = r.nextInt(10);
		score = score - delta;
		System.out.printf("%s打了一次游戏，成绩降低了%.2f分，现在%.2f分\n",name,delta,score);
	}

}
