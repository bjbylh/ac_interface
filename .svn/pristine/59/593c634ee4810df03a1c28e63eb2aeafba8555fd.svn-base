package com.nuaa.accesscontrol;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.nuaa.connection.DynamicProperties;

public class GetConnection {

	public static Connection getConnection()
	{
		try {
			DynamicProperties dynamicProperties=new DynamicProperties();
			dynamicProperties.getProperties();
			return DriverManager.getConnection(dynamicProperties.DBURL,
											   dynamicProperties.DBUSER,
											   dynamicProperties.DBPASS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}