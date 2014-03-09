package it.uniroma3.dia.db1.jdbc.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Student {

	private String firstName;
	private String lastName;
	private int code;
	private Date birthDate;
	private List<Exam> exams;

	public Student() {
		this.exams = new LinkedList<Exam>();
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getCode() {
		return code;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public List<Exam> getExams() {
		return this.exams;
	}

	public void setExams(List<Exam> exams) {
		this.exams = exams;
	}

	public void addExam(Exam e) {
		this.exams.add(e);
	}

	public String toString() {
		return "{code: " + code + ", firstName:" + firstName + ", lastName:" + lastName
				+ ", birthDate: " + birthDate + ", exams:" + exams.toString()+"}";
	}
}
