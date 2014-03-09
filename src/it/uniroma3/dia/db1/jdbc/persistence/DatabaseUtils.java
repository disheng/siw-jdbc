package it.uniroma3.dia.db1.jdbc.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseUtils {

	public static void createTables() throws SQLException{
		Connection connection = new DataSource().getConnection();
		
		String query = "CREATE TABLE students(code integer NOT NULL," +
				"firstname character varying(64) NOT NULL," +
				"lastname character varying(64) NOT NULL," +
				"birthdate date NOT NULL,CONSTRAINT pk_students PRIMARY KEY (code))";

		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		 
		query = "CREATE TABLE exams ( student_code integer, score integer, course text,date date, " +
				"CONSTRAINT exams_student_code_fkey FOREIGN KEY (student_code) REFERENCES students (code) " +
				"MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION )";
		statement = connection.createStatement();
		statement.executeUpdate(query);
		
		// release resources
		statement.close();
		connection.close();
	}
}
