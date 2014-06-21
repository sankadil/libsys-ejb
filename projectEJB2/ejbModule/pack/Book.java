/*
 * Created on Aug 7, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pack;

import java.io.Serializable;

/**
 * @author sankadil
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Book implements Serializable {
	private String author, title,classno,isbn,subject,assno =null;
	/**
	 * @return
	 */
	public String getAssno() {
		return assno;
	}

	/**
	 * @return
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @return
	 */
	public String getClassno() {
		return classno;
	}

	/**
	 * @return
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * @return
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param string
	 */
	public void setAssno(String string) {
		assno = string;
	}

	/**
	 * @param string
	 */
	public void setAuthor(String string) {
		author = string;
	}

	/**
	 * @param string
	 */
	public void setClassno(String string) {
		classno = string;
	}

	/**
	 * @param string
	 */
	public void setIsbn(String string) {
		isbn = string;
	}

	/**
	 * @param string
	 */
	public void setSubject(String string) {
		subject = string;
	}

	/**
	 * @param string
	 */
	public void setTitle(String string) {
		title = string;
	}

}
