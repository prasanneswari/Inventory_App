package com.example.shekinah.inventory;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.shekinah.inventory.MainActivity.domain_name;
import static com.example.shekinah.inventory.MainActivity.port;

public class OrderedRequisition extends AppCompatActivity {

    Button cancel;

    public static ListView orderedListView;
    public static ArrayList<OrderedReqDict> orDict;
    public static OrderedReqAdapter orAdapter;

    RequestQueue sch_RequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordered_requisition);

        orderedListView = (ListView) findViewById(R.id.ordered_listview);
        orDict = new ArrayList<OrderedReqDict>();

        cancel = (Button) findViewById(R.id.cancelButton);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        orderedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("JOSHUA","position "+i);
                OrderedReqDict o = orDict.get(i);
                updateQuantity(i,o);

            }
        });

        httpRequest();

    }

    public void updateQuantity(final int pos,final OrderedReqDict o)
    {
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(OrderedRequisition.this);
        View promptsView = li.inflate(R.layout.ordered_prompt, null);

        final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(OrderedRequisition.this);
        alertDialogBuilder.setView(promptsView);

        final TextView idText = (TextView) promptsView.findViewById(R.id.prompt_id);
        final TextView nameText = (TextView) promptsView.findViewById(R.id.prompt_name);
        final TextView qtyReqText = (TextView) promptsView.findViewById(R.id.prompt_qty_req);
        final TextView qtyRecText = (TextView) promptsView.findViewById(R.id.prompt_qty_rec);
        final TextView reqDateText = (TextView) promptsView.findViewById(R.id.prompt_req_date);
        final TextView recDateText = (TextView) promptsView.findViewById(R.id.prompt_rec_date);
        final TextView posText = (TextView) promptsView.findViewById(R.id.prompt_item_pos);
        final TextView dateText = (TextView) promptsView.findViewById(R.id.prompt_input_date);


        idText.setText(o.ProductId);
        nameText.setText(o.ProductName);
        qtyReqText.setText(o.QuantityRequested);
        qtyRecText.setText(o.QuantityReceived);
        reqDateText.setText(o.RequestedDate);
        recDateText.setText(o.ReceivedDate);

        posText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(o.ProductId,posText);
            }
        });

        final EditText quantityInput = (EditText) promptsView.findViewById(R.id.prompt_qty_input);

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDueDate(dateText);
            }
        });

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                String newQty = quantityInput.getText().toString().trim();
                                String position = posText.getText().toString();
                                String date = dateText.getText().toString();
                                if((!date.equals("")) &&  (!date.equals("None")) &&  (!newQty.equals("")) &&  (!newQty.equals("None")) && (!position.equals("None")) && (!position.equals("")))
                                { 
                                    String jsonString = "{\"purchaseid\":\"" + o.PurchaseId + "\", \"purchaseorderid\":\"" + o.PurchaseOrderId + "\", \"purchasedetailsid\":\"" + o.PurchaseDetailsId + "\", \"quantityreceived\":\"" + newQty + "\", \"receiveddate\":\"" + date + "\", \"positionid\":\"" + position + "\"}";
                                    Log.d("Joshua","Order Json "+jsonString);
                                    String urlToCall = "http://"+domain_name+":"+port+"/InventoryApp/update_storeorders/";
                                    dialog.dismiss();
                                    try
                                    {
                                        JSONObject jsonStrObj = new JSONObject(jsonString);
                                        JSONSenderVolley(urlToCall, jsonStrObj,"Order");
                                    }
                                    catch (JSONException e) {
                                        Log.d("ERROR",""+e);
                                    }

                                    dialog.dismiss();
                                }
                                else {
                                    dialog.dismiss();
                                    showAlert("Please Enter the Date & Required Quantity");
                                }
                            }
                        })
                .setNegativeButton("Conflict",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
//                                String newQty = quantityInput.getText().toString().trim();
//                                if((!dueDate.equals("")))
//                                {
//                                    String jsonString = "{\"duedate\":\"" + dueDate + "\", \"sessionid\":\"" + sessionid + "\", \"productid\":\"" + p.ProductId + "\"}";
//                                    Log.d("Joshua","Suppress json "+jsonString);
//                                    String urlToCall = "http://"+domain_name+":"+port+"/InventoryApp/add_purchaserequisition/";
//                                    dialog.dismiss();
//                                    try
//                                    {
//                                        JSONObject jsonStrObj = new JSONObject(jsonString);
//                                        //JSONSenderVolley(urlToCall, jsonStrObj,"Suppress");
//                                        Log.d("JOSHUA","SUPPRESS API NEEDED");
//                                    }
//                                    catch (JSONException e) {
//                                        Log.d("ERROR",""+e);
//                                    }
//                                }
//                                else
//                                {
//                                    dialog.dismiss();
//                                    showAlert("Please Enter the Date");
//                                }
                                dialog.dismiss();
                                Log.d("JOSHUA","Need conflict API");
                            }
                        });


        // create alert dialog
        final android.app.AlertDialog alertDialog = alertDialogBuilder.create();

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

    public void setDueDate(final TextView date)
    {
        // Get Current Date
        int mYear, mMonth, mDay;
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(OrderedRequisition.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        String fillerMonth, fillerDay;
                        fillerMonth = "-";
                        fillerDay = "-";
                        if(monthOfYear<10)
                        {
                            fillerMonth = "-0";
                        }
                        if(dayOfMonth<10)
                        {
                            fillerDay = "-0";
                        }
                        String newDate = year + fillerMonth + (monthOfYear + 1) + fillerDay + dayOfMonth;
                        date.setText(newDate);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void showAlert(String message)
    {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(OrderedRequisition.this);
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

    private void showDialog(String pid, final TextView itemPos){

        String jsonS = "{\"productid\":\""+pid+"\"}";
        Log.d("JOSHUA","prodid -- "+jsonS);
        String urlrs = "http://"+domain_name+":"+port+"/InventoryApp/get_positionquantity/";

        final ArrayList<ReqPositionDict> reqPositionDict = new ArrayList<ReqPositionDict>();
        final Dialog dialog = new Dialog(OrderedRequisition.this);
        LayoutInflater li = LayoutInflater.from(OrderedRequisition.this);
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
                                        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(OrderedRequisition.this);
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

                                ReqPositionAdapter posAdapter = new ReqPositionAdapter(OrderedRequisition.this,reqPositionDict);
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
                ReqPositionDict p = reqPositionDict.get(i);
                itemPos.setText(p.positionId);
                dialog.dismiss();
            }
        });
    }

    public void JSONSenderVolley(String url, final JSONObject json,String tag)
    {
        Log.d("URL", "--- "+url);
        Log.d("JSON STRING", ""+json.toString());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("---------", "---"+response.toString());
                        try
                        {
                            String errorCode = response.getString("error_code");
                            String errorDesc = response.getString("error_desc");
                            Log.d("errorCode","" + errorCode+"--" + errorDesc);
                            Toast.makeText(OrderedRequisition.this, ""+errorDesc, Toast.LENGTH_SHORT).show();
//                            if(errorCode == "1")
//                            {
//                                onBackPressed();
//                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(" VOLLEY ERROR JSON ", "" + String.valueOf(error));
                        Log.d(" VOLLEY ERROR JSON ","" + String.valueOf(error));
                        Toast.makeText(OrderedRequisition.this, ""+error, Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept","application/json");
                //headers.put("Content-Type","application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                //return (headers != null || headers.isEmpty()) ? headers : super.getHeaders();
                return headers;
            }
        };

        jsonObjReq.setTag("");
        addToRequestQueue(jsonObjReq);
    }


    public void httpRequest()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url=" http://"+domain_name+":"+port+"/InventoryApp/get_storeorders/";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("hello :", response.toString());

                        try
                        {
                            JSONObject jsonObjectResp = new JSONObject(response);

                            JSONArray jsonResp = jsonObjectResp.getJSONArray("get_storeorders");//CHANGE
                            Log.d("VOLLEY", "  JSONRESP ->  " + jsonResp);
                            orDict.clear();
                            for (int i = 0; i < jsonResp.length(); i++)
                            {
                                String duedate = jsonResp.getJSONObject(i).getString("duedate");
                                String productid = jsonResp.getJSONObject(i).getString("productid");
                                String productname = jsonResp.getJSONObject(i).getString("productname");
                                String purchasedetailsid = jsonResp.getJSONObject(i).getString("purchasedetailsid");
                                String purchaseid = jsonResp.getJSONObject(i).getString("purchaseid");
                                String purchaseorderid = jsonResp.getJSONObject(i).getString("purchaseorderid");
                                //String quantityordered = jsonResp.getJSONObject(i).getString("quantityordered");
                                String quantityreceived = jsonResp.getJSONObject(i).getString("quantityreceived");
                                String quantityrequested = jsonResp.getJSONObject(i).getString("quantityrequested");
                                String requesteddate = jsonResp.getJSONObject(i).getString("requesteddate");
                                String receivedddate = jsonResp.getJSONObject(i).getString("receiveddate");
                                String status = jsonResp.getJSONObject(i).getString("status");
                                orDict.add(new OrderedReqDict(productid,productname,purchasedetailsid,purchaseid,purchaseorderid,quantityreceived,quantityrequested,requesteddate,receivedddate,duedate,status));
                            }

                            updateList();

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VOLLEY ERROR ",""+error);
                Toast.makeText(OrderedRequisition.this, ""+error, Toast.LENGTH_SHORT).show();
            }

        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void updateList()
    {
        orAdapter = new OrderedReqAdapter(OrderedRequisition.this,orDict);
        orderedListView.setAdapter(orAdapter);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        if (sch_RequestQueue == null) {

            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

/*

get_purchaseorders

{"get_storeorders":[ { "duedate": "2018-03-30", "productid": 1, "productname": "Pen", "purchasedetailsid": 1, "purchaseid": 5, "purchaseorderid": 20, "quantityordered": 10, "quantityreceived": 10, "quantityrequested": 10, "receiveddate": "18-03-26", "requesteddate": "2018-03-01", "status": "received" } ]}

 */
