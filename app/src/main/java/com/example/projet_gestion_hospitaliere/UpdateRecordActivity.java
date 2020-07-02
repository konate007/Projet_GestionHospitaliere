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

public class UpdateRecordActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mAgeEditText;
    private EditText mPhoneEditText;
    private EditText mAddressEditText;
    private EditText mImageEditText;
    private Button mUpdateBtn;

    private PatientDBHelper dbHelper;
    private long receivedPatientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_record);

        //init
        mNameEditText = findViewById(R.id.patientNameUpdate);
        mAgeEditText = findViewById(R.id.patientAgeUpdate);
        mPhoneEditText = findViewById(R.id.patientPhoneUpdate);
        mAddressEditText = findViewById(R.id.patientAddressUpdate);
        mImageEditText = findViewById(R.id.patientProfileImageLinkUpdate);
        mUpdateBtn = findViewById(R.id.updatePatientButton);

        dbHelper = new PatientDBHelper(this);

        try {
            //get intent to get person id
            receivedPatientId = getIntent().getLongExtra("PATIENT_ID", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /***populate user data before update***/
        Patient queriedPatient = dbHelper.getPatient(receivedPatientId);
        //set field to this patient data
        mNameEditText.setText(queriedPatient.getName());
        mAgeEditText.setText(queriedPatient.getAge());
        mPhoneEditText.setText(queriedPatient.getPhone());
        mAddressEditText.setText(queriedPatient.getAddress());
        mImageEditText.setText(queriedPatient.getImage());



        //listen to add button click to update
        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the save person method
                updatePatient();
            }
        });
    }

    private void updatePatient(){
        String name = mNameEditText.getText().toString().trim();
        String age = mAgeEditText.getText().toString().trim();
        String phone = mPhoneEditText.getText().toString().trim();
        String address = mAddressEditText.getText().toString().trim();
        String image = mImageEditText.getText().toString().trim();


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

        //create updated person
        Patient updatedPatient = new Patient(name, age, phone, address, image);

        //call dbhelper update
        dbHelper.updatePatientRecord(receivedPatientId, this, updatedPatient);

        //finally redirect back home
        // NOTE you can implement an sqlite callback then redirect on success delete
        goBackHome();

    }

    private void goBackHome(){
        startActivity(new Intent(this, ListViewPatient.class));
    }
}