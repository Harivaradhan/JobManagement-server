package com.tap.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
public static Connection getConnection() {
	Connection con=null;
	try {

    	Class.forName("com.mysql.cj.jdbc.Driver");
		 con = DriverManager.getConnection("mysql://root:NArJySUIOdQMLfoAFHyXiSXMhnKRyNXc@shinkansen.proxy.rlwy.net:57936/railway", "root", "NArJySUIOdQMLfoAFHyXiSXMhnKRyNXc");

	} catch (SQLException e) {
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
  return con;
}
}
