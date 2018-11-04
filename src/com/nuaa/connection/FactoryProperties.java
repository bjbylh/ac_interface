package com.nuaa.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.nuaa.accesscontrol.DBConnectionInfo;
import com.nuaa.accesscontrol.DataBaseConfig;

public class FactoryProperties {

	//c3p0 config properties
	
	public static String DRIVER_CLASS="com.mysql.cj.jdbc.Driver";
	
	public static String InitialPoolSize="";
	
	public static String MinPoolSize="2";
	
	public static String MaxPoolSize="5";
	
	//<!-- 璁惧畾鏁版嵁搴撹繛鎺ヨ秴鏃舵椂闂达紝浠ョ涓哄崟浣嶃�傚鏋滆繛鎺ユ睜涓煇涓暟鎹簱杩炴帴澶勪簬绌洪棽鐘舵�佷笖瓒呰繃timeout绉掓椂锛屽氨浼氫粠杩炴帴姹犱腑绉婚櫎-->
	public static String checkTimeout="30000";
	
	public static String maxIdleTime="60";
	
	//<!-- 鏈�澶х殑PreparedStatement鐨勬暟閲� -->
	public static String Max_statements="50";
	
	//<!-- 璁剧疆鏁版嵁搴� -->
	public static String Idle_test_period="60000";
	
	//<!-- 褰撹繛鎺ユ睜閲岄潰鐨勮繛鎺ョ敤瀹岀殑鏃跺�欙紝C3P0涓�涓嬭幏鍙栫殑鏂扮殑杩炴帴鏁� -->
	public static String Acquire_increment="2";
	
	//<!-- 姣忔閮介獙璇佽繛鎺ユ槸鍚﹀彲鐢� -->
	public static Boolean Validate=true;
	
	public static void main(String[] args) throws Exception {
		String s = "vela.routeconfig";
		DBConnectionInfo db = new DBConnectionInfo(s);
		DataBaseConfig dataBaseConfig = db.GetDataBaseConfig();

		for (int i = 0; i < 1000; i++) {
			Connection c = db.getConnection();
			Statement stm = c.createStatement();
			ResultSet result = stm.executeQuery("select configstr from ac_client where client_id='" + s + "'");
			while (result.next()) {
				if (result.getString("configstr") != null) {
					String retstr = result.getString("configstr");
					System.out.println(retstr);
				}
			}
			result.close();
			stm.close();
			c.close();
		}
	}
}
