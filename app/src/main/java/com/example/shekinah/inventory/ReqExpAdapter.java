package com.example.shekinah.inventory;

/**
 * Created by JoshuaNitesh on 20/03/18.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.shekinah.inventory.MainActivity.domain_name;
import static com.example.shekinah.inventory.MainActivity.port;
import static com.example.shekinah.inventory.ReqDetails.listDataChild;

public class ReqExpAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private ArrayList<ReqHeaderDict> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, ArrayList<ReqChildDict>> _listDataChild;

    com.android.volley.RequestQueue sch_RequestQueue;

    public ReqExpAdapter(Context context, ArrayList<ReqHeaderDict> listDataHeader, HashMap<String, ArrayList<ReqChildDict>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(Integer.toString(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final ReqChildDict childDict = (ReqChildDict) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_req_details_adapter, null);
        }

        final TextView balView = (TextView) convertView.findViewById(R.id.reqBal);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.itemCheck);
        TextView prodName = (TextView) convertView.findViewById(R.id.reqPrd);
        TextView reqQty = (TextView) convertView.findViewById(R.id.reqQty);
        final TextView itemPos = (TextView) convertView.findViewById(R.id.itemPos);
        final EditText issued = (EditText) convertView.findViewById(R.id.issuedQty);

        prodName.setText(childDict.Pname);
        reqQty.setText(childDict.Qreq);
        issued.setText(childDict.Qissued);
        itemPos.setText(childDict.Position);
        balView.setText(childDict.Balance);

        if(childDict.Checked) {
            checkBox.setChecked(true);
        }
        else {
            checkBox.setChecked(false);
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    ReqChildDict c = listDataChild.get(Integer.toString(groupPosition)).get(childPosition);
                    listDataChild.get(Integer.toString(groupPosition)).set(childPosition,new ReqChildDict(true,c.DetailsId,c.Pid,c.Pname,c.Qissued,c.Qreq,c.ReqId,c.Status,c.Balance,c.Position,c.PositionId));
                    //Log.d("JOSHUA",groupPosition+" "+childPosition+" issued"+ c.Qissued+" bal"+c.Balance+" checked"+c.Checked);
                }
                else
                {
                    ReqChildDict c = listDataChild.get(Integer.toString(groupPosition)).get(childPosition);
                    listDataChild.get(Integer.toString(groupPosition)).set(childPosition,new ReqChildDict(false,c.DetailsId,c.Pid,c.Pname,c.Qissued,c.Qreq,c.ReqId,c.Status,c.Balance,c.Position,c.PositionId));
                    //Log.d("JOSHUA",groupPosition+" "+childPosition+" issued"+ c.Qissued+" bal"+c.Balance+" checked"+c.Checked);
                }
            }
        });

        itemPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(groupPosition,childPosition,childDict.Pid,itemPos);
            }
        });

        issued.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                String enteredString  = s.toString();
                final int prevIssued = Integer.parseInt(childDict.Qissued);
                int requested = Integer.parseInt(childDict.Qreq);
                int result = 0;
                if(enteredString.length() > 0 & enteredString != "")
                {
                    int input = Integer.parseInt(enteredString);
                    if(input > requested)
                    {
                        issued.setText(childDict.Qissued);
                        showAlert("Issue cannot exceed requested quantity");
                    }
//                    else if(input < prevIssued)
//                    {
//                        issued.setText(childDict.Qissued);
//                        showAlert("Issue cannot be reduced");
//                    }
                    else
                    {
                        result = requested - input;
                        ReqChildDict c = listDataChild.get(Integer.toString(groupPosition)).get(childPosition);
                        listDataChild.get(Integer.toString(groupPosition)).set(childPosition,new ReqChildDict(c.Checked,c.DetailsId,c.Pid,c.Pname,enteredString,c.Qreq,c.ReqId,c.Status,Integer.toString(result),c.Position,c.PositionId));
                        //Log.d("JOSHUA",groupPosition+" "+childPosition+" issued"+ c.Qissued+" bal"+c.Balance+" checked"+c.Checked);
                        balView.setText(Integer.toString(result));
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(Integer.toString(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final ReqHeaderDict headDict = (ReqHeaderDict) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.activity_req_exp_group, null);
        }

        TextView JtsPid = (TextView) convertView.findViewById(R.id.jtsPid);
        TextView JtsPname = (TextView) convertView.findViewById(R.id.jtsPname);
        JtsPid.setTypeface(null, Typeface.BOLD);
        JtsPname.setTypeface(null, Typeface.BOLD);
        JtsPid.setText(headDict.JtsProdId);
        JtsPname.setText(headDict.JtsProdName);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void showAlert(String message)
    {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(_context);
        builder.setTitle("Error");
        builder.setMessage(message);

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        android.support.v7.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDialog(final int groupPosition,final int childPosition, String pid, final TextView itemPos){

        String jsonS = "{\"productid\":\""+pid+"\"}";
        Log.d("JOSHUA","prodid -- "+jsonS);
        String urlrs = "http://"+domain_name+":"+port+"/InventoryApp/get_positionquantity/";

        final ArrayList<ReqPositionDict> reqPositionDict = new ArrayList<ReqPositionDict>();
        final Dialog dialog = new Dialog(_context);
        LayoutInflater li = LayoutInflater.from(_context);
        final View view = li.inflate(R.layout.activity_req_pos_layout, null);
        final ListView lv = (ListView) view.findViewById(R.id.position_listView);

        try
        {
            JSONObject jsonToSend = new JSONObject(jsonS);

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    urlrs, jsonToSend,

                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                Log.d("JOSHUA",""+response);
                                if(response.has("error_code"))
                                {
                                    String errorCode = response.getString("error_code");
                                    String errorDesc = response.getString("error_desc");
                                    if(errorCode.equals("2"))
                                    {
                                        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(_context);
                                        builder.setTitle("ALERT");
                                        builder.setMessage(errorDesc);

                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        });

                                        android.app.AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }
                                }

                                JSONArray jsonResp = response.getJSONArray("position_quantity");
                                for (int i = 0; i < jsonResp.length(); i++)
                                {
                                    String position = jsonResp.getJSONObject(i).getString("position");
                                    String quantity = jsonResp.getJSONObject(i).getString("quantity");
                                    String positionId = jsonResp.getJSONObject(i).getString("positionid");
                                    String quantityId = jsonResp.getJSONObject(i).getString("quantityid");
                                    String positionStat = jsonResp.getJSONObject(i).getString("positionstatus");
                                    reqPositionDict.add(new ReqPositionDict(position,quantity,positionId,quantityId,positionStat));
                                }

                                ReqPositionAdapter posAdapter = new ReqPositionAdapter(_context,reqPositionDict);
                                lv.setAdapter(posAdapter);
                                //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setTitle("PICK A POSITION");
                                dialog.setContentView(view);
                                dialog.show();
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                            catch (Exception e){
                                Log.d("Exception 2", ""+e);
                            }

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(" Error---------", "Tabitha: " + String.valueOf(error));
                    Log.d("my test error-----","Tabitha: " + String.valueOf(error));
                }
            })

            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Accept","application/json");
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };

            // Adding request to request queue
            jsonObjReq.setTag("");
            addToRequestQueue(jsonObjReq);
        }
        catch (JSONException e) {
            Log.d("JOSHUA","ADAPTER ERROR GETPOS "+e);
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("JOSHUA","POSITION "+i);
                //itemPos.setText(Integer.toString(result));
                ReqPositionDict p = reqPositionDict.get(i);
                itemPos.setText(p.Position);
                ReqChildDict c = listDataChild.get(Integer.toString(groupPosition)).get(childPosition);
                listDataChild.get(Integer.toString(groupPosition)).set(childPosition,new ReqChildDict(c.Checked,c.DetailsId,c.Pid,c.Pname,c.Qissued,c.Qreq,c.ReqId,c.Status,c.Balance,p.Position,p.positionId));
                dialog.dismiss();
            }
        });
    }

    public <T> void addToRequestQueue(Request<T> req) {
        if (sch_RequestQueue == null) {

            sch_RequestQueue = Volley.newRequestQueue(_context);
        }
        sch_RequestQueue.add(req);
    }
}
