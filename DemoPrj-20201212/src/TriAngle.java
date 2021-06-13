import java.util.Scanner;

/**
 * 
 */

/**
 * 判断三角形类型
 * @author HY
 *
 */
public class TriAngle {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("请输入三角形的三条边:");
        double a=in.nextDouble();
        double b=in.nextDouble();
        double c=in.nextDouble();
        
        if(a>=1&&a<=200&&b>=1&&b<=200&&c>=1&&c<=200) { //输入是否合法
        
            if(a+b>c&&a+c>b&&b+c>a) { //判断是否为三角形
            
                if(a==b&&b==c)
                    System.out.println("等边三角形");
                else
                {
                    //是等腰
                    if((a==b)||(b==c)|(a==c))
                    {
                        if((a*a+b*b-c*c<0.1)||(a*a+c*c-b*b<0.1)||(b*b+c*c-a*a<0.1))//不要用a*a+b*b==c*c
                            System.out.println("等腰直角三角形");
                        else
                            System.out.println("等腰三角形");
                    }
                    //非等腰
                    else
                    {
                        if((a*a+b*b-c*c<0.1)||(a*a+c*c-b*b<0.1)||(b*b+c*c-a*a<0.1))
                            System.out.println("直角三角形");
                        else
                            System.out.println("一般三角形");
                    }
                }
            }
            else
                System.out.println("不是三角形");
        }
        else
            System.out.println("Wrong Format");

	}

}
