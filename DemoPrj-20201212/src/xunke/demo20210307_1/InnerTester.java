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
		
		//������̬�ڲ����ʵ��(����Ҫ�ⲿʵ���Ĵ���)
		ChessBoard.M m = new ChessBoard.M();
		m.work();

		//����ʵ���ڲ��࣬����Ҫ���ⲿ���ʵ������
		ChessBoard board = new ChessBoard();
		ChessBoard.Stone stone = board.new Stone();
		stone.run();
		
		ChessBoard.Stone stone2 = new ChessBoard().new Stone();
		stone2.run();
	}

}
