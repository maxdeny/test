package com.example.goldfoxchina.util;

import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 实例化对数据库的操作
 * 
 * @author kysl
 * 
 */
public class ClassicMessageDAO {

	private DBOpenHelper db;
	private static ClassicMessageDAO dao = null;

	// ArrayList<HashMap<String, String>> array;

	public synchronized static ClassicMessageDAO getClassicMessageDAO(
			Context context) {
		if (dao == null) {
			dao = new ClassicMessageDAO(context);

		}

		return dao;
	}

	private ClassicMessageDAO(Context context) {
		db = DBOpenHelper.getDBOpenHelper(context, "ReceivingInformation.db",
				null, 1); // 数据库名称为ReceivingInformation.db
		// array = new ArrayList<HashMap<String, String>>(); // 存放查询出的数据
	}

	// 插入数据
	public boolean InsertData(String name, String telnum, String zipcode,
			String area, String street) {
		boolean flag = false;
		SQLiteDatabase database = db.getWritableDatabase();

		try {

			database.execSQL(
					"insert into Message (name,telnum,zipcode,area,street)values(?,?,?,?,?)",
					new Object[] { name, telnum, zipcode, area, street });

			flag = true;
		} catch (Exception e) {
			flag = false;
		} finally {

			db.close();
		}
		return flag;
	}

	// 删除数据
	public boolean DelData(int id) {
		boolean flag = false;
		SQLiteDatabase database = db.getWritableDatabase();

		try {

			database.execSQL("delete from Message where _id=?",
					new Integer[] { id });

			flag = true;
		} catch (Exception e) {
			flag = false;
		} finally {

			db.close();

		}

		return flag;
	}

	// 修改数据
	public boolean UpdateData(String name, String telnum, String zipcode,
			String area, String street, int id) {
		boolean flag = false;
		SQLiteDatabase database = db.getWritableDatabase();

		try {

			database.execSQL(
					"update Message set name=?,telnum=?,zipcode=?,area=?,street=? where _id=?",
					new Object[] { name, telnum, zipcode, area, street, id });

			flag = true;
		} catch (Exception e) {
			flag = false;
		} finally {

			db.close();

		}
		return flag;
	}

	/**
	 * 查询单条数据
	 * 如果只获取了一次结果集，不关掉cursor对程序的逻辑没有影响，只是会抛一个非必要性异常，但是如果多次获取结果集，就必须先关掉cursor，
	 * 再重新获取结果集，否则cursor没释放，之后的结果集是获取不到的，且会报错。
	 * 
	 * @param id
	 * @return
	 */
	public HashMap<String, String> SelData(int id) {

		HashMap<String, String> map = null;
		Cursor c = null;
		SQLiteDatabase database = db.getReadableDatabase();

		try {

			c = database.rawQuery("select * from Message where _id=?",
					new String[] { id + "" });
			if (c != null) {
				while (c.moveToNext()) {
					String _id = c.getString(c.getColumnIndex("_id"));
					String name = c.getString(c.getColumnIndex("name"));
					String telnum = c.getString(c.getColumnIndex("telnum"));
					String zipcode = c.getString(c.getColumnIndex("zipcode"));
					String area = c.getString(c.getColumnIndex("area"));
					String street = c.getString(c.getColumnIndex("street"));
					map = new HashMap<String, String>();
					map.put("_id", _id);
					map.put("name", name);
					map.put("telnum", telnum);
					map.put("zipcode", zipcode);
					map.put("area", area);
					map.put("street", street);

				}
			}

		} catch (Exception e) {

		} finally {
			c.close(); // 必须关掉
			db.close();

		}

		return map;
	}

	// 查询数量
	public int SelCount() {
		int arg = 0;
		Cursor c = null;
		SQLiteDatabase database = db.getReadableDatabase();

		try {

			c = database.rawQuery("select count(*) from Message", null);
			if (c != null) {
				while (c.moveToNext()) {
					arg = c.getInt(0);
				}
			}

		} catch (Exception e) {

		} finally {
			c.close();
			db.close();

		}

		return arg;
	}

	// 查询ID
	public int[] SelID() {
		int[] count_id = null;
		Cursor c = null;
		SQLiteDatabase database = db.getReadableDatabase();

		try {

			c = database.rawQuery("select _id from Message", null);
			if (c != null) {
				count_id = new int[c.getCount()];
				int i = 0;
				while (c.moveToNext()) {
					count_id[i] = c.getInt(c.getColumnIndex("_id"));
					i++;
				}
			}

		} catch (Exception e) {

		} finally {
			c.close();
			db.close();

		}

		return count_id;
	}
}
