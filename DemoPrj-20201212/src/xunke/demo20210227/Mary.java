/**
 * 
 */
package xunke.demo20210227;

/**
 * @author HY
 *
 */
public class Mary implements CollegeStudent {

	/* (non-Javadoc)
	 * @see xunke.demo20210227.Student#gotoSchool()
	 */
	@Override
	public void gotoSchool() {
		System.out.println(Student.SCHOOL_NAME+"mary���ҿ�������ѧ");

	}

	/* (non-Javadoc)
	 * @see xunke.demo20210227.Student#study()
	 */
	@Override
	public void study() {
		System.out.println("mary��������������ǿ����");

	}

	/* (non-Javadoc)
	 * @see xunke.demo20210227.Student#enjoyHoliday()
	 */
	@Override
	public void enjoyHoliday() {
		System.out.println("mary��������������ˣ�");

	}

	public void playGame() {
		System.out.println("mary����������Ϸ");
	}

	@Override
	public void takeCETTest() {
		System.out.println("mary�����ڱ�����");
		
	}

	@Override
	public void swim() {
		
		System.out.println("mary������ѧϰ����Ӿ");
	}
}
