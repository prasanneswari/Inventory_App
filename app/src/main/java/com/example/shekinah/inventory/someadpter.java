package com.example.shekinah.inventory;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import static com.example.shekinah.inventory.purchase_admin.check_select;
import static com.example.shekinah.inventory.purchase_admin.contactList;
import static com.example.shekinah.inventory.purchase_admin.ednw;
import static com.example.shekinah.inventory.purchase_admin.need_send;

/**
 * Created by vinay on 29-03-2018.
 */
public class someadpter extends ArrayAdapter<String> {
    private Context context;
    int pos_get=0;
    private List<String> values;
    TextView qntity_take;
    TextView prsnid;
    TextView prsname;
    TextView qntity_return;
    TextView qntity_balanc;
    TextView status;
    public someadpter(Context context, List<String> array)
    {
        super(context, R.layout.activity_purchase_admin, array);
        this.context = context;
        this.values = array;
    }
    @NonNull
    @Override
    public View getView(final int positionv, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.purchaseadmin_adptr, parent, false);
        prsnid = (TextView) rowView.findViewById(R.id.pid);
        prsname = (TextView) rowView.findViewById(R.id.pnamid);
        qntity_take = (TextView) rowView.findViewById(R.id.qntakeid);
        qntity_return = (TextView) rowView.findViewById(R.id.qntyreturnid);
        qntity_balanc = (TextView) rowView.findViewById(R.id.qntybalid);
        status = (TextView) rowView.findViewById(R.id.statusid);

        final CheckBox chk = (CheckBox) rowView.findViewById(R.id.chid);
        try {

            prsnid.setText(contactList.get(positionv).get("purchaseorderid"));
            prsname.setText(contactList.get(positionv).get("productname"));
            qntity_take.setText(contactList.get(positionv).get("quantityrequested"));
            status.setText(contactList.get(positionv).get("status"));
            qntity_return.setText(ednw.get(positionv));
            qntity_balanc.setText(need_send.get(positionv));
            if(check_select.get(positionv)){

                chk.setChecked(true);

            }
            else{

                chk.setChecked(false);

            }
            qntity_return.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pos_get=positionv;
                    final String[] finalva = {""};
                    final Dialog openDialog = new Dialog(context,R.style.DialogTheme);

                    openDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    //dialog.setTitle("please enter room names");
                    openDialog.setContentView(R.layout.purchese_editqnty);
                    openDialog.setCanceledOnTouchOutside(false);

                    final TextView quantity_take =(TextView) openDialog.findViewById(R.id.qntyid);
                    final TextView quantity_return =(TextView) openDialog.findViewById(R.id.qtrnid);
                    final TextView quantity_bal =(TextView) openDialog.findViewById(R.id.qntbalid);
                    Button incrz =(Button)openDialog.findViewById(R.id.incrid);
                    Button decrz =(Button)openDialog.findViewById(R.id.decreid);
                    Button svbtn =(Button)openDialog.findViewById(R.id.saveid);
                    Button cancelbtn =(Button)openDialog.findViewById(R.id.canclid);

                    // {"pid", "pname","qtake","qreturn","qtybal"}

     /*   contact.put("qtake", String.valueOf(10));
        ednw.add(String.valueOf(x));
        // recive_qty.add();
        need_send.add(String.valueOf(10 - x));
        contactList.add(contact);*/

                    System.out.println(contactList.get(pos_get).get("quantityrequested"));
                    quantity_take.setText(contactList.get(pos_get).get("quantityrequested"));
                    quantity_return.setText(ednw.get(pos_get));
                    quantity_bal.setText(need_send.get(pos_get));

                    final int[] k = {Integer.parseInt(ednw.get(pos_get))};
                    final int[] i = {Integer.parseInt(need_send.get(pos_get))};
                    final int j = Integer.parseInt(contactList.get(pos_get).get("quantityrequested"));

                    incrz.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            // TODO Auto-generated method stub
                            if(i[0] !=0&&i[0]>=0) {

                                --i[0];
                                ++k[0];

                                quantity_bal.setText(String.valueOf(i[0]));
                                quantity_return.setText(String.valueOf(k[0]));
//                    finalva[0] =String.valueOf(i[0]);
                            }
                            // openDialog.dismiss();
                        }
                        // Toast.makeText(getApplicationContext(), "please fill all details", Toast.LENGTH_LONG).show();

                    });
                    openDialog.show();
                    decrz.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            if(k[0]>=0&&i[0]<j) {
                                ++i[0];
                                --k[0];
                                quantity_return.setText(String.valueOf(k[0]));
                                quantity_bal.setText(String.valueOf(i[0]));

//                    finalva[0] =String.valueOf(i[0]);
                            }

                            //openDialog.dismiss();
                        }
                        // Toast.makeText(getApplicationContext(), "please fill all details", Toast.LENGTH_LONG).show();
                    });
                    openDialog.show();

                    svbtn.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            ednw.set(pos_get,quantity_return.getText().toString());
                            need_send.set(pos_get,quantity_bal.getText().toString());
                            check_select.set(positionv,true);
                            notifyDataSetChanged();
               /* String json= returnedit_jsonstr(contactList.get(poslist).get("quantityissued"),finalva[0]);
                Toast.makeText(getApplicationContext(), json, Toast.LENGTH_LONG).show();*/
                            openDialog.dismiss();
                        }

                        // Toast.makeText(getApplicationContext(), "please fill all details", Toast.LENGTH_LONG).show();

                    });
                    openDialog.show();
                    cancelbtn.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub

                            openDialog.dismiss();

                            //  Toast.makeText(getApplicationContext(), spinner_item + " - " + edittext1.getText().toString().trim(), Toast.LENGTH_LONG).show();
                        }
                    });
                    openDialog.show();
                }
            });
           /* qntity_return.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                  //  ednw.set(positionv,dynedtxt.getText().toString());

                   // values

                   // notifyDataSetChanged();
                   *//* qntity_balanc.setText(need_send.get(positionv));
                    qntity_return.setText(ednw.get(positionv));*//*
                    int h= Integer.parseInt(contactList.get(positionv).get("qtake"));
                    int j= Integer.parseInt(ednw.get(positionv));
                    int k=0;
                    String enteredString  = s.toString();
                    if(enteredString.length() > 0 & enteredString != "")
                    {

                        int input = Integer.parseInt(enteredString);
                        if(input > h)
                        {
                            Toast.makeText(context, "Issue cannot exceed requested quantity", Toast.LENGTH_SHORT).show();
                        }
                        else if(input < j)
                        {

                            Toast.makeText(context, "Issue cannot be reduced", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            k=h-j;
                            ednw.set(positionv, String.valueOf(j));
                                need_send.set(positionv, String.valueOf(k));
                            Log.d("need_send", String.valueOf(need_send));
                               qntity_balanc.setText(String.valueOf(k));
                        }
                        *//*need_send.set(positionv, String.valueOf(h-j));
                        Log.d("need_send", String.valueOf(need_send));
                        qntity_balanc.setText(String.valueOf(h-j));

                        ednw.set(positionv, qntity_return.getText().toString());*//*
                    }
                }
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }
            });*/
            chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                               @Override
                                               public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                   if(isChecked)
                                                       check_select.set(positionv,true);
                                                   else
                                                       check_select.set(positionv,false);
                                                   notifyDataSetChanged();
                                               }
                                           }
            );
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        return rowView;
    }
    public void add_espdtdialog()
    {

    }
}