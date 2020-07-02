package com.example.projet_gestion_hospitaliere.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import com.example.projet_gestion_hospitaliere.model.Patient;
import com.example.projet_gestion_hospitaliere.model.RendezVous;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PatientDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "NewPatientRdv.db";
    private static final int DATABASE_VERSION = 1 ;
    public static final String TABLE_NAME = "NewPatient";
    public static final String TABLE_RDV = "NewRdv";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PATIENT_NAME = "name";
    public static final String COLUMN_PATIENT_AGE = "age";
    public static final String COLUMN_PATIENT_PHONE = "phone";
    public static final String COLUMN_PATIENT_ADDRESS = "address";
    public static final String COLUMN_PATIENT_IMAGE = "image";

    public static final String COLUMN_RDV_ID = "_id";
    public static final String COLUMN_RDV_NOM = "name";
    public static final String COLUMN_RDV_HEURE = "heure";
    public static final String COLUMN_RDV_DATE = "date";
    public static final String COLUMN_RDV_IMAGE = "image";


    public PatientDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PATIENT_NAME + " TEXT NOT NULL, " +
                COLUMN_PATIENT_AGE + " NUMBER NOT NULL, " +
                COLUMN_PATIENT_PHONE + " TEXT NOT NULL, " +
                COLUMN_PATIENT_ADDRESS + " TEXT NOT NULL, " +
                COLUMN_PATIENT_IMAGE + " BLOB NOT NULL);"
        );

        db.execSQL(" CREATE TABLE " + TABLE_RDV + " (" +
                COLUMN_RDV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_RDV_NOM + " TEXT NOT NULL, " +
                COLUMN_RDV_HEURE + " TEXT NOT NULL, " +
                COLUMN_RDV_DATE + " TEXT NOT NULL, " +
                COLUMN_RDV_IMAGE + " TEXT NOT NULL) ;"
        );


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RDV);

        this.onCreate(db);
    }
    /**create record**/
    public void saveNewPatient(Patient patient) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PATIENT_NAME, patient.getName());
        values.put(COLUMN_PATIENT_AGE, patient.getAge());
        values.put(COLUMN_PATIENT_PHONE, patient.getPhone());
        values.put(COLUMN_PATIENT_ADDRESS, patient.getAddress());
        values.put(COLUMN_PATIENT_IMAGE, patient.getImage());

        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    public void saveNewRdv(RendezVous rdv) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RDV_NOM, rdv.getNom());
        values.put(COLUMN_RDV_HEURE, rdv.getDate());
        values.put(COLUMN_RDV_DATE, rdv.getHeure());
        values.put(COLUMN_RDV_IMAGE, rdv.getImage());

        // insert
        db.insert(TABLE_RDV,null, values);
        db.close();
    }

    /**Query records, give options to filter results**/
    public List<Patient> peopleList(String filter) {
        String query;
        if(filter.equals("")){
            //regular query
            query = "SELECT  * FROM " + TABLE_NAME;
        }else{
            //filter results by filter option provided
            query = "SELECT  * FROM " + TABLE_NAME + " ORDER BY "+ filter;
        }

        List<Patient> patientLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Patient patient;

        if (cursor.moveToFirst()) {
            do {
                patient = new Patient();

                patient.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                patient.setName(cursor.getString(cursor.getColumnIndex(COLUMN_PATIENT_NAME)));
                patient.setAge(cursor.getString(cursor.getColumnIndex(COLUMN_PATIENT_AGE)));
                patient.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_PATIENT_PHONE)));
                patient.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_PATIENT_ADDRESS)));
                patient.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_PATIENT_IMAGE)));
                patientLinkedList.add(patient);
            } while (cursor.moveToNext());
        }
        return patientLinkedList;
    }

    public List<RendezVous> peopleRdvList(String filter) {
        String query;
        if(filter.equals("")){
            //regular query
            query = "SELECT  * FROM " + TABLE_RDV;
        }else{
            //filter results by filter option provided
            query = "SELECT  * FROM " + TABLE_RDV + " ORDER BY "+ filter;
        }

        List<RendezVous> rdvLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery(query, null);
        RendezVous rdv;

        if (data.moveToFirst()) {
            do {
                rdv = new RendezVous();

                rdv.setId(data.getLong(data.getColumnIndex(COLUMN_RDV_ID)));
                rdv.setNom(data.getString(data.getColumnIndex(COLUMN_RDV_NOM)));
                rdv.setDate(data.getString(data.getColumnIndex(COLUMN_RDV_DATE)));
                rdv.setHeure(data.getString(data.getColumnIndex(COLUMN_RDV_HEURE)));
                rdv.setImage(data.getString(data.getColumnIndex(COLUMN_RDV_IMAGE)));
                rdvLinkedList.add(rdv);
            } while (data.moveToNext());
        }
        return rdvLinkedList;
    }

    /**Query only 1 record**/
    public Patient getPatient(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE _id="+ id;
        Cursor cursor = db.rawQuery(query, null);

        Patient receivedPatient = new Patient();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            receivedPatient.setName(cursor.getString(cursor.getColumnIndex(COLUMN_PATIENT_NAME)));
            receivedPatient.setAge(cursor.getString(cursor.getColumnIndex(COLUMN_PATIENT_AGE)));
            receivedPatient.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_PATIENT_PHONE)));
            receivedPatient.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_PATIENT_ADDRESS)));
            receivedPatient.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_PATIENT_IMAGE)));
        }
        return receivedPatient;
    }

    public List<String> getImageFromDBSQLite(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> tabImage = new ArrayList<>();
        String query = "SELECT  image FROM " + TABLE_NAME ;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                tabImage.add(cursor.getString(cursor.getColumnIndex(COLUMN_PATIENT_IMAGE)));
            }while (cursor.moveToNext());
        }
        return tabImage ;
    }

    public RendezVous getRdv(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_RDV + " WHERE _id="+ id;
        Cursor cursor = db.rawQuery(query, null);

        RendezVous receivedRdv = new RendezVous();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            receivedRdv.setNom(cursor.getString(cursor.getColumnIndex(COLUMN_RDV_NOM)));
            receivedRdv.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_RDV_DATE)));
            receivedRdv.setHeure(cursor.getString(cursor.getColumnIndex(COLUMN_RDV_HEURE)));
            receivedRdv.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_RDV_IMAGE)));
        }
        return receivedRdv;
    }


    /**delete record**/
    public void deletePatientRecord(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE _id='"+id+"'");
        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();

    }

    public void deleteRdvRecord(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_RDV+" WHERE _id='"+id+"'");
        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();

    }

    /**update record**/
    public void updatePatientRecord(long patientId, Context context, Patient updatedpatient) {
        SQLiteDatabase db = this.getWritableDatabase();
        //you can use the constants above instead of typing the column names
        db.execSQL("UPDATE  "+TABLE_NAME+" SET name ='"+ updatedpatient.getName() + "', age ='" + updatedpatient.getAge()+ "', phone ='"+ updatedpatient.getPhone() + "', address ='"+ updatedpatient.getAddress() + "',image ='"+ updatedpatient.getImage() + "'  WHERE _id='" + patientId + "'");
        Toast.makeText(context, "Updated successfully.", Toast.LENGTH_SHORT).show();

    }
}