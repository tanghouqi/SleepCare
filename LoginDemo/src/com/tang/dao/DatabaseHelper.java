package com.tang.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	// �汾,���ݿ���
	private static final String DATABASE_NAME = "test_project.db";
	private static final int VERSION = 1;
	//����ͨ��super���ø��൱�еĹ��캯�� 
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table userInfo "+"(username varchar,password varchar,age integer,sex varchar,phone integer)");
		
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("ALTER TABLE userInfo ADD COLUMN other STRING");
		
	}

}
