package xunke.demo20210307;

public class ProtableHD implements USBDevice {

	private String brand;
	private int capacity;
	
	public ProtableHD(String brand, int capacity) {
		super();
		this.brand = brand;
		this.capacity = capacity;
	}

	@Override
	public void plugin() {
		System.out.println(this+"正在初始化磁头.....ok!");

	}

	@Override
	public void work() {
		System.out.println(this+"正在读取磁盘数据.....ok!");

	}

	@Override
	public void pullout() {
		System.out.println(this+"发送最后的数据，进行磁盘复位!");

	}

	@Override
	public String toString() {
		return String.format("%dT%s移动硬盘", this.capacity,this.brand);
	}

	
}
