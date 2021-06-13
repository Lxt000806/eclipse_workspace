package xunke.demo20210410_1;

public class Mate40 implements Callable {

	public Mate40() {
		System.out.print("×é×°mate40...");
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
		System.out.println("mate40 is calling now");

	}

}
