package com.nuaa.accesscontrol;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.nuaa.accesscontrol.EnumDefine.AccountValidationResultsEnum;
import com.nuaa.accesscontrol.EnumDefine.EditPasswordResultEnum;
import com.nuaa.accesscontrol.EnumDefine.TokenValidationResultsEnum;
import com.nuaa.connection.ConstantProperties;
import com.nuaa.desutil.DesKey;
import com.nuaa.desutil.DesUtils;

public class AccessControl implements IAccessControl {
	
	private boolean isSecureAccess=false;
	private String calluserid;
	private String callclientid;
	
	@Override
	public String GetAccessToken(String ClientBID, String UserID) {
		return TokenGenerate.generate(ClientBID, UserID);
	}
	
	@Override
	public TokenValidationResultsEnum IsValidAccess(String Token, String Client_id) {
		//Token = Token.split(":")[1];
		try{
			if (!TokenGenerate.isTokenValid(Token)) return TokenValidationResultsEnum.ErrorTokenCode;
			if (!TokenGenerate.isTokenTimeValid(Token)) return TokenValidationResultsEnum.ErrorTokenTimeout;
			if (!TokenGenerate.isTokenClientValid(Token, Client_id)) return TokenValidationResultsEnum.ErrorClientID;
			callclientid=Client_id;
			calluserid=TokenGenerate.getTokenUserID(Token);
			isSecureAccess=true;
		}catch (Exception e) {
			e.printStackTrace();
			return TokenValidationResultsEnum.ErrorDBTimeout;
			// TODO: handle exception
		}
		return TokenValidationResultsEnum.Success;
	}

	@Override
	public AccountValidationResultsEnum IsValidAccess(String UserID, String Password, String Client_id) {
		try{
			Connection con = null; 
	        Statement stmt = null;  
	        ResultSet result = null;
	        Class.forName(ConstantProperties.DBDRIVER);
//	        con = DriverManager.getConnection(ConstantProperties.DBURL,
//	        		                          ConstantProperties.DBUSER,
//	        		                          ConstantProperties.DBPASS);
	        con=GetConnection.getConnection();
	        stmt = con.createStatement(); 
	        DesUtils des = new DesUtils(DesKey.deskey);//�Զ�����    
	        result = stmt.executeQuery("select * from ac_user where username='"+des.encrypt(UserID)+"'"); 
			if (!result.next()) return AccountValidationResultsEnum.ErrorUserID;
			String desusername=result.getString("username");
			String despassword=result.getString("password");
			int loginnum=result.getInt("login_num");
			if (loginnum>=5){
				return AccountValidationResultsEnum.ErrorUserIDLocked;
			}
			DesUtils des2= new DesUtils(UserID);
			if (!des2.decrypt(despassword).equals(Password)){
				stmt.execute("update ac_user set login_num=login_num+1 where username='"+des.encrypt(UserID)+"'");
				return AccountValidationResultsEnum.ErrorPassword;
			}
			stmt.execute("update ac_user set login_num=0 where username='"+des.encrypt(UserID)+"'");
			calluserid=UserID;
			callclientid=Client_id;
			isSecureAccess=true;
	        result.close();  
	        con.close();
		}catch (Exception e) {
			e.printStackTrace();
			return AccountValidationResultsEnum.ErrorDBTimeout;
			// TODO: handle exception
		}
		return AccountValidationResultsEnum.Success;
	}

	@Override
	public List<String> GetPermissonList(String Satellite_id) {
		List<String> list=new ArrayList<>();
		if (isSecureAccess==false) return list;
		try{
			Connection con = null; 
	        Statement stmt = null;  
	        ResultSet result = null; 
			Class.forName(ConstantProperties.DBDRIVER);
//	        con = DriverManager.getConnection(ConstantProperties.DBURL,
//	        		                          ConstantProperties.DBUSER,
//	        		                          ConstantProperties.DBPASS);
			con=GetConnection.getConnection();
	        DesUtils des = new DesUtils(DesKey.deskey);//�Զ�����    
	        String sql="select function_id from(select "+
					   "function_id from(select function_id,satellite_id from ac_func_role inner join"+
					   " (select * from ac_user_role where username='"+des.encrypt(calluserid)+"') tmptable"+
					   " on ac_func_role.role_id=tmptable.role_id) tmptable2 "+
					   "where tmptable2.satellite_id='"+Satellite_id+"') tmptable3 "+
					   "where function_id in (select function_id from ac_cli_func where client_id='"+callclientid+"')";
	        stmt = con.createStatement();
	        result = stmt.executeQuery(sql);
	        while (result.next()){  
	            String id = result.getString("function_id");
	            list.add(id);
	            System.out.println("Get Function:"+id);  
	        }  
	        result.close();  
	        con.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public EditPasswordResultEnum EditPassword(String UserID, String OLD_Password, String NEW_Password) {
		try{
			Connection con = null; 
	        Statement stmt = null;  
	        ResultSet result = null; 
			Class.forName(ConstantProperties.DBDRIVER);
//	        con = DriverManager.getConnection(ConstantProperties.DBURL,
//	        		                          ConstantProperties.DBUSER,
//	        		                          ConstantProperties.DBPASS);
			con=GetConnection.getConnection();
	        DesUtils des = new DesUtils(DesKey.deskey);
	        stmt = con.createStatement();
	        result = stmt.executeQuery("select password from ac_user where username='"+des.encrypt(UserID)+"'");
	        if (!result.next()) return EditPasswordResultEnum.ErrorUserID;
	        DesUtils des2= new DesUtils(UserID);
	        if (!des2.decrypt(result.getString("password")).equals(OLD_Password)) return EditPasswordResultEnum.ErrorPassword;
	        stmt.execute("update ac_user set password='"+des2.encrypt(NEW_Password)+"' where username='"+des.encrypt(UserID)+"'");
	        result.close();  
	        con.close();
		}catch (Exception e) {
			e.printStackTrace();
			return EditPasswordResultEnum.ErrorDBTimeout;
		}
		return EditPasswordResultEnum.Success;
	}
	
	@Override
	public String GetCurrentUserID() {
		if (!isSecureAccess)
			return "";
		else
			return calluserid;
	}
	
	public static void main(String[] args) {
		AccessControl accessControl=new AccessControl();
//		String token=accessControl.GetAccessToken("vela.authmngr", "test");
//		System.out.println(token);
		//System.out.println(accessControl.IsValidAccess(token, "client01"));
		System.out.println(accessControl.IsValidAccess("test2", "1234", "vela.ooc"));
		List list=accessControl.GetPermissonList("APSTAR09");
		for (Iterator<String> iter=list.iterator();iter.hasNext();)
		{
			System.out.println(iter.next());
		}
		System.out.println(accessControl.GetCurrentUserID());
		
		
//		AccessControl accessControl=new AccessControl();
//		System.out.println(accessControl.EditPassword("test123", "12345", "1234"));
	}

}
