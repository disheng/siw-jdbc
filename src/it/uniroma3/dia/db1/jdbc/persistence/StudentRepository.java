package it.uniroma3.dia.db1.jdbc.persistence;

import it.uniroma3.dia.db1.jdbc.model.Exam;
import it.uniroma3.dia.db1.jdbc.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class StudentRepository {

	private DataSource dataSource;
	private ExamRepository examsRepository;

	public StudentRepository() {
		dataSource = new DataSource();
		this.examsRepository = new ExamRepository();
	}

	public void persist(Student student) throws SQLException {
		Connection connection = this.dataSource.getConnection();
		PreparedStatement statement = null;
		try {
			if (findByPrimaryKey(student.getCode()) != null) {
				System.err.println("the student already exists");
				return;
			}

			String insert = "insert into students(code, firstname, lastname, birthDate) values (?,?,?,?)";

			statement = connection.prepareStatement(insert);
			statement.setInt(1, student.getCode());
			statement.setString(2, student.getFirstName());
			statement.setString(3, student.getLastName());

			Date t = new Date();
			t.getTime();
			statement.setDate(4, new java.sql.Date(student.getBirthDate().getTime()));
			statement.executeUpdate();

			for (Exam exam : student.getExams()) {
				// TODO se ci sono degli errori qui? che succede?
				// Come possiamo fare in modo che: o inserisco tutti gli esami e
				// lo studente o niente?
				this.examsRepository.persist(exam, student);
			}
			// TODO
			// Che succede se eccezione prima?
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			statement.close();
			connection.close();
		}
	}

	public void delete(Student student) throws Exception {
		Connection connection = this.dataSource.getConnection();

		for (Exam exam : student.getExams()) {
			// TODO voglio eliminare tutto
			this.examsRepository.delete(exam, student);
		}

		String insert = "delete from students where code = ?";

		PreparedStatement statement = connection.prepareStatement(insert);
		statement.setInt(1, student.getCode());
		statement.executeUpdate();

		// release resources
		statement.close();
		connection.close();
	}

	public Student findByPrimaryKey(int code) throws Exception {

		Connection connection = this.dataSource.getConnection();
		String query = "select * from students where code=?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, code);
		ResultSet result = statement.executeQuery();
		Student student = null;
		if (result.next()) {
			if (student == null) {
				long secs;
				java.util.Date birthDate;

				student = new Student();
				student.setCode(result.getInt("code"));
				student.setFirstName(result.getString("firstname"));
				student.setLastName(result.getString("lastname"));
				secs = result.getDate("birthdate").getTime();
				birthDate = new java.util.Date(secs);
				student.setBirthDate(birthDate);
			}

			if (result.getString("course") != null) {
				Exam e = new Exam();
				e.setCourse(result.getString("course"));
				e.setDate(new java.util.Date(result.getDate("date").getTime()));
				e.setScore(result.getInt("score"));
				student.addExam(e);
			}
		}

		// release resources
		result.close();
		statement.close();
		connection.close();

		return student;
	}

	public List<Student> findAll() throws Exception {
		List<Student> students = new LinkedList<Student>();
		Connection connection = this.dataSource.getConnection();
		String query = "select * from students left join exams on student_code = code order by code";
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet result = statement.executeQuery();
		Student prevStudent = null;
		while (result.next()) {
			if (prevStudent == null || result.getInt("code") != prevStudent.getCode()) {
				long secs;
				java.util.Date birthDate;

				Student student = new Student();
				student.setCode(result.getInt("code"));
				student.setFirstName(result.getString("firstname"));
				student.setLastName(result.getString("lastname"));
				secs = result.getDate("birthdate").getTime();
				birthDate = new java.util.Date(secs);
				student.setBirthDate(birthDate);
				prevStudent = student;

				students.add(student);
			}

			if (result.getString("course") != null) {
				Exam e = new Exam();
				e.setCourse(result.getString("course"));
				e.setDate(new java.util.Date(result.getDate("date").getTime()));
				e.setScore(result.getInt("score"));
				prevStudent.addExam(e);
			}
		}

		// release resources
		result.close();
		statement.close();
		connection.close();

		return students;
	}

	public List<Student> findByBirthDate(java.util.Date birthDate) throws Exception {
		List<Student> students = new LinkedList<Student>();
		Connection connection = this.dataSource.getConnection();
		String query = "select * from students left join exams on student_code = code where birthdate=? order by code";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setDate(1, new java.sql.Date(birthDate.getTime()));
		ResultSet result = statement.executeQuery();
		Student prevStudent = null;
		while (result.next()) {
			if (prevStudent == null || result.getInt("code") != prevStudent.getCode()) {
				long secs;

				Student student = new Student();
				student.setCode(result.getInt("code"));
				student.setFirstName(result.getString("firstname"));
				student.setLastName(result.getString("lastname"));
				secs = result.getDate("birthdate").getTime();
				birthDate = new java.util.Date(secs);
				student.setBirthDate(birthDate);
				prevStudent = student;

				students.add(student);
			}

			if (result.getString("course") != null) {
				Exam e = new Exam();
				e.setCourse(result.getString("course"));
				e.setDate(new java.util.Date(result.getDate("date").getTime()));
				e.setScore(result.getInt("code"));
				prevStudent.addExam(e);
			}
		}

		// release resources
		result.close();
		statement.close();
		connection.close();

		return students;
	}
}
