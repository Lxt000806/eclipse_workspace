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
		
		//try����һ����ؿ죬�ܹ�������ڲ�������Ƿ��׳��쳣�������Բ���
        try {
        	System.out.println("������200M�ڴ�ռ�");
        	c = a/b;
        	Integer.parseInt("123");
        	m[2]=23;
        	char ch = str.charAt(0);
        	System.out.println("HelloWorld"); //try�����׳��쳣�����䣬����������
        }catch(ArithmeticException e) {
        	System.out.println("��ѧ�쳣�������ˣ�û�����׳�������");
        	//e.printStackTrace(); ��ӡ�쳣λ�õĸ���
        	c = 0;
        }catch(NumberFormatException e) {
        	System.out.println("���ݸ�ʽ���쳣�������ˣ�Ŀǰ�Ѿ�����");
        }catch(ArrayIndexOutOfBoundsException e) {
        	System.out.println("�����±�Խ���쳣�������ˣ��Ѿ������");
        }catch(NullPointerException e) {
        	System.out.println("��ָ���쳣�Ѿ������֣��������ˣ�");
        }catch(Exception e) {
        	System.out.println("�쳣�������ˣ�");
        }catch(Throwable e) {
        	System.out.println("����Ҳ�������ˣ�");
        }//catch������     
        finally {//Finally��Ϊ����Ч���ͷ��������Դ��ֻ��System.exit(0)
        	System.out.println("�ͷ���200M�ڴ�ռ�");
        }
        
		System.out.println("c="+c);
	}

}
/**
 * 
 * 
 */
