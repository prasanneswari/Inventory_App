package com.example.shekinah.inventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.shekinah.inventory.PurchasePending.adapter;
import static com.example.shekinah.inventory.PurchasePending.pendingDict;
import static com.example.shekinah.inventory.PurchasePending.pending_listview;


/**
 * Created by JoshuaNitesh on 28/02/18.
 */

public class PendingAdapter extends BaseAdapter{

    private Context pContext;
    private LayoutInflater pInflater;
    private ArrayList<PendingDict> pDataSource;

    public PendingAdapter(Context pContext, ArrayList<PendingDict> pDataSource) {
        this.pContext = pContext;
        this.pInflater = (LayoutInflater) pContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.pDataSource = pDataSource;
    }

    @Override
    public int getCount() {
        return pDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return pDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        final PendingDict pending = (PendingDict) getItem(position);
        final View rowView = pInflater.inflate(R.layout.pending_list_view, viewGroup, false);

        TextView productIdText = (TextView) rowView.findViewById(R.id.P_id);
        productIdText.setText(pending.ProductId);

        TextView productNameText = (TextView) rowView.findViewById(R.id.P_product);
        productNameText.setText(pending.ProductName);

        TextView productCurrText = (TextView) rowView.findViewById(R.id.P_currQty);
        productCurrText.setText(pending.ProductCurrQty);

        TextView productThreshText = (TextView) rowView.findViewById(R.id.P_thresh);
        productThreshText.setText(pending.ProductThresh);

        final TextView qtyText = (TextView) rowView.findViewById(R.id.P_qty);
        qtyText.setText(pending.Quantity);

//        rowView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Changed the parameters(Added)
//                updateQuantity(position,pending.ProductId,pending.ProductName,pending.ProductCurrQty,pending.ProductThresh,pending.ProductPos,pending.Quantity);
//            }
//        });

        return rowView;
    }

    public void updateList() //TOO MUCH PROCESSING
    {
        adapter = new PendingAdapter(pContext,pendingDict);
        pending_listview.setAdapter(adapter);
    }

}




/*

        final CheckBox pCheckBox = (CheckBox) rowView.findViewById(R.id.P_checkbox);
        if(pending.checked) {
            pCheckBox.setChecked(true);
        }
        else {
            pCheckBox.setChecked(false);
        }



                pCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    PendingDict c =pendingDict.get(position);
                    pendingDict.set(position,new PendingDict(true,c.ProductId,c.ProductName,c.ProductPos,c.ProductThresh,c.ProductCurrQty,""));
                }
                else {
                    PendingDict c =pendingDict.get(position);
                    pendingDict.set(position,new PendingDict(false,c.ProductId,c.ProductName,c.ProductPos,c.ProductThresh,c.ProductCurrQty,""));
                }
            }
        });

 */

