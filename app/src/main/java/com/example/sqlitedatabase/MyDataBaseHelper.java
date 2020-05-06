package com.example.sqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Student.db";
    private static final String TABLE_NAME = "student_details";
    private static final String ID = "_id";
    private static final String NAME = "name";
    private static final String AGE = "age";
    private static final String GENDER = "gender";
    private static final int VERSION_NUMBER = 3;
    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+NAME+" VARCHAR(255), "+AGE+" INTEGER,"+GENDER+" VARCHAR(15));";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;
    private static final String READ_ALL_DATA= "SELECT * FROM "+TABLE_NAME;
    private Context context;

    public MyDataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(CREATE_TABLE);
            Toast.makeText(context,"onCreate is called",Toast.LENGTH_LONG).show();
        } catch (Exception e){
            Toast.makeText(context,"Exception"+e,Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE);
            onCreate(db);
            Toast.makeText(context,"onUpgrade is called",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(context,"Exception"+e,Toast.LENGTH_LONG).show();
        }

    }

    public long insertData(String name,String age, String gender){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,name);
        contentValues.put(AGE,age);
        contentValues.put(GENDER,gender);

        long rowID = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        return rowID;
    }

    public Cursor displayAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(READ_ALL_DATA,null);
        return cursor;
    }

    public boolean updateData(String id , String name,String age, String gender){
        SQLiteDatabase sqLiteDatabase  = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID,id);
        contentValues.put(NAME,name);
        contentValues.put(AGE,age);
        contentValues.put(GENDER,gender);

        sqLiteDatabase.update(TABLE_NAME,contentValues,ID+" = ?", new String[] {id});
        return true;
    }

    public int deleteData(String id){
        SQLiteDatabase sqLiteDatabase  = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME,ID+" = ?",new String[] {id});
    }
}
