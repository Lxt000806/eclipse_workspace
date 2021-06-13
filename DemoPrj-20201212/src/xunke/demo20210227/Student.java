package xunke.demo20210227;

public interface Student {

	//static final
	String SCHOOL_NAME = "福州大学";
	
	public void gotoSchool();
	public void study();
	public void enjoyHoliday();
}
/**
 * 接口：极度抽象类（最干瘪的类）
 * 接口是抽象类一种特殊的形态，也不能创建实例
 * 接口里所有的方法都是公有抽象方法，所有的变量都是常量
 */
