import java.util.Scanner;

/**
 * 在北大校园里,没有自行车,上课办事会很不方便.但实际上,并非去办任何事情都是骑车快,
 * 因为骑车总要找车、开锁、停车、锁车等,这要耽误一些时间.假设找到自行车,开锁并车
 * 上自行车的时间为27秒;停车锁车的时间为23秒;步行每秒行走1.2米,骑车每秒行走3.0米。
 * 请判断走不同的距离去办事,是骑车快还是走路快。
 * 输入:
 * 输入一行，包含一个整数，表示一次办事要行走的距离,单位为米。
 * 输出:
 * 输出一行,如果骑车快,输出一行"Bike";如果走路快,输出一行"Walk";如果一样快,输出一行"All"。
 */

/**
 * 
 * @author HY
 *
 */
public class BikeWalk {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int startBike = 27;
		int closeBike = 23;
		double bikeV = 3.0;
		double walkV = 1.2;
		double bikeTime;
		double walkTime;
		int distance;
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入要行走的距离：");
		distance = scanner.nextInt();
		
		bikeTime = distance/bikeV + startBike + closeBike;
		walkTime = distance/walkV;
		if(bikeTime<walkTime)
			System.out.println("Bike");
		else if(bikeTime>walkTime)
			System.out.println("Walk");
		else if(bikeTime==walkTime)
			System.out.println("All");

	}

}
