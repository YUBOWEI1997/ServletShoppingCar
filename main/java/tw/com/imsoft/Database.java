package tw.com.imsoft;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
	public static Connection getConnection() throws Exception {
		Class.forName("oracle.jdbc.OracleDriver");
		return DriverManager.getConnection("jdbc:oracle:thin:@//61.216.84.217:1534/ORCL", "demo", "123456");
	}
}