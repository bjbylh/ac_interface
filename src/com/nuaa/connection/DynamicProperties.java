package com.nuaa.connection;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.nuaa.desutil.DesKey;
import com.nuaa.desutil.DesUtils;

public class DynamicProperties {
	
	
    public String DBDRIVER = "com.mysql.cj.jdbc.Driver";  
    
    public String DBURL = "";  
    
    public String DBUSER = "";  
    
    public String DBPASS = "";
    
    public void getProperties(){
    	try {
    		InputStream in = this.getClass().getClassLoader().getResourceAsStream("db.cfg");
            InputStreamReader read = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            DBURL = bufferedReader.readLine();
            DBUSER = bufferedReader.readLine();
            DBPASS = bufferedReader.readLine();
            read.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void getDesProperties(){
    	try{
    		InputStream in = this.getClass().getClassLoader().getResourceAsStream("codedb.cfg");
            InputStreamReader read = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            DesUtils desUtils=new DesUtils(DesKey.deskey);
            DBURL = desUtils.decrypt(bufferedReader.readLine());
            DBUSER = desUtils.decrypt(bufferedReader.readLine());
            DBPASS = desUtils.decrypt(bufferedReader.readLine());
            read.close();
    	}catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static void main(String[] args) {
		DynamicProperties dynamicProperties=new DynamicProperties();
		dynamicProperties.getProperties();
		System.out.println(dynamicProperties.DBURL);
		System.out.println(dynamicProperties.DBUSER);
		System.out.println(dynamicProperties.DBPASS);
		
	}
    
}
