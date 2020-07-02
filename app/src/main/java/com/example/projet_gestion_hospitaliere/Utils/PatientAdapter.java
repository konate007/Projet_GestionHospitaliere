package com.example.projet_gestion_hospitaliere.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projet_gestion_hospitaliere.R;
import com.example.projet_gestion_hospitaliere.UpdateRecordActivity;
import com.example.projet_gestion_hospitaliere.model.Patient;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.ViewHolder> {
    private List<Patient> mPeopleList;
    private Context mContext;
    private RecyclerView mRecyclerV;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView patientNameTxtV;
        public TextView patientAgeTxtV;
        public TextView patientPhoneTxtV;
        public TextView patientAddressTxtV;
        public ImageView patientImageImgV;


        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            patientNameTxtV = v.findViewById(R.id.name);
            patientAgeTxtV = v.findViewById(R.id.age);
            patientPhoneTxtV = v.findViewById(R.id.phone);
            patientAddressTxtV = v.findViewById(R.id.address);
            patientImageImgV = v.findViewById(R.id.image);
        }
    }

    public void add(int position, Patient patient) {
        mPeopleList.add(position, patient);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mPeopleList.remove(position);
        notifyItemRemoved(position);
    }



    // Provide a suitable constructor (depends on the kind of dataset)
    public PatientAdapter(List<Patient> myDataset, Context context, RecyclerView recyclerView) {
        mPeopleList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PatientAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.single_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final Patient patient = mPeopleList.get(position);
        holder.patientNameTxtV.setText("Name: " + patient.getName());
        holder.patientAgeTxtV.setText("Age: " + patient.getAge());
        holder.patientPhoneTxtV.setText("Phone: " + patient.getPhone());
        holder.patientAddressTxtV.setText("Address: " + patient.getAddress());
        Picasso.with(mContext).load(patient.getImage()).placeholder(R.mipmap.ic_launcher).into(holder.patientImageImgV);

        //listen to single view layout click
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Choose option");
                builder.setMessage("Update or delete patient?");
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //go to update activity
                        goToUpdateActivity(patient.getId());

                    }
                });
                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PatientDBHelper dbHelper = new PatientDBHelper(mContext);
                        dbHelper.deletePatientRecord(patient.getId(), mContext);

                        mPeopleList.remove(position);
                        mRecyclerV.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mPeopleList.size());
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

    }

    private void goToUpdateActivity(long patientId){
        Intent goToUpdate = new Intent(mContext, UpdateRecordActivity.class);
    goToUpdate.putExtra("PATIENT_ID", patientId);
        mContext.startActivity(goToUpdate);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mPeopleList.size();
    }

}