package com.example.goldfoxchina.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBOpenHelper extends SQLiteOpenHelper {

	private Context context;
	private static DBOpenHelper dbOpenHelper = null;

	// 构造方法
	private DBOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		this.context = context;
	}

	public static DBOpenHelper getDBOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		if (dbOpenHelper == null) {
			dbOpenHelper = new DBOpenHelper(context, name, factory, version);
		}
		return dbOpenHelper;
	}

	// 创建
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table if not exists  Message (_id integer primary key autoincrement, "
				+ "name varchar(30) not null, telnum varchar(15) not null, "
				+ "zipcode varchar(10),"
				+ "area varchar(60),"
				+ "street varchar(100));");
//		Toast.makeText(context, "创建成功", Toast.LENGTH_SHORT).show();
	}

	// 数据库更新
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
