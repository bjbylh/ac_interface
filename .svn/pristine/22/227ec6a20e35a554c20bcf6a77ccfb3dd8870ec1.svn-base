package com.nuaa.accesscontrol;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.nuaa.connection.ConstantProperties;

public class GetConfig {

    
	public static String GetConfigString(String client_id) throws Exception{
		String retstr="";
		Connection con = null; 
        Statement stmt = null;  
        ResultSet result = null; 
		Class.forName(ConstantProperties.DBDRIVER);
        con = DriverManager.getConnection(ConstantProperties.DBURL,
        		                          ConstantProperties.DBUSER,
        		                          ConstantProperties.DBPASS);  
        stmt = con.createStatement(); 
        result = stmt.executeQuery("select configstr from ac_client where client_id='"+client_id+"'"); //ִ��SQL ��䣬��ѯ���ݿ�  
        while (result.next()){  
           if (result.getString("configstr")!=null)
        	   retstr=result.getString("configstr");  
        }  
        result.close();  
        con.close();
        return retstr;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(GetConfig.GetConfigString("client03"));
	}
	
}
