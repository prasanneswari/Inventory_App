package com.example.shekinah.inventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by JoshuaNitesh on 27/03/18.
 */

public class OrderedReqAdapter extends BaseAdapter {

    private Context orContext;
    private LayoutInflater orInflater;
    private ArrayList<OrderedReqDict> orDataSource;

    public OrderedReqAdapter(Context orContext, ArrayList<OrderedReqDict> orDataSource) {
        this.orContext = orContext;
        this.orInflater = (LayoutInflater) orContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.orDataSource = orDataSource;
    }

    @Override
    public int getCount() {
        return orDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return orDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        final OrderedReqDict ordered = (OrderedReqDict) getItem(position);
        final View rowView = orInflater.inflate(R.layout.ordered_req_list_view, viewGroup, false);

        TextView IdText = (TextView) rowView.findViewById(R.id.idText);
        IdText.setText(ordered.ProductId);

        TextView orderIdText = (TextView) rowView.findViewById(R.id.nameText);
        orderIdText.setText(ordered.ProductName);

        TextView reqDateText = (TextView) rowView.findViewById(R.id.reqQtyText);
        reqDateText.setText(ordered.QuantityRequested);

        TextView dueDateText = (TextView) rowView.findViewById(R.id.recQtyText);
        dueDateText.setText(ordered.QuantityReceived);

        TextView statusText = (TextView) rowView.findViewById(R.id.recDateText);
        statusText.setText(ordered.ReceivedDate);

        return rowView;
    }
}
