package xunke.demo20210226;

public class Student {

	//���Եķ�װ
	private int age; 
	
	public void setAge(int age) {
		if(age>=7 && age<=14)
			this.age = age;
		else
			this.age = 7;
	}
	public void showInfo() {
		System.out.println("����+age+���ѧ��");
	}
}
