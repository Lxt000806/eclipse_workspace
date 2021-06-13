package xunke.demo20210321;
/**
 * 无效中国移动手机号码异常
 * @author HY
 *
 */
public class InvalidCMPhoneCodeException extends RuntimeException {

	public InvalidCMPhoneCodeException() {
		// TODO Auto-generated constructor stub
	}

	public InvalidCMPhoneCodeException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidCMPhoneCodeException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidCMPhoneCodeException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidCMPhoneCodeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
