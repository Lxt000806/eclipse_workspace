import java.util.Scanner;

/**
 * 
 */

/**
 * @author HY
 *
 */
public class StudentRank {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Float score;
		int key;
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("请输入学生成绩：");	
		score = scanner.nextFloat();
		key = (int) (score/10);
		
		if(score>100 || score<0) {
			System.out.println("输入不合法");
		}else {
			switch(key) {
			    case 10:
			    case 9:
			    	System.out.println("优秀");
			    	break;
			    case 8:
			    case 7:
			    	System.out.println("良");
		    	    break;
			    case 6:
			    	System.out.println("及格");
		    	    break;
		    	default:
		    		System.out.println("不及格");
		    		break;
			}
		}
	}

}
