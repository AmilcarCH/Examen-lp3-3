package com.delaroystudios.sqlitelogin.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.delaroystudios.sqlitelogin.bean.User;
import com.delaroystudios.sqlitelogin.util.Commons;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by delaroy on 3/27/17.
 */
public class UserDAO extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;



    private static final String TABLE_USER = "user";

    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    public UserDAO(Context context){
        super(context, Commons.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public  void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(DROP_USER_TABLE);
        onCreate(db);
    }

    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public boolean checkUser(String email){
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0){
            return true;
        }
        return false;
    }

    public boolean checkUser(String email, String password){
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " =?";
        String[] selectionArgs = { email, password };

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0){
            return true;
        }
        return false;
    }

    public void updateUser(User user){
        ContentValues values = new ContentValues();
        values.put("name",user.getName());
        values.put("lastNameF",user.getEmail());
        values.put("lastNameM",user.getPassword());

        getWritableDatabase().update(TABLE_USER,values,"id="+user.getId(),null);
    }

    public User findUserById(int id) {
        String columns[] = {"id", "name", "email", "password"};
        String where = "id=" + id;
        Cursor cursor = getReadableDatabase().query(TABLE_USER, columns, where, null, null, null, null);
        User user = null;
        while (cursor.moveToNext()) {
            user = new User();
            user.setId(cursor.getInt(0));
            user.setName(cursor.getString(1));
            user.setEmail(cursor.getString(2));
            user.setPassword(cursor.getString(3));
        }
        return user;
    }
    public void deleteUserById(int id){
        String where="id="+id;
        getWritableDatabase().delete(TABLE_USER,where,null);
    }

    public ArrayList listaUser(){
        ArrayList<String> listUsers = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        String q = "SELECT * FROM user";
        Cursor registros = database.rawQuery(q,null);
        if(registros.moveToFirst()){
            do{
                listUsers.add(registros.getString(0)+ "  |  " + registros.getString(1)+ " --> "+ registros.getString(2));
            }while(registros.moveToNext());

        }
        return listUsers;

    }
}
