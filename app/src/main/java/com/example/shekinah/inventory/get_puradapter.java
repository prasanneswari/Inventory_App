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

import static com.example.shekinah.inventory.ordered_reqvi.check_select1;
import static com.example.shekinah.inventory.ordered_reqvi.contactList2;
import static com.example.shekinah.inventory.ordered_reqvi.ednw1;
import static com.example.shekinah.inventory.ordered_reqvi.need_send1;

/**
 * Created by vinay on 29-03-2018.
 */

public class get_puradapter  extends ArrayAdapter<String> {
    private Context context;
    int pos_get = 0;
    private List<String> values;
    TextView reqsted_qnty;
    TextView purchased_qnty;
    TextView Ordered_qnty;
    TextView bal;
    TextView recived;
    TextView prsnid;

    TextView prsname;

    TextView qntity_return;

    TextView status;

    public get_puradapter(Context context, List<String> array) {
        super(context, R.layout.activity_purchase_admin, array);
        this.context = context;
        this.values = array;
    }

    @NonNull
    @Override
    public View getView(final int positionv, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.get_pur_adapter, parent, false);
        prsnid = (TextView) rowView.findViewById(R.id.pid);
        prsname = (TextView) rowView.findViewById(R.id.pnamid);
        reqsted_qnty = (TextView) rowView.findViewById(R.id.qntakeid);
        qntity_return = (TextView) rowView.findViewById(R.id.qntyreturnid);

        status = (TextView) rowView.findViewById(R.id.statusid);
        purchased_qnty = (TextView) rowView.findViewById(R.id.prqntid);
        Ordered_qnty = (TextView) rowView.findViewById(R.id.ordrdqid);
        bal = (TextView) rowView.findViewById(R.id.qntybalid);
        recived = (TextView) rowView.findViewById(R.id.dtrecid);
        final CheckBox chk = (CheckBox) rowView.findViewById(R.id.chid);
        try {
            prsnid.setText(contactList2.get(positionv).get("purchasedetailsid"));
            prsname.setText(contactList2.get(positionv).get("productname"));
            reqsted_qnty.setText(contactList2.get(positionv).get("quantityrequested"));
            status.setText(contactList2.get(positionv).get("status"));
            Ordered_qnty.setText(contactList2.get(positionv).get("quantityordered"));
            purchased_qnty.setText(contactList2.get(positionv).get("purchasedetailsid"));
            recived.setText(ednw1.get(positionv));
            bal.setText(need_send1.get(positionv));
            //  qntity_return.setText(ednw1.get(positionv));
            if (check_select1.get(positionv)) {
                chk.setChecked(true);
            } else {
                chk.setChecked(false);
            }

            recived.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pos_get = positionv;
                    final String[] finalva = {""};
                    final Dialog openDialog = new Dialog(context, R.style.DialogTheme);

                    openDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    //dialog.setTitle("please enter room names");
                    openDialog.setContentView(R.layout.procurment_dialog);
                    openDialog.setCanceledOnTouchOutside(false);
                    final TextView quantity_take = (TextView) openDialog.findViewById(R.id.qntyid);
                    final TextView reqvstdtm = (TextView) openDialog.findViewById(R.id.rqdtid);


                    final TextView producnm = (TextView) openDialog.findViewById(R.id.qposidid);
                    final TextView quantity_return = (TextView) openDialog.findViewById(R.id.qtrnid);
                    final TextView quantity_bal = (TextView) openDialog.findViewById(R.id.qntbalid);
                    Button incrz = (Button) openDialog.findViewById(R.id.incrid);
                    Button decrz = (Button) openDialog.findViewById(R.id.decreid);
                    Button svbtn = (Button) openDialog.findViewById(R.id.saveid);
                    Button cancelbtn = (Button) openDialog.findViewById(R.id.canclid);

                    // {"pid", "pname","qtake","qreturn","qtybal"}

     /*   contact.put("qtake", String.valueOf(10));
        ednw.add(String.valueOf(x));
        // recive_qty.add();
        need_send.add(String.valueOf(10 - x));
        contactList.add(contact);*/

                    System.out.println(contactList2.get(pos_get).get("quantityordered"));

                    reqvstdtm.setText(contactList2.get(pos_get).get("requesteddate"));

                    producnm.setText(contactList2.get(pos_get).get("productname"));

                    quantity_take.setText(contactList2.get(pos_get).get("quantityordered"));
                    quantity_return.setText(ednw1.get(pos_get));
                    quantity_bal.setText(need_send1.get(pos_get));

                    final int[] k = {Integer.parseInt(ednw1.get(pos_get))};
                    final int[] i = {Integer.parseInt(need_send1.get(pos_get))};
                    final int j = Integer.parseInt(contactList2.get(pos_get).get("quantityordered"));

                    incrz.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            // TODO Auto-generated method stub
                            if (i[0] != 0 && i[0] >= 0) {

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
                            if (k[0] >= 0 && i[0] < j) {
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
                            ednw1.set(pos_get, quantity_return.getText().toString());
                            need_send1.set(pos_get, quantity_bal.getText().toString());
                            check_select1.set(positionv, true);
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
                                               public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                   if (isChecked)
                                                       check_select1.set(positionv, true);
                                                   else
                                                       check_select1 .set(positionv, false);
                                                   notifyDataSetChanged();
                                               }
                                           }
            );
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return rowView;
    }
}