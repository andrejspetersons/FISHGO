package com.example.fishgomobile;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {

    private Context context;
    //database name
    private static final String DB_NAME = "userbase.sqlite";
    //database version
    private static final int DB_VERSION = 2;
    //table name
    private static final String TABLE_NAME = "user_info";
    //id column
    private static final String ID = "id";
    //Name column
    private static final String NAME = "Name";
    //Surname column
    private static final String SURNAME = "Surname";
    //Email column
    private static final String EMAIL = "Email";
    //Date of birth
    private static final String date_of_birth = "DOB";
    //Password
    private static final String PASSWORD = "Password";

    public DBHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    //method creates DB by running SQL query
    @Override
    public void onCreate(SQLiteDatabase database) {
        String request = "CREATE TABLE " + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT, "
                + SURNAME + " TEXT, "
                + EMAIL + " TEXT,"
                + date_of_birth + " TEXT, "
                + PASSWORD + " TEXT);";

        database.execSQL(request);
    }

    public void addNewUser(String name, String surname, String email, String dob, String password) {

        //creating variable for DB and calling writable method
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME, name);
        values.put(SURNAME, surname);
        values.put(EMAIL, email);
        values.put(date_of_birth, dob);
        values.put(PASSWORD, password);

        //inserting all content values in table
        db.insert(TABLE_NAME, null, values);
        //closing database
        db.close();

    }

    public Boolean UserExist(String email, String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT*FROM " + TABLE_NAME + "\nWHERE Email='" + email + "'AND Password='" + password + "';";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }

    public Cursor getEmailInfo(String email){

        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT* FROM "+TABLE_NAME+"\nWHERE Email='"+email+"';";
        Cursor cursor=db.rawQuery(query,null);
        //cursor.moveToFirst();
        return cursor;
    }

    public void DeleteData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME;
        db.execSQL(query);

    }
}