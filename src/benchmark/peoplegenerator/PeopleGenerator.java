package benchmark.peoplegenerator;

import java.util.List;

import benchmark.entities.Person;

public interface PeopleGenerator {
	String generatePeopleInsert(Integer quantity);
	List<String> generatePeopleInsertAsList(Integer quantity);
	List<Person> generatePeopleAsObject(Integer quantity);
}
