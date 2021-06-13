package xunke.demo20210226;

public class Student {

	//属性的封装
	private int age; 
	
	public void setAge(int age) {
		if(age>=7 && age<=14)
			this.age = age;
		else
			this.age = 7;
	}
	public void showInfo() {
		System.out.println("我是+age+岁的学生");
	}
}
