package xunke.demo20210307;

public class Computer {

	private USBDevice dev1;
	private USBDevice dev2;
	
	public void setUSBDevice(USBDevice dev) {
		if(dev1==null) {
			dev1 = dev;
			dev1.plugin();
			dev1.work();
		}
		else if(dev2==null) {
			dev2 = dev;
			dev2.plugin();
			dev2.work();
		}
		else
			System.out.println("所有USB口已经插满！");
	}
	
	public void shutdown() {
		if(dev1!=null) dev1.pullout();
		if(dev2!=null) dev2.pullout();
	}
}
