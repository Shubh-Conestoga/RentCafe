package com.shrijee.rentcafe.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.shrijee.rentcafe.model.Rent;
import com.shrijee.rentcafe.model.RentedProperty;
import com.shrijee.rentcafe.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

// Database Helper for SQLite Database
public class DatabaseHelper extends SQLiteOpenHelper {

    // Field name constants
    private static String DB_NAME = "RentDB";
    private static String USER_TABLE = "USER";
    private static String USER_COL1 = "USER_ID";
    private static String USER_COL2 = "USER_NAME";
    private static String USER_COL3 = "USER_EMAIL";
    private static String USER_COL4 = "USER_PASSWORD";
    private static String USER_COL5 = "USER_CONTACT";
    
    // create table statement
    private static String CREATE_USER =  "CREATE TABLE " + USER_TABLE + " ("
            + USER_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + USER_COL2 + " VARCHAR(30) NOT NULL, "
            + USER_COL3 + " VARCHAR(50) NOT NULL, "
            + USER_COL4 + " VARCHAR(50) NOT NULL, "
            + USER_COL5 + " INTEGER(20) NOT NULL "
            + ");";
    
    // Drop table statement
    private static String DROP_USER = "DROP TABLE "+USER_TABLE+";";

    private static String PROPERTY_TABLE = "PROPERTY";
    private static String PROPERTY_COL1 = "PROPERTY_ID";
    private static String PROPERTY_COL2 = "PROPERTY_RENTER_ID";
    private static String PROPERTY_COL3 = "PROPERTY_NAME";
    private static String PROPERTY_COL4 = "PROPERTY_TYPE";
    private static String PROPERTY_COL5 = "PROPERTY_PRICE";
    private static String PROPERTY_COL6 = "PROPERTY_PINCODE";
    private static String PROPERTY_COL7 = "PROPERTY_CITY";
    private static String PROPERTY_COL8 = "PROPERTY_STATE";
    private static String PROPERTY_COL9 = "PROPERTY_DESCRIPTION";
    private static String PROPERTY_COL10 = "PROPERTY_BEDROOM";
    private static String PROPERTY_COL11 = "PROPERTY_BATHROOM";
    private static String PROPERTY_COL12 = "PROPERTY_UTILITIES";
    private static String PROPERTY_COL13 = "PROPERTY_FURNISHED";
    private static String PROPERTY_COL14 = "PROPERTY_PARKING";
    private static String PROPERTY_COL15 = "PROPERTY_IMAGE_URL";
    private static String PROPERTY_COL16 = "PROPERTY_ADDRESS";

    // create table statement
    private static String CREATE_PROPERTY =  "CREATE TABLE " + PROPERTY_TABLE + " ("
            + PROPERTY_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + PROPERTY_COL2 + " INTEGER NOT NULL, "
            + PROPERTY_COL3 + " VARCHAR(30) NOT NULL, "
            + PROPERTY_COL4 + " VARCHAR(30) NOT NULL, "
            + PROPERTY_COL5 + " DECIMAL(10,2) NOT NULL, "
            + PROPERTY_COL6 + " VARCHAR(7) NOT NULL, "
            + PROPERTY_COL7 + " VARCHAR(10) NOT NULL, "
            + PROPERTY_COL8 + " VARCHAR(10) NOT NULL, "
            + PROPERTY_COL9 + " TEXT, "
            + PROPERTY_COL10 + " INTEGER(3) DEFAULT 3, "
            + PROPERTY_COL11 + " INTEGER(3) DEFAULT 2, "
            + PROPERTY_COL12 + " TEXT, "
            + PROPERTY_COL13 + " INTEGER(1) DEFAULT 0, "
            + PROPERTY_COL14 + " INTEGER(1) DEFAULT 0, "
            + PROPERTY_COL15 + " TEXT "
            + " );";
    
    // Drop table statement
    private static String DROP_PROPERTY = "DROP TABLE "+PROPERTY_TABLE+";";


    private static String RENTED_PROPERTY_TABLE = "RENTED_PROPERTY";
    private static String RENTED_PROPERTY_COL1 = "RENTED_PROPERTY_ID";
    private static String RENTED_PROPERTY_COL2 = "PROPERTY_ID";
    private static String RENTED_PROPERTY_COL3 = "RENTER_ID";
    private static String RENTED_PROPERTY_COL4 = "RENTEE_ID";
    private static String RENTED_PROPERTY_COL5 = "LEASE_DURATION";
    private static String RENTED_PROPERTY_COL6 = "LEASE_START_DATE";
    private static String RENTED_PROPERTY_COL7 = "ACTIVE_FLAG";


