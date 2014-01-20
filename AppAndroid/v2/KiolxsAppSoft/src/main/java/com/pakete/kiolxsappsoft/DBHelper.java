package com.pakete.kiolxsappsoft;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	private static final String TAG = DBHelper.class.getSimpleName();
	public static final int DB_VERS = 1;
	public static final String TABLALogEventos = "logeventos";
	public static final boolean Debug = false;

	public DBHelper(Context context, final String DB_NAME) {
		super(context, DB_NAME, null, DB_VERS);
	}


	public Cursor query(SQLiteDatabase db, String query) {
		Cursor cursor = db.rawQuery(query, null);
		if (Debug) {
			Log.d(TAG, "Executing Query: "+ query);
		}
		return cursor;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		/* Create table Logic, once the Application has ran for the first time. */
		String sql2 = String.format("CREATE TABLE %s (id_evento INTEGER PRIMARY KEY AUTOINCREMENT, evento text, fecha text, status text)", TABLALogEventos);
		
        db.execSQL(sql2);

        if (Debug) {
			Log.d(TAG, "onCreate Called.");
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLALogEventos));

		if (Debug) {
			Log.d(TAG, "Upgrade: Dropping Table and Calling onCreate");
		}
		this.onCreate(db);
		
	}
}
