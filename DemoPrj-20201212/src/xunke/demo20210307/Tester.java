/**
 * 
 */
package xunke.demo20210307;

/**
 * @author HY
 *
 */
public class Tester {

	/**
	 * һ��java�ļ��п����ж���࣬��ֻ��һ�����ǹ��е��Һ��ļ�����ͬ
	 * һ����ķ������η���Ҫô��public��Ҫô�ǰ������
	 * �����಻��ʵ����
	 * �������ñ�������ָ��������������У�
	 * 
	 */
	public static void main(String[] args) {
		
		Computer pc = new Computer();
		
		pc.setUSBDevice(new WebCam("�޼�"));
		pc.setUSBDevice(new ProtableHD("ϣ��",2));

		pc.shutdown();
	}

}
