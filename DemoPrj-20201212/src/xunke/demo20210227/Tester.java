/**
 * 
 */
package xunke.demo20210227;

/**
 * @author HY
 *
 */
public class Tester {

	/**
	 * 抽象类不能实例化
	 * 抽象方法不能有方法体{}
	 * protected保护级别针对继承角度有效，引用角度无效
	 * 私有属性只有自己可以访问，子类无法访问
	 * 
	 */

	/**
	 * javabean：任何一个构成java应用程序的class文件，都叫javabean
	 * 封装：将属性设置为私有，配套getter/setter方法对其进行访问
	 * 若一个java类，进行封装，同时还有一个无参构造方法，符合以上条件书写的类，被称为Javabean
	 * Javabean是一种强制书写规范
	 */
	public static void main(String[] args) {
		
		//接口可以指向一个实现这个接口的类的实例，但是只能调用这个对象的接口部分
		//可以用多种接口指向Mary对象，但是调用能力不同
		CollegeStudent stu = new Mary();
		Student stu1 = new Mary();
		Swimmable s = new Mary();
		
		runStudentLife(new Mary());
		runStudentLife(new David());

	}

	/**
	 * 展示学生的学习生活
	 * @param stu
	 */
	private static void runStudentLife(Student stu) {//只要实现了这个接口的对象都能传进来
		stu.gotoSchool();
		stu.study();
		stu.enjoyHoliday();
	}
}
