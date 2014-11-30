package ua.nure.jfdi.conferenceapp.entities;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	String author;
	private String text;
	private long date;

	public Message(String givenAuthor, String givenText, long givenDate) {
		author = givenAuthor;
		text = givenText;
		date = givenDate;
	}

	public String getAuthor() {
		return author;
	}

	public String getText() {
		return text;
	}

	public long getDate() {
		return date;
	}

}
