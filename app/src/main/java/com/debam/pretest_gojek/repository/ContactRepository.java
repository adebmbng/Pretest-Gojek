package com.debam.pretest_gojek.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.debam.pretest_gojek.interfaces.RepositoryListener;
import com.debam.pretest_gojek.models.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Debam on 2/4/17.
 */

public class ContactRepository  extends SQLiteOpenHelper implements RepositoryListener {


    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_NAME = "contactsManager";

    private static final String TABLE_CONTACTS = "contacts";

    private static final String CREATE_TABLE_CONTACT = "CREATE TABLE "+TABLE_CONTACTS+
            "(id TEXT PRIMARY KEY," +
            " first_name TEXT," +
            " last_name TEXT," +
            " email TEXT," +
            " phone_number TEXT," +
            " profil_pic TEXT," +
            " created_at TEXT," +
            " updated_at TEXT," +
            " favorite TEXT);";

    @Override
    public void insert(List<Contact> lContact) {
        SQLiteDatabase db = this.getWritableDatabase();

        for(Contact c: lContact) {
            ContentValues values = new ContentValues();
            values.put("id",c.getId());
            values.put("first_name",c.getFirst_name());
            values.put("last_name", c.getLast_name());
            values.put("email", c.getEmail());
            values.put("phone_number", c.getPhone_number());
            values.put("profil_pic", c.getProfile_pic());
            values.put("created_at", c.getCreated_at());
            values.put("updated_at", c.getUpdated_at());
            values.put("favorite", c.isFavorite());

            db.insert(TABLE_CONTACTS, null, values);
        }

        db.close();

    }

    @Override
    public List<Contact> loaddb() {
        List<Contact> mList = new ArrayList<>();
        String query = "SELECT * FROM "+TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cs = db.rawQuery(query, null);
        if(cs.moveToFirst()){
            do{
                Contact c = new Contact();
                c.setId(cs.getString(0));
                c.setFirst_name(cs.getString(1));
                c.setLast_name(cs.getString(2));
                c.setEmail(cs.getString(3));
                c.setPhone_number(cs.getString(4));
                c.setProfile_pic(cs.getString(5));
                c.setCreated_at(cs.getString(6));
                c.setUpdated_at(cs.getString(7));
                c.setFavorite(cs.getString(8));

                mList.add(c);
            }while(cs.moveToNext());
        }
        return mList;
    }


    @Override
    public Contact getContactById(int i) {
        return null;
    }

    @Override
    public Contact getContactByNum(String num) {
        return null;
    }

    @Override
    public void deleteContact(Contact c) {

    }

    @Override
    public int countDB() {
        List<Contact> mList = loaddb();
        return mList.size();
    }

    public ContactRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CONTACT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CONTACTS);
        onCreate(db);
    }
}
