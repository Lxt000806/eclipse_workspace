/**
 * 
 */
package xunke.demo20201217;

import java.util.Random;

/**
 * @author HY
 *
 */
public class Cat {
	/**毛发的颜色*/
	String furColor;
	
	/**猫的重量*/
	double weight;
	
	Random r = new Random();
	
	/**问好*/
	void sayHello() {
		System.out.printf("小%s猫：大家好，我是小%s猫，体重%.2f公斤\n",furColor,furColor,weight);
	}
	
	/**爬树*/
	void climbTree() {
		
		if(weight>=5.0)
			System.out.printf("小%s猫：体重%.2f公斤,没爬上树，看来要去健身了\n",furColor,weight);
		else
			System.out.printf("小%s猫：体重%.2f公斤,身材还不错，食堂我来了\n",furColor,weight);
	}
	
	/**就餐*/
	void eat() {
		double delta = r.nextInt(10)/10.0; //整数除法都是零，所以除于10.0
		weight = weight + delta;
		System.out.printf("小%s猫：吃了一顿饭，体重增加了%.2f公斤，体重%.2f公斤\n",furColor,delta,weight);
	}
	
	void keepFit() {
		double delta = r.nextInt(10)/10.0; 
		weight = weight - delta;
		System.out.printf("小%s猫：练习好辛苦，体重减少了%.2f公斤，体重%.2f公斤\n",furColor,delta,weight);
	}

}
