package com.wjwl.mobile.taocz.DB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class WmDB extends SQLiteOpenHelper {
	public static final String tableName = "WmDB";
	public static final String BUSINESSID = "BUSINESSID";
	public static final String ITEMCOUNT = "ITEMCOUNT";
	public static final String ITEMID = "ITEMID";
	public static final int version = 1;

	public WmDB(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	public WmDB(Context context) {
		super(context, tableName, null, version);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS " + tableName + " ("
				+ BUSINESSID + " VARCHAR," + ITEMCOUNT + " VARCHAR," + ITEMID
				+ " VARCHAR)");
	}

	public void Insert(String businessid, String itemid, String itemcount) {
		SQLiteDatabase sql = this.getWritableDatabase();
		ContentValues tcv = new ContentValues();
		tcv.put(BUSINESSID, businessid);
		tcv.put(ITEMCOUNT, itemcount);
		tcv.put(ITEMID, itemid);
		sql.insert(tableName, null, tcv);
		sql.close();
	}

	public void Update(String itemid, String itemcount) {
		SQLiteDatabase sql = this.getWritableDatabase();
			sql.execSQL("UPDATE " + tableName + " set " + ITEMCOUNT + "='"
					+ itemcount + "'" + "where " + ITEMID + "='" + itemid
					+ "';");
			sql.close();
			Deletebycount();
	}

	public List<Map<String, String>> find(String selection) {
		SQLiteDatabase sql = this.getWritableDatabase();
		List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();
		Cursor mCursor = sql.query(tableName, null, selection, null, null,
				null, null);
		if (mCursor != null) {
			while (mCursor.moveToNext()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("ITEMID", mCursor.getString(2));
				map.put("ITEMCOUNT", mCursor.getString(1));
				listmap.add(map);
			}
			mCursor.close();
		}
		sql.close();
		return listmap;
	}
	
	public List<Map<String, String>> findallbusinessid() {
		SQLiteDatabase sql = this.getWritableDatabase();
		List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();
		Cursor mCursor = sql.query(tableName, new String[]{"BUSINESSID"} , null, null, BUSINESSID,
				null, null);
		if (mCursor != null) {
			while (mCursor.moveToNext()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("BUSINESSID", mCursor.getString(0));
				listmap.add(map);
			}
			mCursor.close();
		}
		sql.close();
		return listmap;
	}

	
	public int getTakeOutAllCount() {
		int result = 0;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(tableName, null, null, null, null, null, null);
		if (cursor!= null) {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {
				result = result
						+ cursor.getInt(cursor.getColumnIndex("ITEMCOUNT"));
			}
			cursor.close();
		}
		db.close();
		return result;
	}

	public void Deletebybusinessid(String id) {
		SQLiteDatabase sql = this.getWritableDatabase();
		sql.execSQL("DELETE FROM " + tableName + " WHERE " + BUSINESSID
				+ "='" + id + "';");
		sql.close();
	}

	public void Deletebycount() {
		SQLiteDatabase sql = this.getWritableDatabase();
		sql.execSQL("DELETE FROM " + tableName + " WHERE " + ITEMCOUNT
				+ "='0';");
		sql.close();
	}

	public void recreate() {
		SQLiteDatabase sql = this.getWritableDatabase();
		onCreate(sql);
		sql.close();
	}

	public void drop() {
		SQLiteDatabase sql = this.getWritableDatabase();
		sql.execSQL("DROP TABLE IF EXISTS " + tableName);
		sql.close();
	}

	public void deleteall() {
		SQLiteDatabase sql = this.getWritableDatabase();
		sql.execSQL("DELETE FROM " + tableName);
		sql.close();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + tableName);
		onCreate(db);
	}

	//
	// public boolean has(String selection) {
	// SQLiteDatabase sql = this.getWritableDatabase();
	// Cursor mCursor = sql.query(tableName, null, selection, null, null,null,
	// null);
	// try {
	// if (mCursor != null) {
	// if (mCursor.moveToNext()) {
	// return true;
	// }
	// }
	// } finally {
	// mCursor.close();
	// sql.close();
	// }
	//
	// return false;
	// }
	//
	//
	// public boolean hasId(String id) {
	// SQLiteDatabase sql = this.getWritableDatabase();
	// Cursor mCursor = sql.query(tableName, null, BUSINESSIDID + "='" + id +
	// "'", null,
	// null, null, USERNAME);
	// try {
	// if (mCursor != null) {
	// if (mCursor.moveToNext()) {
	// return true;
	// }
	// }
	// } finally {
	// mCursor.close();
	// sql.close();
	// }
	// return false;
	// }
	//
	// public String[] findOne(String id,String name,String[] clumns){
	// List<String[]>
	// list=find(clumns,USERNAME+"='"+name+"' and "+BUSINESSIDID+"='"+id+"'");
	// if(list.size()>0){
	// return list.get(0);
	// }
	// return null;
	// }
	//
	// public List<String[]> findByid(String id) {
	// SQLiteDatabase sql = this.getWritableDatabase();
	// List<String[]> retn = new ArrayList<String[]>();
	// Cursor mCursor = sql.query(tableName, new
	// String[]{USERNAME,ITEMCOUNT,ITEMID}, BUSINESSIDID + "='" + id + "'",
	// null,
	// null, null, "DESC");
	// if (mCursor != null) {
	// while (mCursor.moveToNext()) {
	// String str[] = new String[mCursor.getColumnCount()];
	// for (int i = 0; i < str.length; i++) {
	// str[i] = mCursor.getString(i);
	// }
	// retn.add(str);
	// }
	// mCursor.close();
	// }
	// sql.close();
	// return retn;
	// }
	//
	// public List<String[]> find(String[] columns, String selection) {
	// SQLiteDatabase sql = this.getWritableDatabase();
	// List<String[]> retn = new ArrayList<String[]>();
	// Cursor mCursor = sql.query(tableName, columns, selection, null, null,
	// null, null);
	// if (mCursor != null) {
	// while (mCursor.moveToNext()) {
	// String str[] = new String[mCursor.getColumnCount()];
	// for (int i = 0; i < str.length; i++) {
	// str[i] = mCursor.getString(i);
	// }
	// retn.add(str);
	// }
	// }
	// mCursor.close();
	// sql.close();
	// return retn;
	// }
	//
	// public String[] findbyid(String id, String name) {
	// List<String[]> list = this.find(BUSINESSIDID + "='" + id + "' " +
	// USERNAME + "='"
	// + name + "'");
	// if (list.size() > 0) {
	// return list.get(0);
	// }
	// return null;
	// }

}
