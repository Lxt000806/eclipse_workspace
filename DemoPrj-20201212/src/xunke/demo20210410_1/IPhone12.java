package xunke.demo20210410_1;

public class IPhone12 implements Callable {

	public IPhone12() {
		System.out.print("×é×°iphone12....");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("...ok!");
		
	}
	@Override
	public void call() {
		System.out.println("ipone 12 is calling now");

	}

}
