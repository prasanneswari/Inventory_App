package com.example.shekinah.inventory;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.shekinah.inventory.OrderDetails.dAdapter;
import static com.example.shekinah.inventory.OrderDetails.detailsDict;
import static com.example.shekinah.inventory.OrderDetails.details_listview;

/**
 * Created by JoshuaNitesh on 06/03/18.
 */

public class DetailsAdapter extends BaseAdapter {

    private Context dContext;
    private LayoutInflater dInflater;
    private ArrayList<DetailsDict> dDataSource;

    public DetailsAdapter(Context dContext, ArrayList<DetailsDict> dDataSource) {
        this.dContext = dContext;
        this.dInflater = (LayoutInflater) dContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.dDataSource = dDataSource;
    }



    public int getCount() {
        return dDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return dDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        final DetailsDict details = (DetailsDict) getItem(position);
        final View rowView = dInflater.inflate(R.layout.details_list_view, viewGroup, false);

        final CheckBox dCheckBox = (CheckBox) rowView.findViewById(R.id.D_checkbox);
        if(details.Checked) {
            dCheckBox.setChecked(true);
        }
        else {
            dCheckBox.setChecked(false);
        }

        TextView dNameText = (TextView) rowView.findViewById(R.id.D_name);
        dNameText.setText(details.ProductName);

        TextView dDescText = (TextView) rowView.findViewById(R.id.D_desc);
        dDescText.setText(details.ProductDesc);

        final TextView qtyText = (TextView) rowView.findViewById(R.id.D_qty);
        qtyText.setText(details.QtyRequested);

        dCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true)
                {
                    updateQuantity(position,details.QtyRequested);
                }
                else {
                    DetailsDict c =detailsDict.get(position);
                    detailsDict.set(position,new DetailsDict(false,details.ProductId,details.ProductName,details.ProductDesc,details.QtyRequested,details.Status));
                }
            }
        });

        return rowView;
    }


    public void updateQuantity(final int pos,final String qtyVal)
    {
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(dContext);
        View promptsView = li.inflate(R.layout.quantity_prompt_2, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(dContext);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText quantityInput = (EditText) promptsView.findViewById(R.id.prompt_req_qty_input);
        quantityInput.setText(qtyVal);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                String newQty = quantityInput.getText().toString().trim();
                                DetailsDict c = detailsDict.get(pos);
                                detailsDict.set(pos,new DetailsDict(true,c.ProductId,c.ProductName,c.ProductDesc,newQty,c.Status));
                                updateList();
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                DetailsDict c =detailsDict.get(pos);
                                detailsDict.set(pos,new DetailsDict(false,c.ProductId,c.ProductName,c.ProductDesc,qtyVal,c.Status));
                                updateList();
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();

        quantityInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });

        // show it
        alertDialog.show();
    }

    public void updateList()
    {
        dAdapter = new DetailsAdapter(dContext,detailsDict);
        details_listview.setAdapter(dAdapter);
    }

}
