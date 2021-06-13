package xunke.demo20210314;

public class W {
	
	public void work() {
		
		new Object() {
			public void test() {
				System.out.println("testing now!");
			}
		}.test();
		
		M m = new M() {
		    @Override
		    public void go() {
				System.out.println("gogogo");
			}
		};
		
		m.go();
		m.go();
		
		N n = new N(){//new class ??? implement N()

			@Override
			public void swim() {
				System.out.println("swimming now!");		
			}
			
		};
		//让一个匿名类可调用多次
		n.swim();
		n.swim();
	}

}

class M{
	public void go() {
		System.out.println("clss M is going now!");
	}
}

interface N{
	void swim();
}