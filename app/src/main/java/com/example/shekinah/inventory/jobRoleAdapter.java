package com.example.shekinah.inventory;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.shekinah.inventory.register.selectedJobRoles;

/**
 * Created by Shekinah.. on 29/01/2018.
 */

public class  jobRoleAdapter extends ArrayAdapter<String> {
    /*private Context mContext;
    private ArrayList<register.StateVO> listState;
    private jobRoleAdapter myAdapter;
    private boolean isFromView = false;

    public jobRoleAdapter(Context context, int resource, List<register.StateVO> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.listState = (ArrayList<register.StateVO>) objects;
        this.myAdapter = this;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView,
                              ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.spinner_jobrole, null);
            holder = new ViewHolder();
            holder.mTextView = (TextView) convertView
                    .findViewById(R.id.text);
            holder.mCheckBox = (CheckBox) convertView
                    .findViewById(R.id.checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTextView.setText(listState.get(position).getTitle());

        // To check weather checked event fire from getview() or user input
        isFromView = true;
        holder.mCheckBox.setChecked(listState.get(position).isSelected());
        isFromView = false;

        if ((position == 0)) {
            holder.mCheckBox.setVisibility(View.INVISIBLE);
        } else {
            holder.mCheckBox.setVisibility(View.VISIBLE);
        }
        holder.mCheckBox.setTag(position);
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getPosition = (Integer) buttonView.getTag();


            }
        });
        return convertView;
    }

    private class ViewHolder {
        private TextView mTextView;
        private CheckBox mCheckBox;
    }
}

*/
    private Context context;
    private String[] id;
    private String[] NSName;
    String[] checked;
    public static String sID, sName;
    TextView NSIdT,NSNameT;
    CheckBox checkBox;

public jobRoleAdapter(Context context, String[] NSIdS, String[] NSNameS) {

        super(context, R.layout.activity_register, NSNameS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter
        this.context = context;
        this.id = NSIdS;
        this.NSName = NSNameS;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.spinner_jobrole, parent, false);  //Setting content view of xml
        //Assigning IDs from xml
        NSNameT = (TextView) rowView.findViewById(R.id.text);

        checkBox = (CheckBox) rowView.findViewById(R.id.checkbox);

        try {

            //Assigning values from array to individual layouts in list view
            NSNameT.setText(NSName[position]);



        } catch (NullPointerException e) {
            e.printStackTrace();
        }

       /* checkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(checkBox.isChecked()){
                    //System.out.println("Checked...");
                    Log.d("checked box... " ," Id :" + id[position]);
                    Log.d("checked box... " ," Name :" + NSName[position]);
                }else{
                    //System.out.println("Un-Checked...");
                    Log.d("un-checked box... " ," Id :" + id[position]);
                    Log.d("un-checked box... " ," Name :" + NSName[position]);
                }

            }
        });*/


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                               @Override
                                               public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                                                   String pos = id[position];


                                                   if(isChecked){
                                                       //System.out.println("Checked...");
                                                       Log.d("checked box... " ," Id :" + id[position]);
                                                       Log.d("checked box... " ," Name :" + NSName[position]);

                                                       selectedJobRoles.add(pos);
                                                   }else if (!isChecked){
                                                       //System.out.println("Un-Checked...");
                                                       Log.d("un-checked box... " ," Id :" + id[position]);
                                                       Log.d("un-checked box... " ," Name :" + NSName[position]);
                                                       for(int i=0;i<selectedJobRoles.size();i++){
                                                           if (selectedJobRoles.get(i).contentEquals(id[position])){
                                                               selectedJobRoles.remove(i);
                                                           }
                                                       }

                                                   }
                                               }
                                           }
        );

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), updateStatus.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(intent);
                sID = id[position];
                sName = NSName[position];


                Log.d("selected Status " ," Id :" + sID);
                Log.d("selected Status " ," Name :" + sName);



            }
        });
        return rowView;
    }
}
