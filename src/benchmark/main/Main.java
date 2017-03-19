package benchmark.main;

import java.sql.SQLException;

import benchmark.nativejdbc.JDBCPeopleGenerator;
import benchmark.nativejdbc.JDBCPeopleGeneratorImpl;
import benchmark.rowset.RowSet;
import benchmark.rowset.RowSetImpl;

public class Main {
	private static final int QUANTITY = 10000;
	public static void main(String[] args) throws SQLException{
		JDBCPeopleGenerator jdbcGen = new JDBCPeopleGeneratorImpl();
		RowSet rowSet = new RowSetImpl();
//		rowSet.insertPeople(QUANTITY);
//		jdbcGen.insertPeople(QUANTITY, true);
//		jdbcGen.updatePeople(true);
		rowSet.updatePeople();
	}
}

//400 segundos sem batch
//37 segundos rowset
//405 segundos update jdbc
