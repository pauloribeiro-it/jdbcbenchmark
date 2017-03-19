package benchmark.nativejdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import benchmark.connection.BenchMarkConnection;
import benchmark.entities.Person;
import benchmark.peoplegenerator.PeopleGenerator;
import benchmark.peoplegenerator.PeopleGeneratorImpl;

public class JDBCPeopleGeneratorImpl implements JDBCPeopleGenerator {
	private static final int BATCH_DIVISION = 10;
	private static final Integer MILLIS_TIME = 1000;

	public void insertPeople(int quantity, boolean useJBCBatch) throws SQLException {
		try (Connection con = BenchMarkConnection.getConnection(); Statement st = con.createStatement()) {
			PeopleGenerator gen = new PeopleGeneratorImpl();
			List<String> inserts = gen.generatePeopleInsertAsList(quantity);

			System.out.println("Use batch? " + useJBCBatch);
			System.out.println("Start...");
			Long start = System.currentTimeMillis();
			if (useJBCBatch) {
				con.setAutoCommit(false);
				for (int i = 0; i < inserts.size(); i++) {
					String insert = inserts.get(i);
					st.addBatch(insert);
					if (i % BATCH_DIVISION == 0 && i > 0) {
						st.executeBatch();
					}
				}
				st.executeBatch();
				con.commit();
			} else {
				for (String insert : inserts) {
					st.execute(insert);
				}
			}
			Long totalTime = System.currentTimeMillis() - start;
			System.out.println("Total time: " + (totalTime / MILLIS_TIME) + " seconds.");
		}
	}

	public void updatePeople(boolean useJBCBatch) throws SQLException {
		List<Person> people = retrieveAllPeople();
		try (Connection con = BenchMarkConnection.getConnection();
				Statement st = con.createStatement()) {

			System.out.println("Use batch? " + useJBCBatch);
			System.out.println("Start...");
			Long start = System.currentTimeMillis();
			if (useJBCBatch) {
				con.setAutoCommit(false);
				for (int i = 0; i < people.size(); i++) {
					Person p = people.get(i);
					String updateQuery = "update person set name = '"+"updated"+"' where id = "+p.getId();
					st.addBatch(updateQuery);
					if (i > 0 && i % BATCH_DIVISION == 0) {
						st.executeBatch();
					}
				}
				st.executeBatch();
				con.commit();
			} else {
				for (Person p : people) {
					String updateQuery = "update person set name = '"+(p.getName()+" updated")+"' where id = "+p.getId();
					st.executeUpdate(updateQuery);
				}
			}
			Long totalTime = System.currentTimeMillis() - start;
			System.out.println("Total time: " + (totalTime / MILLIS_TIME));
//			if (st != null)
//				st.close();
		}
	}

	private List<Person> retrieveAllPeople() throws SQLException {
		List<Person> people = new ArrayList<>();
		String query = "select * from person";
		try (Connection con = BenchMarkConnection.getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query)) {
			while (rs.next()) {
				people.add(new Person(rs.getInt("id"), rs.getString("name"), rs.getInt("age")));
			}
		}
		return people;
	}
}
