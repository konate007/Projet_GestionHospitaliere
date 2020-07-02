package com.example.projet_gestion_hospitaliere;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.projet_gestion_hospitaliere.Utils.PatientDBHelper;
import com.example.projet_gestion_hospitaliere.model.Patient;

public class AddRecordActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mAgeEditText;
    private EditText mPhoneEditText;
    private EditText mAddressEditText;
    private EditText mImageEditText;
    private Button mAddBtn;
    private PatientDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        //init
        mNameEditText = findViewById(R.id.patientName);
        mAgeEditText = findViewById(R.id.patientAge);
        mPhoneEditText = findViewById(R.id.patientPhone);
        mAddressEditText = findViewById(R.id.patientAddress);
        mImageEditText = findViewById(R.id.patientProfileImageLink);
        mAddBtn = findViewById(R.id.addNewPatientButton);

        //listen to add button click
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the save person method
                savePatient();
            }
        });
    }

    private void savePatient(){
        String name = mNameEditText.getText().toString().trim();
        String age = mAgeEditText.getText().toString().trim();
        String phone = mPhoneEditText.getText().toString().trim();
        String address = mAddressEditText.getText().toString().trim();
        String image = mImageEditText.getText().toString().trim();
        dbHelper = new PatientDBHelper(this);

        if(name.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter a name", Toast.LENGTH_SHORT).show();
        }

        if(age.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter an age", Toast.LENGTH_SHORT).show();
        }

        if(phone.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter an phone", Toast.LENGTH_SHORT).show();
        }
        if(address.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter an address", Toast.LENGTH_SHORT).show();
        }

        if(image.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter an image link", Toast.LENGTH_SHORT).show();
        }
        //create new person
        Patient patient = new Patient(name, age, phone, address, image);
        dbHelper.saveNewPatient(patient);

        //finally redirect back home
        // NOTE you can implement an sqlite callback then redirect on success delete
        goBackHome();

    }

    private void goBackHome(){
        startActivity(new Intent(AddRecordActivity.this, ListViewPatient.class));
    }
}