package com.example.shekinah.inventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by JoshuaNitesh on 22/03/18.
 */

public class ReqPositionAdapter extends BaseAdapter{

    private Context rContext;
    private LayoutInflater rInflater;
    private ArrayList<ReqPositionDict> rDataSource;

    public ReqPositionAdapter(Context rContext, ArrayList<ReqPositionDict> rDataSource) {
        this.rContext = rContext;
        this.rInflater = (LayoutInflater) rContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.rDataSource = rDataSource;
    }

    @Override
    public int getCount() {
        return rDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return rDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        final ReqPositionDict pos = (ReqPositionDict) getItem(position);
        final View rowView = rInflater.inflate(R.layout.activity_pqty_details_adapter, viewGroup, false);

        TextView posName = (TextView) rowView.findViewById(R.id.pos);
        TextView qty = (TextView) rowView.findViewById(R.id.qty);
        TextView stat = (TextView) rowView.findViewById(R.id.Status);

        posName.setText(pos.Position);
        qty.setText(pos.quantity);
        stat.setText(pos.positionStat);

        return rowView;
    }

}
