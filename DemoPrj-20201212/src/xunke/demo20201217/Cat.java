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
	/**ë������ɫ*/
	String furColor;
	
	/**è������*/
	double weight;
	
	Random r = new Random();
	
	/**�ʺ�*/
	void sayHello() {
		System.out.printf("С%sè����Һã�����С%sè������%.2f����\n",furColor,furColor,weight);
	}
	
	/**����*/
	void climbTree() {
		
		if(weight>=5.0)
			System.out.printf("С%sè������%.2f����,û������������Ҫȥ������\n",furColor,weight);
		else
			System.out.printf("С%sè������%.2f����,��Ļ�����ʳ��������\n",furColor,weight);
	}
	
	/**�Ͳ�*/
	void eat() {
		double delta = r.nextInt(10)/10.0; //�������������㣬���Գ���10.0
		weight = weight + delta;
		System.out.printf("С%sè������һ�ٷ�������������%.2f�������%.2f����\n",furColor,delta,weight);
	}
	
	void keepFit() {
		double delta = r.nextInt(10)/10.0; 
		weight = weight - delta;
		System.out.printf("С%sè����ϰ�����࣬���ؼ�����%.2f�������%.2f����\n",furColor,delta,weight);
	}

}
