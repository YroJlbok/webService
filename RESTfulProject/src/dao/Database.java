package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import utils.GlobalValues;

public class Database {

	public Connection Get_Connection() throws Exception {
		System.out.println(this.getClass().getName() + " " + "Get_Connection");
		try {
			System.out.println(" before " + "Get_Connection");
			//String connectionURL = "jdbc:mysql://localhost:3306/MainGames";// "jdbc:mysql://localhost:3306/RESTful_IOS_Android";
			String connectionURL = "jdbc:mysql://192.168.2.88:3306/tomcatdb";
			// String connectionURL =
			// "jdbc:mysql://localhost:3306/workingbrain";
			Connection connection = null;

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			if (GlobalValues.PPRODUCTION) {
				connection = DriverManager.getConnection(connectionURL, "Igor",
						"");
			} else {
				connection = DriverManager.getConnection(connectionURL, "Igor",
						"");
			}
			if (connection == null) {
				System.out.println("connection==null");

			} else {

				System.out.println("connection is not null");

			}
			return connection;
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

}
