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
		
		System.out.print("������ѧ���ɼ���");	
		score = scanner.nextFloat();
		key = (int) (score/10);
		
		if(score>100 || score<0) {
			System.out.println("���벻�Ϸ�");
		}else {
			switch(key) {
			    case 10:
			    case 9:
			    	System.out.println("����");
			    	break;
			    case 8:
			    case 7:
			    	System.out.println("��");
		    	    break;
			    case 6:
			    	System.out.println("����");
		    	    break;
		    	default:
		    		System.out.println("������");
		    		break;
			}
		}
	}

}
