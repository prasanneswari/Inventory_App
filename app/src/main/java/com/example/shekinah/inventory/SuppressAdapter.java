package com.example.shekinah.inventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by JoshuaNitesh on 26/03/18.
 */

public class SuppressAdapter extends BaseAdapter{

    private Context sContext;
    private LayoutInflater sInflater;
    private ArrayList<SuppressDict> sDataSource;

    public SuppressAdapter(Context pContext, ArrayList<SuppressDict> sDataSource) {
        this.sContext = pContext;
        this.sInflater = (LayoutInflater) pContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.sDataSource = sDataSource;
    }

    @Override
    public int getCount() {
        return sDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return sDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

//        final PendingDict pending = (PendingDict) getItem(position);
//        final View rowView = pInflater.inflate(R.layout.suppress_list_view, viewGroup, false);
//
//        TextView productIdText = (TextView) rowView.findViewById(R.id.P_id);
//        productIdText.setText(pending.ProductId);
//
//        TextView productNameText = (TextView) rowView.findViewById(R.id.P_product);
//        productNameText.setText(pending.ProductName);
//
//        TextView productCurrText = (TextView) rowView.findViewById(R.id.P_currQty);
//        productCurrText.setText(pending.ProductCurrQty);
//
//        TextView productThreshText = (TextView) rowView.findViewById(R.id.P_thresh);
//        productThreshText.setText(pending.ProductThresh);
//
//        final TextView qtyText = (TextView) rowView.findViewById(R.id.P_qty);
//        qtyText.setText(pending.Quantity);
//
//
//        return rowView;
        return null;
    }

}
