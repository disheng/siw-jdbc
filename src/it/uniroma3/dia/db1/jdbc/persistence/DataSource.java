package it.uniroma3.dia.db1.jdbc.persistence;

import java.sql.*;

public class DataSource {
	private String dbURI = "jdbc:postgresql://localhost/university";
	private String user = "postgres";
	private String password = "postgres";

	public Connection getConnection()  {
		Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(dbURI,user, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
}
