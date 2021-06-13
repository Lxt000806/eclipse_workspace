package xunke.demo20210307;

/**
 * 网络摄像头
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
		//this调用toString方法的返回值
		System.out.println(this+"正在进行图像获取部件的初始化。。。");
    
	}

	@Override
	public void work() {
		System.out.println(this+"正在传送图像信息。。。");

	}

	@Override
	public void pullout() {
		System.out.println(this+"发送最后的图像信息，清空内存，关闭系统");

	}

	@Override
	public String toString() {
		return "WebCam [brand=" + brand + "]";
	}

	
}
