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
		System.out.println(this+"���ڳ�ʼ����ͷ.....ok!");

	}

	@Override
	public void work() {
		System.out.println(this+"���ڶ�ȡ��������.....ok!");

	}

	@Override
	public void pullout() {
		System.out.println(this+"�����������ݣ����д��̸�λ!");

	}

	@Override
	public String toString() {
		return String.format("%dT%s�ƶ�Ӳ��", this.capacity,this.brand);
	}

	
}
