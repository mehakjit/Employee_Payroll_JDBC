package com.capg;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

public class Mysql {
	public static String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
	public static String userName = "root";
	public static String password = System.getenv("SQL_PASSWORD");
	
	public static void main(String[] args) {
			Connection con;
			System.out.println(password);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("Cannot find the driver in the classpath!", e);
		}
		listDrivers();
		try {
			System.out.println("Connecting to database:"+jdbcURL);
			con=DriverManager.getConnection(jdbcURL,userName,password);
			System.out.println("Connection is success"+con);
		}catch (Exception e) {
			e.printStackTrace();
		}
		readData();
	}

	private static void listDrivers() {
		Enumeration<Driver>driverList = DriverManager.getDrivers();
		while(driverList.hasMoreElements())
		{
			Driver driverClass = (Driver)driverList.nextElement();
			System.out.println(" "+driverClass.getClass().getName());
		}
		
	}
	
	public static void readData() {
		String sql = "Select * from employee_payroll";
		try {
			Connection  con =DriverManager.getConnection(jdbcURL,userName,password); ;
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while(result.next()) {
				System.out.print(result.getInt("id")+" ");
				System.out.print(result.getString("name")+" ");
				System.out.print(result.getDate("start").toLocalDate()+" ");
				System.out.println();
			}
			con.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}	
	}
}

