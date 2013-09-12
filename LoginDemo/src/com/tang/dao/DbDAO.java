package com.tang.dao;

import java.util.List;

import com.tang.bean.UserInfo;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.webkit.WebChromeClient.CustomViewCallback;

public class DbDAO {
	private DatabaseHelper dHelper;
	private SQLiteDatabase db;
	
	public DbDAO(Context context){
		dHelper = new DatabaseHelper(context);
		
	}
	
	//×¢²á
	public boolean regsit(UserInfo userinfo){
		db = dHelper.getWritableDatabase();
		String sql = "insert into userInfo values(?,?,?,?,?)";
		db.execSQL(sql, new Object[]{userinfo.getUsername(),
				userinfo.getPassword(),userinfo.getAge(),userinfo.getSex(),userinfo.getPhone()});
		return true;
	}
	
	
	//µÇÂ¼
	public boolean login(String username,String password){
		db = dHelper.getReadableDatabase();
		String sql = "select * from userInfo where username=? and password=?";
		try {
			Cursor cs = db.rawQuery(sql, new String[]{username,password});
			if(cs.moveToFirst() == true){
				cs.close();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	//ÊÍ·ÅÊý¾Ý¿â 
	public void close(){
		db.close();
	}
}
