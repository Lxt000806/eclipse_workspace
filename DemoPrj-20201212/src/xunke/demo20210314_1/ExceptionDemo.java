/**
 * 
 */
package xunke.demo20210314_1;

/**
 * @author HY
 *
 */
public class ExceptionDemo {

	public static void main(String[] args) {
		
		int a = 10;
		int b = 2;
		int c = -1;
		int[] m = new int[3];
		String str = null;
		
		//try块是一个监控快，能够监控其内部的语句是否抛出异常，并尝试捕获
        try {
        	System.out.println("开辟了200M内存空间");
        	c = a/b;
        	Integer.parseInt("123");
        	m[2]=23;
        	char ch = str.charAt(0);
        	System.out.println("HelloWorld"); //try块中抛出异常后的语句，将不再运行
        }catch(ArithmeticException e) {
        	System.out.println("数学异常被捕获了，没有再抛出方法了");
        	//e.printStackTrace(); 打印异常位置的跟踪
        	c = 0;
        }catch(NumberFormatException e) {
        	System.out.println("数据格式化异常被发现了，目前已经处理");
        }catch(ArrayIndexOutOfBoundsException e) {
        	System.out.println("数组下标越界异常被发现了，已经解决！");
        }catch(NullPointerException e) {
        	System.out.println("空指针异常已经被发现，并处理了！");
        }catch(Exception e) {
        	System.out.println("异常被捕获了！");
        }catch(Throwable e) {
        	System.out.println("错误也被捕获了！");
        }//catch级联块     
        finally {//Finally块为了有效的释放申请的资源，只怕System.exit(0)
        	System.out.println("释放了200M内存空间");
        }
        
		System.out.println("c="+c);
	}

}
/**
 * 
 * 
 */
