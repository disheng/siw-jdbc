package it.uniroma3.dia.db1.jdbc;

import it.uniroma3.dia.db1.jdbc.factory.StudentFactory;
import it.uniroma3.dia.db1.jdbc.model.Student;
import it.uniroma3.dia.db1.jdbc.persistence.StudentRepository;

import java.util.List;

public class UniversityShell {
	
	public static void main(String[] args) throws Exception {
		deleteAllStudents(); 
		generateAndPrint(1);
	}
	
	public static void deleteAllStudents() throws Exception {
		StudentRepository sp = new StudentRepository();
		List<Student> students = sp.findAll();
		
 		for (Student sc : students) {
			sp.delete(sc);
		}
	}
	
	
	public static void generateAndPrint(int n) throws Exception{
		StudentRepository sp = new StudentRepository();
		List<Student> students = StudentFactory.getInstance().getRandomStudents(n);
		
		for(Student s: students){
			sp.persist(s);			
		}
		
 		for (Student sc : sp.findAll()) {
			System.out.println(sc);
		}
	}
}
