package it.uniroma3.dia.db1.jdbc.model;

import java.util.Date;

public class Exam {

	private int score;
	private String course;
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String toString() {
		return "{ score:" + score + ", course:" + course + ", date:" + date + "}";
	}
}
