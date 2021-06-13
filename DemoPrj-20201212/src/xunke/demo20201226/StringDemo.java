/**
 * 
 */
package xunke.demo20201226;

/**
 * @author HY
 *
 */
public class StringDemo {

	/**
	 * @param args
	 */
	public static void main(java.lang.String[] args) {


		String str = " abcdefg ";
		System.out.println(str.length());
		System.out.println(str.trim().length()); //�����ҿո�ȥ��,���ַ�������

		String str2 = "abcxyzmn";
		System.out.println(str2.startsWith("abc")); //true ,�Ƿ���"abc"��ͷ
		System.out.println(str2.startsWith("xyz", 3)); // ���ַ������±�3��ʼ���Ƿ���"xyz"��ͷ
		System.out.println(str2.endsWith("zmn")); //�Ƿ���"zmn"��β
		
		System.out.println(str2.charAt(0)); //ͨ���±����ַ�
		System.out.println(str2.indexOf('m')); //ͨ���ַ����±�(��һ�γ���m��λ��)
		System.out.println(str2.indexOf('m', 5)); //���ַ������±�5��ʼ��Ѱ��m��λ��
		System.out.println(str2.lastIndexOf('m')); //�Ӻ���ǰ,�ҵ������һ��mֹͣ
		
		if(!str2.contains("kkk")) //װ�η�����Ϊ�˺��ã��䱾�ʻ���indexOf()
			System.out.println("û��kkk�Ĵ���");
		
		System.out.println(str2.substring(3,5)); //��ʼ��Χ(����),������Χ(������)�и�
		System.out.println(str2.concat("xyz")); //�ַ���ƴ��
		System.out.println(str2+"xyz"); //�﷨������
		
		System.out.println(str.equalsIgnoreCase("abcdEfg")); //���Դ�Сд�ıȽ��Ƿ����
		System.out.println("abcd".compareTo("abcd")); //�Ƚϴ�С;��ȷ���0 , С�ڷ���-1 , ���ڷ���1
		
		String str3 = "a"+"b"+"c"+"d"; //�漰���������ַ�������,�����������������;������StringBuffer������
		StringBuffer sb = new StringBuffer(); //�ַ�������,�ɱ��ַ�������
		sb.append("a").append("b").append("c").append("d") //��ʽ���;�����м���,����Ч��
		  .deleteCharAt(sb.length()-1); //ɾ�����һ���ַ�
		
		StringBuilder sb2 = new StringBuilder(); //��StringBuffer��API��ͬ,���ٶȸ���,�Ƽ�ʹ��
	}

}
