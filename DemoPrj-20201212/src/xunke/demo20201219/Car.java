/**
 * 
 */
package xunke.demo20201219;

/**
 * 轿车
 * @author HY
 *
 */
public class Car {

	/**汽车品牌*/
	String brand; //实例变量属于对象,每个对象都有一份拷贝
	
	/**汽车价格*/
	int price;
	
	/**
	 * 静态变量,属于类本身,也叫类变量,在类加载完成之后,其就被创建了
	 * 其只有一份拷贝，被这个类的所有实例所共享
	 * 静态变量的构建早于实例变量
	 * */
	static int count = 0;
	
	//构造方法的多样性,让我们使用Car类的时候更加方便易用------> 构造方法的重载
	//层叠构造方法:构造方法调用构造方法
	
	//无参构造方法
	public Car() {
		this("丰田",120000);
	}
	
	public Car(String brand) {
		this(brand,120000);
	}
	
	public Car(int price) {
		this("丰田",price);
	}
	
	/**
	 * 全参构造方法
	 * @param brand
	 * @param price
	 */
	public Car(String brand,int price) {
		this.brand = "("+brand+")";
		this.price = price;
		Car.count++; //可以访问静态变量
	}
	
	//实例方法,只能通过引用变量才能调用,因为实例方法属于对象
	void run() {
		System.out.println("一辆"+this.brand+"牌的价值为"+this.price+"的轿车在行驶");
	}
	
	void addOil() {
		
	}
	
	//静态方法属于类,只要类被加载了,该方法就能被调用了
	public static void showInfo() {
		System.out.println("您总共创建了"+Car.count+"辆汽车");
		//静态方法不能访问所有非静态成员(实例成员)
	}

}
