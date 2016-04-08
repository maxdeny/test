package com.wjwl.mobile.taocz.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLDB extends SQLiteOpenHelper {
	SQLiteDatabase db;
	public final String tbname1 = "t_userinfo";
	//public final String tbname2 = "t_takeout";
	public static final String dbName = "taocz";
	public static final int version = 1;

	public SQLDB(Context context) {
		super(context, dbName, null, version);
	}

	/**
	 * 创建表的内容
	 * 
	 * (任务与视频信息列表)
	 * c_id,c_pid（推送序号）,c_downloadtype(视频类型，1=网站下载，2=点对点，3=转发，),c_title,c_url,
	 * c_senderphone（ 发送者手机号码
	 * ）,c_sendername（发送者名称）,c_getinfotimes(获取信息的次数)，c_downloadtimes(尝试下载的次数
	 * )，c_videosize
	 * (视频文件大小),c_videodate(推送日期)，c_filetype(文件类型，3gp,mp4等),c_videofile
	 * (视频文件名，含路径)，c_status(1=收到短信，2=已获取信息，3下载中，4下载完成，5已经播放)
	 * 
	 * (用户信息列表) c_id,c_telnum,c_pw,c_registerstate(0-未注册，2-注册成功)
	 * 
	 * (系统配置表) c_downloadType (2手动下载，1自动下载) c_UpdateType(1自动更新，2手动更新)
	 * 
	 */

	@Override
	public void onCreate(SQLiteDatabase db) {
		this.db = db;
		String sql_1 = "create table if not exists "
				+ tbname1
				+ "(c_id integer default '1' not null primary key autoincrement,"
				+ "c_userid text," + "c_username text,"
				+ "c_userpassword text," + "c_rememberpassword text)";
//		String sql_2 = "create table if not exists "
//				+ tbname2
//				+ "(c_id integer default '1' not null primary key autoincrement,"
//				+ "c_takeoutid text," + "c_takeoutcount integer)";
		db.execSQL(sql_1);
//		db.execSQL(sql_2);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql_1 = "DROP TABLE IF EXISTS " + tbname1;
//		String sql_2 = "DROP TABLE IF EXISTS " + tbname2;
		db.execSQL(sql_1);
//		db.execSQL(sql_2);
		onCreate(db);
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		// TODO 每次成功打开数据库后首先被执行
	}

	/**
	 * 添加数据
	 */
	public long insert(String tabname, ContentValues values) {
		SQLiteDatabase db = this.getWritableDatabase();
		long result = db.insert(tabname, null, values);
		db.close();
		return result;
	}

	/**
	 * 删除记录
	 * 
	 * @param _id
	 */
	public void delete(String tbname, String whereClause, String[] whereArgs) {
		SQLiteDatabase db = getWritableDatabase();
		db.delete(tbname, whereClause, whereArgs);
		db.close();
	}

	public void delete2(String tbname, String whereClause, String[] whereArgs) {
		SQLiteDatabase db = getWritableDatabase();
		db.delete(tbname, whereClause, whereArgs);
		db.close();
	}
	/**
	 * 更新记录的，跟插入的很像
	 */

	public void update(String tbname, String whereClause, String[] whereArgs,
			ContentValues values) {
		SQLiteDatabase db = getWritableDatabase();
		db.update(tbname, values, whereClause + "=?", whereArgs);
		db.close();
	}

	public void update(String tbname, String whereClause, String whereArgs,
			ContentValues values) {
		SQLiteDatabase db = getWritableDatabase();
		db.update(tbname, values, whereClause + "=?",
				new String[] { whereArgs });
		db.close();
	}

	/**
	 * 有条件查询某几列数据
	 * 
	 * @tbname 表名
	 * @colnames 查询内容
	 * @selection 条件字段
	 * @selectionArgs 条件值
	 * @return Cursor
	 */
	public Cursor select(String tbname, String[] colnmns, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(tbname, colnmns, selection + "=?",
				selectionArgs, null, null, null);
		// String name = cursor.getString(cursor
		// .getColumnIndex("name"));
		db.close();
		return cursor;
	}

	public Cursor select(String tbname, String colnmns, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(tbname, new String[] { colnmns }, selection
				+ "=?", selectionArgs, null, null, null);
		// String name = cursor.getString(cursor
		// .getColumnIndex("name"));
		db.close();
		return cursor;
	}

	/**
	 * 无条件判断查询某几列数据 tbname 表名 colnames 查询内容
	 * 
	 * @tbname 表名
	 * @colnames 查询内容
	 * @return Cursor
	 */
	public Cursor query(String tbname, String[] colnames) {
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db
				.query(tbname, colnames, null, null, null, null, null);
		db.close();
		return cursor;
	}

	/**
	 * 无条件判断查询某一列数据
	 * 
	 * @tbname 表名
	 * @colnames 查询内容
	 * @return Cursor
	 */
	public Cursor query(String tbname, String colnames) {
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.query(tbname, new String[] { colnames }, null, null,
				null, null, null);
		db.close();
		return cursor;
	}

	/**
	 * 查询所有数据
	 * 
	 * 
	 */
	public Cursor query(String tbname) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(tbname, null, null, null, null, null, null);
		return cursor;
	}

	/**
	 * 将Cursor内String类型值全部取出来
	 * 
	 * @return String[]
	 */
	public String[] getString(Cursor cursor, String colnmns) {
		String[] result = null;
		if (cursor.getCount() > 0) {
			result = new String[cursor.getCount()];
			int num = 0;
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {
				result[num] = cursor.getString(cursor.getColumnIndex(colnmns));
				num++;
			}
		}
		return result;
	}

	/**
	 * 将Cursor内int类型值全部取出来
	 * 
	 * @return int[]
	 */
	public int[] getInt(Cursor cursor, String colnmns) {
		int[] result = null;
		if (cursor.getCount() > 0) {
			result = new int[cursor.getCount()];
			int num = 0;
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {
				result[num] = cursor.getInt(cursor.getColumnIndex(colnmns));
				num++;
			}
		}
		return result;
	}

	public void close() {
		if (db != null)
			db.close();
	}

	public boolean tabIsExist(String tabName) {
		boolean result = false;
		if (tabName == null) {
			return false;
		}
		Cursor cursor = null;

		SQLiteDatabase dbInfo = this.getReadableDatabase();
		try {

			String sql = "select count(*) as name from sqlite_master where type ='table' and name ='"
					+ this.tbname1.trim() + "'";
			cursor = dbInfo.rawQuery(sql, null);
			if (cursor.moveToNext()) {
				int count = cursor.getInt(0);
				if (count > 0) {
					result = true;
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		dbInfo.close();
		return result;
	}
}