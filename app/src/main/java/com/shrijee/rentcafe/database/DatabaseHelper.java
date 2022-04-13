package com.shrijee.rentcafe.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.shrijee.rentcafe.model.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "RentDB";

    private static String USER_TABLE = "USER";
    private static String USER_COL1 = "USER_ID";
    private static String USER_COL2 = "USER_NAME";
    private static String USER_COL3 = "USER_EMAIL";
    private static String USER_COL4 = "USER_PASSWORD";
    private static String USER_COL5 = "USER_CONTACT";
    private static String CREATE_USER =  "CREATE TABLE " + USER_TABLE + " ("
            + USER_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + USER_COL2 + " VARCHAR(30) NOT NULL, "
            + USER_COL3 + " VARCHAR(50) NOT NULL, "
            + USER_COL4 + " VARCHAR(50) NOT NULL, "
            + USER_COL5 + " INTEGER(20) NOT NULL "
            + ");";
    private static String DROP_USER = "DROP TABLE "+USER_TABLE+";";

//    private static String PROPERTY_TABLE = "PROPERTY";
//    private static String PROPERTY_COL1 = "PROPERTY_ID";
//    private static String PROPERTY_COL2 = "PROPERTY_RENTER_ID";
//    private static String PROPERTY_COL3 = "PROPERTY_DESCRIPTION";
//    private static String PROPERTY_COL4 = "PROPERTY_RATING";
//    private static String PROPERTY_COL5 = "PROPERTY_PRICE";
//    private static String PROPERTY_COL6 = "PROPERTY_";
//    private static String CREATE_PROPERTY =  "CREATE TABLE " + USER_TABLE + " ("
//            + USER_COL1 + "INTEGER AUTOINCREMENT PRIMARY KEY NOT NULL, "
//            + USER_COL2 + "VARCHAR(30) NOT NULL, "
//            + USER_COL3 + "VARCHAR(50) NOT NULL, "
//            + USER_COL4 + "VARCHAR(50) NOT NULL, "
//            + USER_COL5 + "INTEGER(20) NOT NULL, "
//            + ");";



    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_USER);

        onCreate(sqLiteDatabase);
    }

    public boolean save(User user)
    {
        if(user!=null)
        {
            try
            {
                SQLiteDatabase sqLiteDatabase = getWritableDatabase();
                ContentValues contentValues = new ContentValues();

                contentValues.put(USER_COL2, user.getName());
                contentValues.put(USER_COL3, user.getEmail());
                contentValues.put(USER_COL4, user.getPassword());
                contentValues.put(USER_COL5, user.getContact());

                long affectedRows= sqLiteDatabase.insert(USER_TABLE,null,contentValues);
                return affectedRows != -1;
            }
            catch (Exception e)
            {
                Log.e("ERROR CATCH", "Database helper -> save: "+ e.getMessage() );
                return false;
            }
        }
        return false;
    }

    public User loginCheck(String email,String password)
    {
        try{
            User user = null;
            String query = "SELECT * FROM " + USER_TABLE + " WHERE " + USER_COL3 + "= ? and " + USER_COL4 + "= ? ;";
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery(query,new String[]{email,password});
            while (cursor.moveToNext())
            {
                user = new User(cursor.getInt(0));
                user.setName(cursor.getString(1));
                user.setEmail(cursor.getString(2));
                user.setName(cursor.getString(3));
                user.setContact(String.valueOf(cursor.getInt(4)));
            }
            return user;
        }
        catch (Exception e)
        {
            Log.e("ERROR CATCH", "loginCheck: " + e.getMessage() );
        }
        return null;
    }
}
