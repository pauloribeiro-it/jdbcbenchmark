package benchmark.rowset;

import java.sql.SQLException;

public interface RowSet {
	void updatePeople() throws SQLException;
	void insertPeople(int quantity) throws SQLException;
}
