package sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import viewmodel.PersonViewModel;

/**
 * Created by Rozerin on 7.07.2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "AndroidFinishUp.db";
    public static final String PERSON_TABLE_NAME = "Person";
    public static final String PERSON_COLUMN_ID ="id";
    public static final String PERSON_COLUMN_NAME = "name";
    public static final String PERSON_COLUMN_SURNAME = "surname";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table " + PERSON_TABLE_NAME + "("
                    + PERSON_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + PERSON_COLUMN_NAME + " TEXT, "
                    + PERSON_COLUMN_SURNAME + " TEXT    "
                    +")"
                   );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PERSON_TABLE_NAME);
        onCreate(db);
    }

    public PersonViewModel getPersonInfo(int id){

        PersonViewModel personViewModel = new PersonViewModel();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + PERSON_TABLE_NAME + " where id=" + id + "", null);

        cursor.moveToFirst();

        personViewModel.setName(cursor.getString(cursor.getColumnIndex(PERSON_COLUMN_NAME)));
        personViewModel.setSurname(cursor.getString(cursor.getColumnIndex(PERSON_COLUMN_SURNAME)));


        if (!cursor.isClosed()) {
            cursor.close();
        }

        return personViewModel;

    }


    public boolean updatePerson(Integer id, String name, String surname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PERSON_COLUMN_NAME, name);
        contentValues.put(PERSON_COLUMN_SURNAME, surname);

        db.update(PERSON_TABLE_NAME, contentValues, "id=" + id,  new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deletePerson(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PERSON_TABLE_NAME,
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public boolean addPeople(String name, String surname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PERSON_COLUMN_NAME, name);
        contentValues.put(PERSON_COLUMN_SURNAME, surname);
        db.insert(PERSON_TABLE_NAME, null, contentValues);
        return true;
    }
    public ArrayList<PersonViewModel> getAllPeople() {
        ArrayList<PersonViewModel> array_list = new ArrayList<>();

        PersonViewModel personViewModel ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + PERSON_TABLE_NAME, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            personViewModel = new PersonViewModel();
            personViewModel.setId(res.getInt(res.getColumnIndex(PERSON_COLUMN_ID)));
            personViewModel.setName(res.getString(res.getColumnIndex(PERSON_COLUMN_NAME)));
            personViewModel.setSurname(res.getString(res.getColumnIndex(PERSON_COLUMN_SURNAME)));

            array_list.add(personViewModel);
            res.moveToNext();
        }

        res.close();

        return array_list;
    }

}
