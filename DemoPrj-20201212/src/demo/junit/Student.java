/**
 * 
 */
package demo.junit;

import java.util.ArrayList;

/**
 * @author HY
 *
 */
public class Student {

	private int age;
	private String name;
	private ArrayList<Double> scores = new ArrayList<Double>();
	
	public Student(String name,int age) {
		this.name = name;
		this.age = age;
	}
	
	public ArrayList<Double> initScores() {
		scores.add(90.0);
		scores.add(95.5);
		scores.add(99.0);
		
		return scores;
	}

	public int getAge() {
		return age;
	}

	public boolean setAge(int age) {
		if(age<=28) {
			this.age = age;
			return true;
		}
		return false;
					
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Double> getScores() {
		return scores;
	}

	public void setScores(ArrayList<Double> scores) {
		this.scores = scores;
	}
	
	
	
}
