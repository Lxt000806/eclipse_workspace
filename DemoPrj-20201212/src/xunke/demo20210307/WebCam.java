package xunke.demo20210307;

/**
 * ��������ͷ
 * @author HY
 *
 */
public class WebCam implements USBDevice {

	private String brand;
	
	public WebCam(String brand) {
		super();
		this.brand = brand;
	}

	@Override
	public void plugin() {
		//this����toString�����ķ���ֵ
		System.out.println(this+"���ڽ���ͼ���ȡ�����ĳ�ʼ��������");
    
	}

	@Override
	public void work() {
		System.out.println(this+"���ڴ���ͼ����Ϣ������");

	}

	@Override
	public void pullout() {
		System.out.println(this+"��������ͼ����Ϣ������ڴ棬�ر�ϵͳ");

	}

	@Override
	public String toString() {
		return "WebCam [brand=" + brand + "]";
	}

	
}
