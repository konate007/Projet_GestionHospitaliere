package com.example.projet_gestion_hospitaliere;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projet_gestion_hospitaliere.Utils.PatientDBHelper;
import com.example.projet_gestion_hospitaliere.model.RendezVous;

public class AddRdvRecordActivity extends AppCompatActivity {

    private EditText mNomEditText;
    private EditText mDateEditText;
    private EditText mHeureEditText;
    private Spinner spinner ;
    RendezVous rendezVous ;
    private Button mAddBtn;
    private PatientDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rdv_record);

        //init
        mNomEditText = findViewById(R.id.rdvNom);
        mDateEditText = findViewById(R.id.rdvDate);
        mHeureEditText = findViewById(R.id.rdvHeure);
        spinner = findViewById(R.id.spinner);
        mAddBtn = findViewById(R.id.addNewRdvButton);
        dbHelper = new PatientDBHelper(this);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dbHelper.getImageFromDBSQLite()));

        //listen to add button click
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the save person method
                saveRdv();
            }
        });
    }

    private void saveRdv(){
        String nom = mNomEditText.getText().toString().trim();
        String date = mDateEditText.getText().toString().trim();
        String heure = mHeureEditText.getText().toString().trim();

        dbHelper = new PatientDBHelper(this);

        if(nom.isEmpty() ||date.isEmpty() || heure.isEmpty() || spinner.getSelectedItem().toString().isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must fill all fields", Toast.LENGTH_SHORT).show();
        }else {
            //create new person
            RendezVous rdv = new RendezVous(nom, date, heure, spinner.getSelectedItem().toString());
            dbHelper.saveNewRdv(rdv);

            //finally redirect back home
            // NOTE you can implement an sqlite callback then redirect on success delete
            goBackHome();
        }

    }

    private void goBackHome(){
        startActivity(new Intent(AddRdvRecordActivity.this, ListViewNote.class));
    }
}