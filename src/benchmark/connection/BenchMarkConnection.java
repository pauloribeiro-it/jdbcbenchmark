package benchmark.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class BenchMarkConnection {
	private static final Properties properties = new Properties();

	static {
		try {
			properties.load(BenchMarkConnection.class.getResourceAsStream("connection.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private BenchMarkConnection() {

	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(properties.getProperty(ConnectionParametersEnum.URL.getDescription()), properties);
	}

	public static void closeConnection(Connection con) throws SQLException {
		if (con != null)
			con.close();
	}
	public static Properties getProperties() {
		return properties;
	}
}
