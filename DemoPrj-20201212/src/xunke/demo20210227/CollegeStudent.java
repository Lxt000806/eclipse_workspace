package xunke.demo20210227;

/**
 * 子接口继承父接口，子接口将获得父接口的所有抽象方法，形成更大规模的接口
 * 接口可以继承多个接口
 */
public interface CollegeStudent extends Student,Swimmable {

	void takeCETTest();
}
