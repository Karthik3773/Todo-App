package com.example.todo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TABLE = "todo1";
    public DBHelper(Context context) {
        super(context, "todo1App.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase database) {
        String query;
        query = "CREATE TABLE IF NOT EXISTS todo1( _id INTEGER PRIMARY KEY, work TEXT)";
        database.execSQL(query);
    }

    public String InsertData(String work) {
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            String query = "insert into todo1 (work) values ('" + work + "')";
            database.execSQL(query);
            database.close();
            return "Task Added";

        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public void DeleteData(String work){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE,"work=?",new String[]{work});
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old,int current_version) {
        String query;
        query = "DROP TABLE IF EXISTS todo1";
        database.execSQL(query);
        onCreate(database);
    }

    public Cursor getworks() {
        try {
            String selectQuery = "SELECT * FROM todo1 order by _id";
            SQLiteDatabase database = this.getWritableDatabase();
            return database.rawQuery(selectQuery, null);
        } catch (Exception ex) {
            return null;
        }
    }
}

