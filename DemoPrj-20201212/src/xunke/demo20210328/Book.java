package xunke.demo20210328;

import java.io.Serializable;

//public class Book implements Serializable{
public class Book{
	private String bookName;
	
	public Book(String bookName) {
		super();
		this.bookName = bookName;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	
	@Override
	public String toString() {
		return "Book [bookName=" + bookName + "]";
	}
	
	
}
