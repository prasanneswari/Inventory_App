package com.example.shekinah.inventory;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import java.util.List;
import java.util.Map;

import static com.example.shekinah.inventory.MainActivity.domain_name;
import static com.example.shekinah.inventory.MainActivity.port;
import static com.example.shekinah.inventory.MainActivity.sessionid;

public class PurchasePending extends AppCompatActivity {

    private ProgressDialog dialog_progress ;
    AlertDialog.Builder builder;
    AlertDialog alert_dialog;
    Button cancel,suppressButton;

    public static ListView pending_listview;
    public static ArrayList<PendingDict> pendingDict;
    public static PendingAdapter adapter;

    RequestQueue sch_RequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_pending);

        pending_listview = (ListView) findViewById(R.id.L_PendingList_Id);
        pendingDict = new ArrayList<PendingDict>();

        cancel = (Button) findViewById(R.id.B_Cancel_Id);
        suppressButton = (Button) findViewById(R.id.B_Suppress);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PurchasePending.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        suppressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(PurchasePending.this,SuppressItems.class);
//                //intent.putExtra()
//                startActivity(intent);
                Log.d("JOSHUA","Suppress API needed for intent");
            }
        });

        pending_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("JOSHUA","position "+i);
                PendingDict p = pendingDict.get(i);
                updateQuantity(i,p);

            }
        });

        httpRequest();
    }

    public void updateQuantity(final int pos,final PendingDict p)
    {
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(PurchasePending.this);
        View promptsView = li.inflate(R.layout.quantity_prompt, null);

        final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(PurchasePending.this);
        alertDialogBuilder.setView(promptsView);

        final TextView idText = (TextView) promptsView.findViewById(R.id.prompt_id);
        final TextView nameText = (TextView) promptsView.findViewById(R.id.prompt_name);
        final TextView currText = (TextView) promptsView.findViewById(R.id.prompt_curr);
        final TextView threshText = (TextView) promptsView.findViewById(R.id.prompt_thresh);
        final TextView positionText = (TextView) promptsView.findViewById(R.id.prompt_pos);
        final TextView dateText = (TextView) promptsView.findViewById(R.id.prompt_date);

        idText.setText(p.ProductId);
        nameText.setText(p.ProductName);
        currText.setText(p.ProductCurrQty);
        threshText.setText(p.ProductThresh);
        positionText.setText(p.ProductPos);

        final EditText quantityInput = (EditText) promptsView.findViewById(R.id.prompt_req_qty);
        quantityInput.setText(p.Quantity);

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDueDate(dateText);
            }
        });

        // set dialog message
        alertDialogBuilder.setCancelable(false).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Order",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                String newQty = quantityInput.getText().toString().trim();
                                String dueDate = dateText.getText().toString();
                                if((!newQty.equals("")) && (!dueDate.equals("")))
                                {
                                    List<String> prodId = new ArrayList<String>();
                                    prodId.add(p.ProductId);
                                    List<String> quantArr = new ArrayList<String>();
                                    quantArr.add(newQty);
                                    String jsonString = "{\"duedate\":\"" + dueDate + "\", \"sessionid\":\"" + sessionid + "\", \"productid\":\"" + prodId + "\", \"quantity\":\"" + quantArr + "\"}";
                                    Log.d("Joshua","Order Json "+jsonString);
                                    String urlToCall = "http://"+domain_name+":"+port+"/InventoryApp/add_purchaserequisition/";
                                    dialog.dismiss();
                                    try
                                    {
                                        JSONObject jsonStrObj = new JSONObject(jsonString);
                                        JSONSenderVolley(urlToCall, jsonStrObj,"Order");
                                    }
                                    catch (JSONException e) {
                                        Log.d("ERROR",""+e);
                                    }
                                }
                                else {
                                    dialog.dismiss();
                                    showAlert("Please Enter the Date & Required Quantity");
                                }
                            }
                        })
                .setNegativeButton("Suppress",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                String dueDate = dateText.getText().toString();
                                if((!dueDate.equals("")))
                                {
                                    String jsonString = "{\"duedate\":\"" + dueDate + "\", \"sessionid\":\"" + sessionid + "\", \"productid\":\"" + p.ProductId + "\"}";
                                    Log.d("Joshua","Suppress json "+jsonString);
                                    String urlToCall = "http://"+domain_name+":"+port+"/InventoryApp/add_purchaserequisition/";
                                    dialog.dismiss();
                                    try
                                    {
                                        JSONObject jsonStrObj = new JSONObject(jsonString);
                                        //JSONSenderVolley(urlToCall, jsonStrObj,"Suppress");
                                        Log.d("JOSHUA","SUPPRESS API NEEDED");
                                    }
                                    catch (JSONException e) {
                                        Log.d("ERROR",""+e);
                                    }
                                }
                                else
                                {
                                    dialog.dismiss();
                                    showAlert("Please Enter the Date");
                                }
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

        DatePickerDialog datePickerDialog = new DatePickerDialog(PurchasePending.this,
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(PurchasePending.this);
        builder.setTitle("Error");
        builder.setMessage(message);

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void JSONSenderVolley(String url, final JSONObject json, final String tag)
    {

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

                            Toast.makeText(PurchasePending.this, ""+errorDesc, Toast.LENGTH_SHORT).show();

                            if(errorCode.equals("1") && tag.equals("Order"))
                            {
                                httpRequest();
                            }


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
                        Toast.makeText(PurchasePending.this, ""+error, Toast.LENGTH_SHORT).show();
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

        jsonObjReq.setTag("");
        addToRequestQueue(jsonObjReq);
    }

    public void httpRequest()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url=" http://"+domain_name+":"+port+"/InventoryApp/get_purchasableproducts/";
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

                            JSONArray jsonResp = jsonObjectResp.getJSONArray("purchasable_product_details");//CHANGE
                            Log.d("VOLLEY", "  JSONRESP ->  " + jsonResp);
                            pendingDict.clear();
                            for (int i = 0; i < jsonResp.length(); i++)
                            {
                                Boolean checked = false;
                                String pId = jsonResp.getJSONObject(i).getString("productid");
                                String pName = jsonResp.getJSONObject(i).getString("productname");
                                String pPos = jsonResp.getJSONObject(i).getString("productposition");
                                String pThresh = jsonResp.getJSONObject(i).getString("reorderlevel");
                                String pCurrQty = jsonResp.getJSONObject(i).getString("currentquantity");
                                String qty = "0";
                                pendingDict.add(new PendingDict(checked,pId,pName,pPos,pThresh,pCurrQty,qty));
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
                Toast.makeText(PurchasePending.this, ""+error, Toast.LENGTH_SHORT).show();
            }

        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void updateList()
    {
        adapter = new PendingAdapter(PurchasePending.this,pendingDict);
        pending_listview.setAdapter(adapter);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        if (sch_RequestQueue == null) {

            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
        //getRequestQueue().add(req);
    }

}


/*

{"purchasable_product_details":[
{
"productid": 3,
"productname": "Permanent marker",
"productposition": "S1R1B1P1",
"reorderlevel": 5,
"currentquantity": 2 },

{ "productid": 2, "productname": "Note Pad", "productposition": "S6R7B4P5", "reorderlevel": 10, "currentquantity": 10 } ]}

 */
