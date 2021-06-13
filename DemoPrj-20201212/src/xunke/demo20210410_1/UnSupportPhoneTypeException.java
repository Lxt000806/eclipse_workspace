/**
 * 
 */
package xunke.demo20210410_1;

/**
 * 不支持的手机类型异常
 * @author HY
 *
 */
public class UnSupportPhoneTypeException extends RuntimeException {

	/**
	 * 
	 */
	public UnSupportPhoneTypeException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public UnSupportPhoneTypeException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public UnSupportPhoneTypeException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UnSupportPhoneTypeException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public UnSupportPhoneTypeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
