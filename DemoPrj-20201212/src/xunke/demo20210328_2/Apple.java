package xunke.demo20210328_2;

public class Apple implements Comparable<Apple>{
	private String color;
	private Integer weight;
	
	public Apple(String color, Integer weight) {
		super();
		this.color = color;
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "Apple [color=" + color + ", weight=" + weight + "]";
	}
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
	
	@Override
	public int hashCode() {
		return this.color.hashCode()+new Integer(this.weight).hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj==this) return true;
		if(obj==null) return false;
		if(!(obj instanceof Apple)) return false;
		
		Apple otherApple = (Apple)obj;
		return this.color.equals(otherApple.color) && this.weight==otherApple.weight;
	}
	
	@Override
	public int compareTo(Apple otherApple) {
		
//		if(this.weight>otherApple.weight)
//			return 1;
//		else if(this.weight<otherApple.weight)
//			return -1;
//		
//		return 0;
		return this.weight.compareTo(otherApple.weight);
	}
}
