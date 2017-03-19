package benchmark.rowset;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

import benchmark.connection.BenchMarkConnection;
import benchmark.entities.Person;
import benchmark.peoplegenerator.PeopleGenerator;
import benchmark.peoplegenerator.PeopleGeneratorImpl;

public class RowSetImpl implements RowSet {
	private static final String RETRIEVE_PEOPLE = "select * from person";
	private static final Integer MILLIS_TIME = 1000;
	private PeopleGenerator peopleGenerator = new PeopleGeneratorImpl();
	
	public void updatePeople() throws SQLException {
		try(Connection con = BenchMarkConnection.getConnection();
			CachedRowSet rowSet = RowSetProvider.newFactory().createCachedRowSet()){
			con.setAutoCommit(false);
			rowSet.setCommand(RETRIEVE_PEOPLE);
			rowSet.execute(con);
			Long start = System.currentTimeMillis();
			while (rowSet.next()) {
				rowSet.updateString("name", rowSet.getString("name")+" is even");
				rowSet.updateRow();
			}
			rowSet.acceptChanges(con);
			Long totalTime = (System.currentTimeMillis() - start);
			System.out.println("Total time is: " + (totalTime / MILLIS_TIME));
		}
	}

	public void insertPeople(int quantity) throws SQLException {
		List<Person> people = peopleGenerator.generatePeopleAsObject(quantity);
		try(Connection con = BenchMarkConnection.getConnection();
				CachedRowSet rowSet = RowSetProvider.newFactory().createCachedRowSet()){
			con.setAutoCommit(false);
			rowSet.setCommand(RETRIEVE_PEOPLE);
			rowSet.execute(con);
			System.out.println("Start...");
			Long start = System.currentTimeMillis();
			for(int i = 0;i < people.size();i++){
				Person person = people.get(i);
				rowSet.moveToInsertRow();
				rowSet.updateInt("id", person.getId());
				rowSet.updateInt("age", person.getAge());
				rowSet.updateString("name", person.getName());
				rowSet.insertRow();
			}
			rowSet.moveToCurrentRow();
			rowSet.acceptChanges();
			Long totalTime = System.currentTimeMillis() - start;
			System.out.println("Total time: " + (totalTime / MILLIS_TIME) +" seconds.");
		}
	}
}
