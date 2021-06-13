/**
 * 
 */
package xunke.demo20210307_1;

/**
 * @author HY
 *
 */
public class InnerTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//创建静态内部类的实例(不需要外部实例的创建)
		ChessBoard.M m = new ChessBoard.M();
		m.work();

		//创建实例内部类，必须要有外部类的实例存在
		ChessBoard board = new ChessBoard();
		ChessBoard.Stone stone = board.new Stone();
		stone.run();
		
		ChessBoard.Stone stone2 = new ChessBoard().new Stone();
		stone2.run();
	}

}
