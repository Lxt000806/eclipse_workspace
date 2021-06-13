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
		System.out.println(Student.SCHOOL_NAME+"mary：我开奔驰上学");

	}

	/* (non-Javadoc)
	 * @see xunke.demo20210227.Student#study()
	 */
	@Override
	public void study() {
		System.out.println("mary：东看西看，勉强过关");

	}

	/* (non-Javadoc)
	 * @see xunke.demo20210227.Student#enjoyHoliday()
	 */
	@Override
	public void enjoyHoliday() {
		System.out.println("mary：马尔代夫，我来了！");

	}

	public void playGame() {
		System.out.println("mary：我在玩游戏");
	}

	@Override
	public void takeCETTest() {
		System.out.println("mary：我在背单词");
		
	}

	@Override
	public void swim() {
		
		System.out.println("mary：我在学习自由泳");
	}
}
