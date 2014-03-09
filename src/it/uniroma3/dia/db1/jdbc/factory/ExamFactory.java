package it.uniroma3.dia.db1.jdbc.factory;

import it.uniroma3.dia.db1.jdbc.model.Exam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.googlecode.jeneratedata.core.Generator;
import com.googlecode.jeneratedata.dates.DateGenerator;
import com.googlecode.jeneratedata.numbers.IntegerGenerator;

public class ExamFactory {

	private static SimpleDateFormat dateFromat = new SimpleDateFormat("yyyy/MM/dd");
	private static ExamFactory instance;

	private static final String[] words = new String[] { "Math", "OOP", "SIW", "Fisics",
			"Fisics 2", "Logica", "Storia", "APS" };
	private Generator<Date> dateGenerator;
	private Generator<Integer> scoreGenerator;
	private Random random;

	public static synchronized ExamFactory getInstance() {
		if (instance == null)
			instance = new ExamFactory();
		return instance;
	}

	private ExamFactory() {
		try {
			this.dateGenerator = new DateGenerator(dateFromat.parse("2001/01/01"),
					dateFromat.parse("2014/01/01"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.scoreGenerator = new IntegerGenerator(13);
		this.random = new Random();
	}

	public Exam getRandomExam() {
		Exam res = new Exam();
		res.setCourse(this.getCourse());
		res.setScore(this.scoreGenerator.generate() + 18);
		res.setDate(this.dateGenerator.generate());
		return res;
	}

	public List<Exam> getExams(int n) {
		List<Exam> res = new LinkedList<Exam>();
		for (int i = 0; i < n; i++) {
			res.add(this.getRandomExam());
		}
		return res;
	}

	private String getCourse() {
		return words[this.random.nextInt(words.length)];
	}

}
