package it.uniroma3.dia.db1.jdbc.factory;

import it.uniroma3.dia.db1.jdbc.model.Student;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.googlecode.jeneratedata.core.Generator;
import com.googlecode.jeneratedata.dates.DateGenerator;
import com.googlecode.jeneratedata.numbers.IntegerGenerator;
import com.googlecode.jeneratedata.people.FemaleNameGenerator;
import com.googlecode.jeneratedata.people.LastNameGenerator;
import com.googlecode.jeneratedata.people.MaleNameGenerator;

public class StudentFactory {

	private static SimpleDateFormat dateFromat = new SimpleDateFormat("yyyy/MM/dd");
	private static StudentFactory instance;

	private Generator<String> maleNameGenerator;
	private Generator<String> femaleNameGenerator;
	private Generator<String> lastNameGenerator;
	private Generator<Date> dateGenerator;
	private Generator<Integer> idGenerator;
	private Random random;
	private ExamFactory examsGenerator;

	public static synchronized StudentFactory getInstance() {
		if (instance == null)
			instance = new StudentFactory();
		return instance;
	}

	private StudentFactory() {
		this.examsGenerator = ExamFactory.getInstance();
		this.maleNameGenerator = new MaleNameGenerator();
		this.femaleNameGenerator = new FemaleNameGenerator();
		this.lastNameGenerator = new LastNameGenerator();
		try {
			this.dateGenerator = new DateGenerator(dateFromat.parse("1970/01/01"),
					dateFromat.parse("2000/01/01"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.idGenerator = new IntegerGenerator();
		this.random = new Random();
	}

	public Student getRandomStudent() {
		Student res = new Student();
		res.setCode(this.idGenerator.generate());
		res.setBirthDate(this.dateGenerator.generate());
		res.setFirstName(this.getName());
		res.setLastName(this.lastNameGenerator.generate());
		res.setExams(this.examsGenerator.getExams(this.random.nextInt(10)));
		return res;
	}

	public List<Student> getRandomStudents(int number) {
		List<Student> students = new LinkedList<Student>();
		for (int i = 0; i < number; i++)
			students.add(this.getRandomStudent());
		return students;
	}

	private String getName() {
		if (Math.random() > 0.5) {
			return this.maleNameGenerator.generate();
		} else {
			return this.femaleNameGenerator.generate();
		}
	}
}
