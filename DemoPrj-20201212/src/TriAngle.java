import java.util.Scanner;

/**
 * 
 */

/**
 * �ж�����������
 * @author HY
 *
 */
public class TriAngle {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("�����������ε�������:");
        double a=in.nextDouble();
        double b=in.nextDouble();
        double c=in.nextDouble();
        
        if(a>=1&&a<=200&&b>=1&&b<=200&&c>=1&&c<=200) { //�����Ƿ�Ϸ�
        
            if(a+b>c&&a+c>b&&b+c>a) { //�ж��Ƿ�Ϊ������
            
                if(a==b&&b==c)
                    System.out.println("�ȱ�������");
                else
                {
                    //�ǵ���
                    if((a==b)||(b==c)|(a==c))
                    {
                        if((a*a+b*b-c*c<0.1)||(a*a+c*c-b*b<0.1)||(b*b+c*c-a*a<0.1))//��Ҫ��a*a+b*b==c*c
                            System.out.println("����ֱ��������");
                        else
                            System.out.println("����������");
                    }
                    //�ǵ���
                    else
                    {
                        if((a*a+b*b-c*c<0.1)||(a*a+c*c-b*b<0.1)||(b*b+c*c-a*a<0.1))
                            System.out.println("ֱ��������");
                        else
                            System.out.println("һ��������");
                    }
                }
            }
            else
                System.out.println("����������");
        }
        else
            System.out.println("Wrong Format");

	}

}
