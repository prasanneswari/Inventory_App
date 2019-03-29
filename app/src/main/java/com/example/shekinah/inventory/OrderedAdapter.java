package com.example.shekinah.inventory;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.shekinah.inventory.PurchaseOrdered.adapter;
import static com.example.shekinah.inventory.PurchaseOrdered.orderedDict;
import static com.example.shekinah.inventory.PurchaseOrdered.orders_listview;

/**
 * Created by JoshuaNitesh on 28/02/18.
 */

public class OrderedAdapter extends BaseAdapter {

    private Context oContext;
    private LayoutInflater oInflater;
    private ArrayList<OrderedDict> oDataSource;

    public OrderedAdapter(Context oContext, ArrayList<OrderedDict> oDataSource) {
        this.oContext = oContext;
        this.oInflater = (LayoutInflater) oContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.oDataSource = oDataSource;
    }

    @Override
    public int getCount() {
        return oDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return oDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        final OrderedDict ordered = (OrderedDict) getItem(position);
        final View rowView = oInflater.inflate(R.layout.ordered_list_view, viewGroup, false);

        TextView orderIdText = (TextView) rowView.findViewById(R.id.O_id);
        orderIdText.setText(ordered.OderId);

        TextView reqDateText = (TextView) rowView.findViewById(R.id.O_reqDate);
        reqDateText.setText(ordered.ReqDate);

        TextView dueDateText = (TextView) rowView.findViewById(R.id.O_dueDate);
        dueDateText.setText(ordered.DueDate);

        TextView statusText = (TextView) rowView.findViewById(R.id.O_status);
        statusText.setText(ordered.Status);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(oContext,OrderDetails.class);
                intent.putExtra("orderId",ordered.OderId);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                oContext.startActivity(intent);
            }
        });

        return rowView;
    }

    public void updateList() //TOO MUCH PROCESSING
    {
        adapter = new OrderedAdapter(oContext,orderedDict);
        orders_listview.setAdapter(adapter);
    }
}