    private static String CREATE_RENTED_PROPERTY =  "CREATE TABLE " + RENTED_PROPERTY_TABLE + " ("
            + RENTED_PROPERTY_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + RENTED_PROPERTY_COL2 + " INTEGER NOT NULL, "
            + RENTED_PROPERTY_COL3 + " INTEGER NOT NULL, "
            + RENTED_PROPERTY_COL4 + " INTEGER NOT NULL, "
            + RENTED_PROPERTY_COL5 + " INTEGER(2) NOT NULL, "
            + RENTED_PROPERTY_COL6 + " INTEGER, "
            + RENTED_PROPERTY_COL7 + " INTEGER(1) DEFAULT 0 "
            + ");";

    private static String DROP_RENTED_PROPERTY_TABLE = "DROP TABLE "+RENTED_PROPERTY_TABLE+";";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER);
        sqLiteDatabase.execSQL(CREATE_PROPERTY);
        sqLiteDatabase.execSQL(CREATE_RENTED_PROPERTY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_USER);
        sqLiteDatabase.execSQL(DROP_RENTED_PROPERTY_TABLE);
        sqLiteDatabase.execSQL(DROP_PROPERTY);

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

    public boolean save(Rent rent)
    {
        if(rent!=null)
        {
            try
            {
                SQLiteDatabase sqLiteDatabase = getWritableDatabase();

                ContentValues contentValues = new ContentValues();
                contentValues.put(PROPERTY_COL2, rent.getRenter() );
                contentValues.put(PROPERTY_COL3, rent.getRentName() );
                contentValues.put(PROPERTY_COL4, rent.getType() );
                contentValues.put(PROPERTY_COL5, rent.getPrice() );
                contentValues.put(PROPERTY_COL6, rent.getPincode() );
                contentValues.put(PROPERTY_COL7, rent.getCity() );
                contentValues.put(PROPERTY_COL8, rent.getState() );
                contentValues.put(PROPERTY_COL9, rent.getDescription() );
                contentValues.put(PROPERTY_COL10,rent.getNoOfBedroom());
                contentValues.put(PROPERTY_COL11,rent.getNoOfBathroom());
                contentValues.put(PROPERTY_COL12,getStringToList(rent.getUtilities()));
                contentValues.put(PROPERTY_COL13,rent.getFurnished());
                contentValues.put(PROPERTY_COL14,rent.getParking());
                contentValues.put(PROPERTY_COL15,rent.getImageURL());

                long affectedRows= sqLiteDatabase.insert(PROPERTY_TABLE,null,contentValues);
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

    public boolean save(RentedProperty rentedProperty)
    {
        if(rentedProperty!=null)
        {
            try
            {
                SQLiteDatabase sqLiteDatabase = getWritableDatabase();
                ContentValues contentValues = new ContentValues();

                contentValues.put(RENTED_PROPERTY_COL2, rentedProperty.getRent().getRentId() );
                contentValues.put(RENTED_PROPERTY_COL3, rentedProperty.getRenterId() );
                contentValues.put(RENTED_PROPERTY_COL4, rentedProperty.getRenteeId() );
                contentValues.put(RENTED_PROPERTY_COL5, rentedProperty.getDuration() );
                contentValues.put(RENTED_PROPERTY_COL6, rentedProperty.getStartingDate().getTime() );
                contentValues.put(RENTED_PROPERTY_COL7, rentedProperty.getActiveFlag() );

                long affectedRows= sqLiteDatabase.insert(RENTED_PROPERTY_TABLE,null,contentValues);
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

    public List<Rent> getRentDetails()
    {
        List<Rent> rentList = new ArrayList<>();
        Rent rent = null;
        String columnList = "p."+PROPERTY_COL1
                +",p."+PROPERTY_COL2
                +",p."+PROPERTY_COL3
                +",p."+PROPERTY_COL4
                +",p."+PROPERTY_COL5
                +",p."+PROPERTY_COL6
                +",p."+PROPERTY_COL7
                +",p."+PROPERTY_COL8
                +",p."+PROPERTY_COL9
                +",p."+PROPERTY_COL10
                +",p."+PROPERTY_COL11
                +",p."+PROPERTY_COL12
                +",p."+PROPERTY_COL13
                +",p."+PROPERTY_COL14
                +",p."+PROPERTY_COL15;
        String query = "SELECT "+ columnList + " FROM " + PROPERTY_TABLE + " p WHERE p." + PROPERTY_COL1 + " NOT IN(SELECT "+RENTED_PROPERTY_COL2+" FROM "+ RENTED_PROPERTY_TABLE+ " WHERE ACTIVE_FLAG = ?);" ;
       try{
           SQLiteDatabase sqLiteDatabase = getReadableDatabase();
           Cursor cursor = sqLiteDatabase.rawQuery(query,new String[]{String.valueOf(1)});
           while (cursor.moveToNext())
           {
               rent = new Rent();
               setRentFromCursor(rent,cursor,0);
               rentList.add(rent);
           }
           return rentList;
       }
       catch (Exception e)
       {
           Log.e("ERROR CATCH", "loginCheck: " + e.getMessage() );
       }
       return rentList;
    }

    private void setRentFromCursor(Rent rent, Cursor cursor, int i) {
        rent.setRentId(cursor.getInt(i++));
        rent.setRenter(cursor.getInt(i++));
        rent.setRentName(cursor.getString(i++));
        rent.setType(cursor.getString(i++));
        rent.setPrice((float) cursor.getDouble(i++));
        rent.setPincode(cursor.getString(i++));
        rent.setCity(cursor.getString(i++));
        rent.setState(cursor.getString(i++));
        rent.setDescription(cursor.getString(i++));
        rent.setNoOfBedroom(cursor.getInt(i++));
        rent.setNoOfBathroom(cursor.getInt(i++));
        rent.setUtilities(getListToString(cursor.getString(i++)));
        rent.setFurnished(cursor.getInt(i++));
        rent.setParking(cursor.getInt(i++));
        rent.setImageURL(cursor.getString(i++));
    }

    public List<RentedProperty> getRentedPropertyDetails(String searchColumn,int value)
    {
        List<RentedProperty> rentList = new ArrayList<>();
        RentedProperty rentedProperty = null;
        Rent rent = null;
        String columns = "r."+RENTED_PROPERTY_COL1+",r."
                +RENTED_PROPERTY_COL2
                +",r."+RENTED_PROPERTY_COL3
                +",r."+RENTED_PROPERTY_COL4
                +",r."+RENTED_PROPERTY_COL5
                +",r."+RENTED_PROPERTY_COL6
                +",r."+RENTED_PROPERTY_COL7
                +",p."+PROPERTY_COL1
                +",p."+PROPERTY_COL2
                +",p."+PROPERTY_COL3
                +",p."+PROPERTY_COL4
                +",p."+PROPERTY_COL5
                +",p."+PROPERTY_COL6
                +",p."+PROPERTY_COL7
                +",p."+PROPERTY_COL8
                +",p."+PROPERTY_COL9
                +",p."+PROPERTY_COL10
                +",p."+PROPERTY_COL11
                +",p."+PROPERTY_COL12
                +",p."+PROPERTY_COL13
                +",p."+PROPERTY_COL14
                +",p."+PROPERTY_COL15
                ;
        String query = "SELECT "+ columns +" FROM " + RENTED_PROPERTY_TABLE + " r, " + PROPERTY_TABLE + " p";
        if(!searchColumn.isEmpty())
        {
                query+=" WHERE " + "p.PROPERTY_ID = r.PROPERTY_ID AND " + searchColumn.trim().toUpperCase() + " = ? AND ACTIVE_FLAG = 1;";
        }
        else
        {
            query+=" ;";
        }
        try{
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor;
            if(searchColumn.trim().toUpperCase().isEmpty())
            {
                cursor = sqLiteDatabase.rawQuery(query,null);
            }
            else
            {
                cursor = sqLiteDatabase.rawQuery(query,new String[]{String.valueOf(value)});
            }

            while (cursor.moveToNext())
            {
                rentedProperty = new RentedProperty();
                rent = new Rent();
                rentedProperty.setRentedPropertyId(cursor.getInt(0));
                rentedProperty.setPropertyId(cursor.getInt(1));
                rentedProperty.setRenterId(cursor.getInt(2));
                rentedProperty.setRenteeId(cursor.getInt(3));
                rentedProperty.setDuration(cursor.getInt(4));
                rentedProperty.setStartingDate(new Date(cursor.getInt(5)));
                rentedProperty.setActiveFlag((cursor.getInt(6)));

                setRentFromCursor(rent,cursor,7);
                rentedProperty.setRent(rent);

                rentList.add(rentedProperty);
            }
            return rentList;
        }
        catch (Exception e)
        {
            Log.e("ERROR CATCH", "loginCheck: " + e.getMessage() );
        }
        return rentList;
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

    private String getStringToList(List<String> utilities) {
        String returnString = "";
        StringBuilder stringBuilder = new StringBuilder(returnString);
        for(String utility : utilities)
        {
            stringBuilder.append(utility +",");
        }
        return stringBuilder.deleteCharAt(stringBuilder.length() -1).toString();
    }

    private List<String> getListToString(String utilities) {
        return Arrays.asList(utilities.split(","));
    }

    public int getRenterIdByEmail(String email) {
        String query = "SELECT " + USER_COL1 + " FROM " + USER_TABLE + " WHERE " + USER_COL3 + "= ?;";
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query,new String[]{email});
        while (cursor.moveToNext())
        {
            int userId = cursor.getInt(0);
            return userId;
        }
        return -1;
    }

    public List<RentedProperty> getRenteePropertyDetails(int userId) {
        List<RentedProperty> rentList = new ArrayList<>();
        Rent rent = null;
        RentedProperty rentedProperty = null;
        String columnList = "DISTINCT(p."+PROPERTY_COL1
                +"),p."+PROPERTY_COL2
                +",p."+PROPERTY_COL3
                +",p."+PROPERTY_COL4
                +",p."+PROPERTY_COL5
                +",p."+PROPERTY_COL6
                +",p."+PROPERTY_COL7
                +",p."+PROPERTY_COL8
                +",p."+PROPERTY_COL9
                +",p."+PROPERTY_COL10
                +",p."+PROPERTY_COL11
                +",p."+PROPERTY_COL12
                +",p."+PROPERTY_COL13
                +",p."+PROPERTY_COL14
                +",p."+PROPERTY_COL15
                +",r."+RENTED_PROPERTY_COL7;
        String sql = "SELECT " + columnList + " FROM " + PROPERTY_TABLE + " p LEFT OUTER JOIN " + RENTED_PROPERTY_TABLE + " r ON r.PROPERTY_ID = p.PROPERTY_ID WHERE " + PROPERTY_COL2 + " = ?;";
        try{
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor;
            cursor = sqLiteDatabase.rawQuery(sql,new String[]{String.valueOf(userId)});
            while (cursor.moveToNext())
            {
                rentedProperty = new RentedProperty();
                rent = new Rent();
                setRentFromCursor(rent,cursor,0);
                rentedProperty.setActiveFlag(cursor.getInt(15));
                rentedProperty.setRent(rent);
                rentList.add(rentedProperty);
            }
            return rentList;
        }
        catch (Exception e)
        {
            Log.e("ERROR CATCH", "loginCheck: " + e.getMessage() );
        }
        return rentList;
    }

    public boolean removeRentendProperty(int rentedPropertyId) {

        try
        {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(RENTED_PROPERTY_COL7,0);
            int affectedRows = sqLiteDatabase.update(RENTED_PROPERTY_TABLE,contentValues,RENTED_PROPERTY_COL1 + " = ?",new String[]{String.valueOf(rentedPropertyId)});
            if(affectedRows==-1)
            {
                return false;
            }
            else
            {
                return true;
            }

        }
        catch (Exception e)
        {
            Log.e("ERROR CHECK", "removeRentendProperty: " + e.getMessage());
        }
        return false;
    }

    public String getPhoneNoById(int renter) {
        String sql = "SELECT " + USER_COL5 + " FROM " + USER_TABLE + " WHERE " + USER_COL1 + " = ?;";
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        try
        {
            Cursor cursor = sqLiteDatabase.rawQuery(sql,new String[]{String.valueOf(renter)});
            while(cursor.moveToNext())
            {
                return cursor.getString(0);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }
}
