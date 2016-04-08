package com.wjwl.mobile.taocz.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DatabaseHepler extends SQLiteOpenHelper {
	private static String DataBase="LogCatTabel";

	public DatabaseHepler(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, null, 1);
		// TODO Auto-generated constructor stub
	}
	public DatabaseHepler(Context context, String name){
		super(context, name, null, 1);
	}
	public DatabaseHepler(Context context){
		super(context,DataBase,null,1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table logcat(id integer not null primary key autoincrement,contents varchar,styles varchar)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}

