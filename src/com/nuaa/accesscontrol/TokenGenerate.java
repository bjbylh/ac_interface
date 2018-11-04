package com.nuaa.accesscontrol;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nuaa.desutil.DesUtils;


public class TokenGenerate {
	
	
	private static String userID;
	private static String clientID;
	private static String uploadtime;
	private static String deskey="testkey";
	
	private static String getTime()
	{
		String time="";
		try  
		{ 
			Date date=new Date();
			DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			time=format.format(date);
		}
		catch (Exception e)   
		{   
			 e.printStackTrace();
		} 
		return time;
	}
	
	public static String generate(String client_id,String user_id) 
	{
		String reString="";
		String desString="";
		reString=client_id+"%::%"+user_id+"%::%"+getTime();
		try {
		      DesUtils des = new DesUtils(deskey);//é‘·î„ç•¾æ¶”å¤Šç˜‘é–½ï¿½
		      desString=des.encrypt(reString);
		      System.out.println("Ô­Âë:" + reString);
		      System.out.println("ÃÜÂë:" + des.encrypt(reString));
		}
		catch (Exception e) {
		     e.printStackTrace();
		}
		return desString;
	}
	
	private static void ConvertToken(String resToken)
	{
		try {
			DesUtils des = new DesUtils(deskey);
			String Token=des.decrypt(resToken);
			System.out.println("Token:"+Token);
			clientID=Token.split("%::%")[0];
			userID=Token.split("%::%")[1];
			uploadtime=Token.split("%::%")[2];
			System.out.println("ac_getclientid:"+clientID);
			System.out.println("ac_getuserid:"+userID);
		}
		catch (Exception e) {
		     e.printStackTrace();
		}

	}
	
	public static Boolean isTokenValid(String Token)
	{
		Boolean Tag=false;
		try
		{
			ConvertToken(Token);
			Tag=true;
		}
		catch (Exception e) {
		     e.printStackTrace();
		}
		System.out.println("Token valid:"+Tag);
		return Tag;
	}
	
	public static Boolean isTokenTimeValid(String Token)
	{
		ConvertToken(Token);
		Boolean Tag=false;
		String systime=getSysTime();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
		try  
		{   
		    Date d1 = df.parse(systime);   
		    Date d2 = df.parse(uploadtime);   
		    long diff = d1.getTime() - d2.getTime();   
		    long days = diff / (1000 * 60 * 60 * 24); 
		    System.out.println("Time period:"+String.valueOf(diff));
		    if (diff>20000) Tag=false; else Tag=true;
		}   
		catch (Exception e)   
		{   
			e.printStackTrace();
			System.out.println("ERROR:Token Time invalid");
		} 
		System.out.println("Time valid:"+Tag);
		return Tag;
	}
	
	public static Boolean isTokenClientValid(String Token,String Client_id)
	{
		ConvertToken(Token);
		Boolean Tag=false;
		System.out.println("Client put id:"+Client_id);
		System.out.println("Token put id:"+clientID);
		if (Client_id.equals(clientID)) Tag=true;
		System.out.println("Client valid:"+Tag);
		return Tag;
	}
	
	public static String getTokenUserID(String Token)
	{
		ConvertToken(Token);
		return userID;
	}
	
	private static String getSysTime()
	{
		String time="";
		try  
		{ 
			Date date=new Date();
			DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			time=format.format(date);
		}
		catch (Exception e)   
		{   
			e.printStackTrace();
			System.out.println("ERROR:getSysTime");
		} 
		return time;
	}
	
	public static void main(String[] args) {
		generate("aa123", "user");
	}
		
	
}
