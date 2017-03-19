package benchmark.peoplegenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import benchmark.entities.Person;

public class PeopleGeneratorImpl implements PeopleGenerator {
	private static final int SEED_AGE = 100;
	private Random random = new Random();

	public String generatePeopleInsert(Integer quantity) {
		StringBuilder inserts = new StringBuilder();

		for (int i = 0; i < quantity; i++) {
			inserts.append("insert into person(name,age,id) values('person " + (i + 1) + "'," + generateAge() + ","
					+ (i + 1) + ");\n");
		}
		return inserts.toString();
	}

	public List<String> generatePeopleInsertAsList(Integer quantity) {
		return Arrays.asList(generatePeopleInsert(quantity).split("\n"));
	}

	public List<Person> generatePeopleAsObject(Integer quantity) {
		List<Person> person = new ArrayList<>();
		Random random = new Random();
		for (int i = 0; i < quantity; i++) {
			int age = random.nextInt(SEED_AGE);
			if (age == 0) {
				age = 1;
			}
			person.add(new Person(i + 1, "Person " + (i + 1), generateAge()));
		}
		return person;
	}

	private int generateAge() {
		int age = random.nextInt(SEED_AGE);
		if (age == 0) {
			age = 1;
		}
		return age;
	}
}
