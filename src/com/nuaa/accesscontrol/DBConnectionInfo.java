package com.nuaa.accesscontrol;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.nuaa.connection.DynamicProperties;
import com.nuaa.connection.FactoryProperties;
import com.nuaa.desutil.DesKey;
import com.nuaa.desutil.DesUtils;

public class DBConnectionInfo {
	private DataBaseConfig dataBaseConfig = new DataBaseConfig();
	private ComboPooledDataSource cpds;
	private String[] rst = null;
	private boolean isInit = false;
	private DynamicProperties dp;

	
	
	public DBConnectionInfo(String client_id) throws Exception {
		String retstr = "";
		Connection con = null;
		Statement stmt = null;
		ResultSet result = null;
		dp = new DynamicProperties();
		dp.getProperties();
		try {
			Class.forName(dp.DBDRIVER);
			con = DriverManager.getConnection(dp.DBURL, dp.DBUSER, dp.DBPASS);
			stmt = con.createStatement();
			result = stmt.executeQuery("select configstr from ac_client where client_id='" + client_id + "'");
			while (result.next()) {
				if (result.getString("configstr") != null)
					retstr = result.getString("configstr");
			}
			result.close();
			con.close();
		} catch (Exception e) {
			//new Exception("Connecting DB Error");
			System.out.println(e.getMessage());
		}

		if (retstr == null || retstr.equals(""))
			return;

		DesUtils desUtils = new DesUtils(DesKey.deskey);
		String configstr = desUtils.decrypt(retstr);
		rst = configstr.split("==");

		dataBaseConfig.setIp(rst[0]);
		dataBaseConfig.setSpace(rst[1]);
		dataBaseConfig.setUser(rst[2]);
		dataBaseConfig.setPsd(rst[3]);
		// for pool
	}

	public DataBaseConfig GetDataBaseConfig() throws Exception {
		return dataBaseConfig;
	}

	public Connection getConnection() throws SQLException {
		if(!isInit){
			cpds = new ComboPooledDataSource();
			try {
				cpds.setDriverClass(FactoryProperties.DRIVER_CLASS);
			} catch (PropertyVetoException e) {
				e.printStackTrace();
				return null;
			}
			if(!dp.DBURL.contains("?"))
				cpds.setJdbcUrl("jdbc:mysql://" + rst[0] + ":3306/" + rst[1]);
			else{
				int pos = dp.DBURL.indexOf("?");
				String tail = dp.DBURL.substring(pos);
				cpds.setJdbcUrl("jdbc:mysql://" + rst[0] + ":3306/" + rst[1] + tail);
			}
			cpds.setUser(rst[2]);
			cpds.setPassword(rst[3]);
			cpds.setMinPoolSize(Integer.parseInt(FactoryProperties.MinPoolSize));
			cpds.setMaxPoolSize(Integer.parseInt(FactoryProperties.MaxPoolSize));
			cpds.setAcquireIncrement(Integer.parseInt(FactoryProperties.Acquire_increment));
			cpds.setIdleConnectionTestPeriod(Integer.parseInt(FactoryProperties.Idle_test_period));
			cpds.setCheckoutTimeout(Integer.parseInt(FactoryProperties.checkTimeout));
			cpds.setMaxStatements(Integer.parseInt(FactoryProperties.Max_statements));
			cpds.setTestConnectionOnCheckin(true);
			cpds.setTestConnectionOnCheckout(true);
			//add start
			cpds.setMaxIdleTime(Integer.parseInt(FactoryProperties.maxIdleTime));
			//add end
			isInit = true;
		}
		return cpds.getConnection();
	}
	
	public static void main(String[] args) throws Exception  {
		DBConnectionInfo d = new DBConnectionInfo("vela.flightdb");

		Connection con = null;
		Statement stmt = null;
		ResultSet result = null;
		String client_id = "vela.flightdb";
		while(true){
			Thread.sleep(500);
			try{
			con = d.getConnection();
			stmt = con.createStatement();
			result = stmt.executeQuery("select configstr from ac_client where client_id='" + client_id + "'");
			while (result.next()) {
				if (result.getString("configstr") != null)
					System.out.println(result.getString("configstr"));
			}
			
			result.close();
			stmt.close();
			con.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
