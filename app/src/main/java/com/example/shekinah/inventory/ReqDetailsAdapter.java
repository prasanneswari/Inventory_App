package com.example.shekinah.inventory;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import java.util.List;
import java.util.Map;

import static com.example.shekinah.inventory.MainActivity.domain_name;
import static com.example.shekinah.inventory.MainActivity.port;

public class ReqDetailsAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] id, reqId, productid, productname, positionA, productidA, quantityA, quantityidA, productnameA, qReq, status, qIss, bal;
    static  String[] detailsId, isuedqty;
    TextView productnameT,qReqT;
    static String detailsid, reqid, Iss, bal1, jsonS;
    EditText qIssT;
    TextView statusT, balT;
    LinearLayout row;
    boolean watch=false;
    com.android.volley.RequestQueue sch_RequestQueue;
    static  String productid1, productname1, qReq1, status1, qIss1, detailsID, oldIssQty, requestedQty;

    public ReqDetailsAdapter(Context context, String[] idS, String[] reqIdS, String[] productidS, String[] productnameS, String[] qReqS, String[] qIssS, String[] statusS, String[] balS) {

        super(context, R.layout.activity_req_details, productnameS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter

        this.context = context;
        this.id = idS;
        this.detailsId = idS; //duplicate for update
        this.reqId = reqIdS;
        this.productid = productidS;
        this.productname = productnameS;
        this.qReq = qReqS;
        this.qIss = qIssS;
        this.isuedqty = qIssS; //duplicate for update
        this.status = statusS;
        this.bal = balS;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_req_details_adapter, parent, false);  //Setting content view of xml
        // ImageView imageView=(ImageView)rowView.findViewById(R.id.image);

        //Assigning IDs from xml
        productnameT = (TextView) rowView.findViewById(R.id.reqPrd);
        qReqT = (TextView) rowView.findViewById(R.id.reqQty);
        qIssT = (EditText) rowView.findViewById(R.id.issuedQty);
        statusT = (TextView) rowView.findViewById(R.id.reqStatus);
        balT = (TextView) rowView.findViewById(R.id.reqBal);
        row = (LinearLayout) rowView.findViewById(R.id.reqRow);
        qIssT.setText(qIss[position]);

        Log.d("qIssT ", qIss[position]);
        balT.setText(bal[position]);
        Log.d("balT ", bal[position]);
        qIssT.requestFocus();
        qIssT.setFocusableInTouchMode(true);


        qIssT.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                //detailsID = id[position];
                String enteredValue  = s.toString();
                Log.d("enteredValue ", enteredValue);
                qIss[position] = enteredValue;
                Log.d("addTextChangedListener ", qIss[position]);

                Iss = enteredValue;
                reqid = reqId[position];
                detailsid = id[position];

                isuedqty[position] = enteredValue;
              }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
               // qIssAnew[position]="nodata";
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //qIssAnew[position] = qIssT.getText().toString();
            }
        });

        try {
            //Assigning values from array to individual layouts in list view
            productnameT.setText(productname[position]);
            qReqT.setText(qReq[position]);
            balT.setText(bal[position]);

            Log.d("Req" ," productname1 :" + productname[position]);
            Log.d("Req" ," qReq1 :" + qReq[position]);
            Log.d("Req" ," qIss1 :" + qIss[position]);
            Log.d("Req" ," status1 :" + status[position]);
            Log.d("Req" ," bal1 :" + bal[position]);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        productnameT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productid1 = productid[position];
                productname1 = productname[position];
                jsonS = "{\"productid\":\""+productid1+"\"}";
                Log.d("-jsnresponse add---",""+jsonS);
                String urlrs1 ="http://"+domain_name+":"+port+"/InventoryApp/get_positionquantity/";
                try {
                    JSONObject rmdt = null;

                    rmdt = new JSONObject(jsonS);
                    get_positionquantityVolley(urlrs1, rmdt);
                } catch (JSONException e) {

                }
                Log.d("-jsnresponse enter---", "" + jsonS);
            }
        });
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                detailsID = id[position];

                productid1 = productid[position];
                productname1 = productname[position];
                qReq1 = qReq[position];
                qIss1 = qIss[position];
                bal1 = bal[position];
                status1 = status[position];
                Log.d("Req" ," detailsID :" + detailsID);
                Log.d("Req" ," productid1 :" + productid1);
                Log.d("Req" ," productname1 :" + productname1);
                Log.d("Req" ," qReq1 :" + qReq1);
                Log.d("Req" ," qIss1 :" + qIss1);
                Log.d("Req" ," status1 :" + status1);
                //qIssT.setText(Iss);
                qIss[position] = qIssT.getText().toString();
                Log.d("new ", qIss[position]);
            }


        });

        return rowView;
    }

    public void get_positionquantityVolley(String url, final JSONObject json)
    {
        Log.d("---url-----", "---"+url);
        Log.d("555555", "00000000"+json.toString());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("--search response- ", "get_positionquantityVolley--"+response.toString());

                        JSONObject responseJSON = null;
                        try {
                            responseJSON = new JSONObject(String.valueOf(response));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            Log.d( " Array", " response ->  " + response);
                            JSONArray new_array1;
                            List<String> positionL = new ArrayList<String>();
                            List<String> quantityL = new ArrayList<String>();
                            List<String> quantityidL = new ArrayList<String>();
                            List<String> productidL = new ArrayList<String>();
                            List<String> productnameL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("position_quantity");
                            Log.d( " Array", " : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String positionS = new_array1.getJSONObject(i).getString("position");
                                String quantityS = new_array1.getJSONObject(i).getString("quantity");
                                String quantityidS = new_array1.getJSONObject(i).getString("quantityid");
                                String productidS = new_array1.getJSONObject(i).getString("productid");
                                String productnameS = new_array1.getJSONObject(i).getString("productname");

                                positionL.add(positionS);
                                quantityL.add(quantityS);
                                quantityidL.add(quantityidS);
                                productidL.add(productidS);
                                productnameL.add(productnameS);
                            }

                            positionA = new String[positionL.size()];
                            quantityA = new String[quantityL.size()];
                            quantityidA = new String[quantityidL.size()];
                            productidA = new String[productidL.size()];
                            productnameA = new String[productnameL.size()];

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
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
            /**
             * Passing some request headers
             * */
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
    public <T> void addToRequestQueue(Request<T> req) {
        if (sch_RequestQueue == null) {

            sch_RequestQueue = Volley.newRequestQueue(context);

        }
        sch_RequestQueue.add(req);
    }

}

