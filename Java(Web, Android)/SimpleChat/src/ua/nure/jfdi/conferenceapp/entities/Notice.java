package ua.nure.jfdi.conferenceapp.entities;

import java.util.Date;

public class Notice {
	private String title;
	private String text;
	private Date date;

	public Notice(String title, String text,long date) {
		
		this.title = title;
		this.text = text;
		this.date = new Date(date);
	}

	public Date getDate() {
		return date;
	}

	public String getTitle() {
		return title;
	}

	public String getText() {
		return text;
	}

}
