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
	 * �����಻��ʵ����
	 * ���󷽷������з�����{}
	 * protected����������Լ̳нǶ���Ч�����ýǶ���Ч
	 * ˽������ֻ���Լ����Է��ʣ������޷�����
	 * 
	 */

	/**
	 * javabean���κ�һ������javaӦ�ó����class�ļ�������javabean
	 * ��װ������������Ϊ˽�У�����getter/setter����������з���
	 * ��һ��java�࣬���з�װ��ͬʱ����һ���޲ι��췽������������������д���࣬����ΪJavabean
	 * Javabean��һ��ǿ����д�淶
	 */
	public static void main(String[] args) {
		
		//�ӿڿ���ָ��һ��ʵ������ӿڵ����ʵ��������ֻ�ܵ����������Ľӿڲ���
		//�����ö��ֽӿ�ָ��Mary���󣬵��ǵ���������ͬ
		CollegeStudent stu = new Mary();
		Student stu1 = new Mary();
		Swimmable s = new Mary();
		
		runStudentLife(new Mary());
		runStudentLife(new David());

	}

	/**
	 * չʾѧ����ѧϰ����
	 * @param stu
	 */
	private static void runStudentLife(Student stu) {//ֻҪʵ��������ӿڵĶ����ܴ�����
		stu.gotoSchool();
		stu.study();
		stu.enjoyHoliday();
	}
}
