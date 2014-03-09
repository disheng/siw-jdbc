package it.uniroma3.dia.db1.jdbc.persistence;

import it.uniroma3.dia.db1.jdbc.model.Exam;
import it.uniroma3.dia.db1.jdbc.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ExamRepository {

	/*
	 * CREATE TABLE exams ( student_code integer, score integer, course text,
	 * date date, CONSTRAINT exams_student_code_fkey FOREIGN KEY (student_code)
	 * REFERENCES students (code) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO
	 * ACTION )
	 */
	private DataSource dataSource;

	public ExamRepository() {
		dataSource = new DataSource();
	}

	public void persist(Exam exam, Student s) throws Exception {
		Connection connection = this.dataSource.getConnection();

		String insert = "insert into exams(student_code, score, course, date) values (?,?,?,?)";

		PreparedStatement statement = connection.prepareStatement(insert);
		statement.setInt(1, s.getCode());
		statement.setInt(2, exam.getScore());
		statement.setString(3, exam.getCourse());
		statement.setDate(4, new java.sql.Date(exam.getDate().getTime()));
		statement.executeUpdate();

		// release resources
		statement.close();
		connection.close();
	}

	public void delete(Exam exam, Student s) throws Exception {
		Connection connection = this.dataSource.getConnection();

		String insert = "delete from exams where student_code = ? and course = ? and date = ?";

		PreparedStatement statement = connection.prepareStatement(insert);
		statement.setInt(1, s.getCode());
		statement.setString(2, exam.getCourse());
		statement.setDate(3, new java.sql.Date(exam.getDate().getTime()));
		statement.executeUpdate();

		// release resources
		statement.close();
		connection.close();
	}
}
