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
import android.widget.CheckBox;
import android.widget.TextView;

public class EmployeesAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] id;
    private String[] empName;
    private String[] empDept;
    private String[] jobRole;
    public static String eID;
    public static String eName;
    public static String eDept;
    public static String eRole;
    TextView prdIdT,empNameT,empDeptT,jobRoleT;

    public EmployeesAdapter(Context context, String[] idS, String[] empNameS, String[] empDeptS, String[] jobRoleS) {

        super(context, R.layout.activity_employees, empNameS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter

        this.context = context;
        this.id = idS;
        this.empName = empNameS;
        this.empDept = empDeptS;
        this.jobRole = jobRoleS;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_employees_adapter, parent, false);  //Setting content view of xml
        // ImageView imageView=(ImageView)rowView.findViewById(R.id.image);

        //Assigning IDs from xml
        empNameT = (TextView) rowView.findViewById(R.id.empName);
        empDeptT = (TextView) rowView.findViewById(R.id.empDept);
        jobRoleT = (TextView) rowView.findViewById(R.id.jobRole);

        try {
            //Assigning values from array to individual layouts in list view
            empNameT.setText(empName[position]);
            empDeptT.setText(empDept[position]);
            jobRoleT.setText(jobRole[position]);

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
                eName = empName[position];
                eDept = empDept[position];
                eRole = jobRole[position];
                Log.d("Employee" ," Id :" + eID);
                Log.d("Employee" ," Name :" + eName);
                Log.d("Employee" ," Dept :" + eDept);
                Log.d("Employee" ," Job Role :" + eRole);
            }
        });
        return rowView;
    }
}
