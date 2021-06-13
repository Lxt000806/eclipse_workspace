/**
 * 
 */
package demo1;

/**
 * @author HY
 *
 */
public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		People a = new People();
		a.name = "王有才";
		a.score = 77;
		
		a.playGame();
		a.playGame();
		a.playGame();
		a.exam();
		
		People b = new People();
		b.name = "钱多多";
		b.score = 66;
		
		b.study();
		b.exam();

	}

}
