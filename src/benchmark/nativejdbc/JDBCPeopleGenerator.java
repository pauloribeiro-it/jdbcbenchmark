package benchmark.nativejdbc;

import java.sql.SQLException;

public interface JDBCPeopleGenerator {
	void updatePeople(boolean useJBCBatch) throws SQLException;
	void insertPeople(int quantity, boolean useJBCBatch) throws SQLException;
}
