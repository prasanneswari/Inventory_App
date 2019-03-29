package com.example.shekinah.inventory;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FamilyAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] id;
    private String[] memName;
    private String[] relation;
    private String[] DOBf;
    public static String eID;
    public static String fName;
    public static String fRelation;
    public static String fDOB;
    TextView prdIdT,memberNameT,relationshipT,DOBFT;

    public FamilyAdapter(Context context, String[] idS, String[] empNameS, String[] empDeptS, String[] jobRoleS) {

        super(context, R.layout.activity_employees, empNameS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter

        this.context = context;
        this.id = idS;
        this.memName = empNameS;
        this.relation = empDeptS;
        this.DOBf = jobRoleS;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_family_adapter, parent, false);  //Setting content view of xml
        // ImageView imageView=(ImageView)rowView.findViewById(R.id.image);

        //Assigning IDs from xml
        memberNameT = (TextView) rowView.findViewById(R.id.memberName);
        relationshipT = (TextView) rowView.findViewById(R.id.relationship);
        DOBFT = (TextView) rowView.findViewById(R.id.DOBF);

        try {
            //Assigning values from array to individual layouts in list view
            memberNameT.setText(memName[position]);
            relationshipT.setText(relation[position]);
            DOBFT.setText(DOBf[position]);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),UpdateEmp.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                eID = id[position];
                fName = memName[position];
                fRelation = relation[position];
                fDOB = DOBf[position];
                Log.d("Employee" ," Id :" + eID);
                Log.d("Family" ," Name :" + fName);
                Log.d("Family" ," Relation :" + fRelation);
                Log.d("Family" ," DOB :" + fDOB);
            }
        });
        return rowView;
    }
}
