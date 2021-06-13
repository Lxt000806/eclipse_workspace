/**
 * 
 */
package xunke.demo20201226;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date; //utility ����

/**
 * @author HY
 *
 */
public class DateDemo {

	/**
	 * ʱ����һ�����֣���1970��1��1�յ������������ĺ�����
	 * 
	 */
	public static void main(String[] args) {

		Date now = new Date(); //�õ���ǰϵͳʱ��
		//���ڷ���@Deprecated,�÷��������ڽ���JDK�в��ٱ�֧��
		System.out.println(now.toLocaleString()); //����ʱ���ַ���

		System.out.println(now.getTime()); //���ʱ�䳤����
		
		//ת������ʱ��ת�ַ���
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //HH:mm:ss ���Զ���ʱ���ʽ
		System.out.println(sdf.format(now));
		
		//�ַ���תʱ��
		String past = "2013-9-15 23:12:23";
		Date pastTime = null;
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf2.setLenient(false); //�ݴ��أ��ַ������벻�淶�ᱨ��
		try {
			pastTime = sdf2.parse(past);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//ʱ�䲿��ϸ�ڵĻ�ȡ(date part)
		Calendar c = Calendar.getInstance(); //�½�����,����ģʽ
		c.setTime(now);
		System.out.println(c.get(Calendar.YEAR)); //���
		System.out.println(c.get(Calendar.WEEK_OF_YEAR)); //����ڼ���
		
		//ʱ��ıȽ�
		System.out.println(pastTime.before(now));
		System.out.println(pastTime.after(now));
		System.out.println(pastTime.compareTo(now));
	}

}
