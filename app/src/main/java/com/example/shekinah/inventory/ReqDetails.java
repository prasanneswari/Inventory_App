package com.example.shekinah.inventory;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

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

import static com.example.shekinah.inventory.ConsumableAdapter.urID;
import static com.example.shekinah.inventory.MainActivity.domain_name;
import static com.example.shekinah.inventory.MainActivity.port;

public class ReqDetails extends AppCompatActivity {

    static String[] qIssAnew;
    String jsonS;
    static  String qty1="";
    com.android.volley.RequestQueue sch_RequestQueue;

    public static ExpandableListView expListReq;
    public static HashMap<String, ArrayList<ReqChildDict>> listDataChild;
    public static ArrayList<ReqHeaderDict> listDataHeader;
    public static ArrayList<ReqDetailsDict> reqDetailsDict;
    public static ReqExpAdapter rAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_req_details);
        expListReq = (ExpandableListView) findViewById(R.id.expListReq);

        Button Back = (Button) findViewById(R.id.cancelUReq);
        Button Update = (Button) findViewById(R.id.updateUReq);

        listDataHeader = new ArrayList<ReqHeaderDict>();
        reqDetailsDict = new ArrayList<ReqDetailsDict>();
        listDataChild = new HashMap<String, ArrayList<ReqChildDict>>();

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<String> detailsIdArr = new ArrayList<String>();
                String reqIdArr = "";
                List<String> quantArr = new ArrayList<String>();
                List<String> statArr = new ArrayList<String>();
                List<String> posArr = new ArrayList<String>();
                int noOfLines = 0;

                if(listDataChild.size() > 0)
                {
                    for(int i=0; i < listDataChild.size(); i++)
                    {
                        for(int j=0; j <  listDataChild.get(Integer.toString(i)).size() ; j++)
                        {
                            ReqChildDict c = listDataChild.get(Integer.toString(i)).get(j);
                            //Log.d("JOSHUA","POS i"+i +" i"+ j +" "+ c.Checked);
                            if( (c.Checked == true) && (Integer.parseInt(c.Qissued) > 0))
                            {
                                detailsIdArr.add(c.DetailsId);
                                reqIdArr = c.ReqId;
                                quantArr.add(c.Qissued);
                                if (Integer.parseInt(c.Qreq) == Integer.parseInt(c.Qissued)) {
                                    statArr.add("8");
                                } else if (Integer.parseInt(c.Qissued) < Integer.parseInt(c.Qreq)) {
                                    statArr.add("7");
                                } else if (Integer.parseInt(c.Qissued) == 0) {
                                    statArr.add("6");
                                }

                                if(c.Position.equals("None") || c.PositionId.equals("None"))
                                {
                                    noOfLines = 0;
                                    break;
                                }
                                else
                                {
                                    posArr.add(c.PositionId);
                                }
                                noOfLines = noOfLines + 1;
                            }
                        }
                    }
                }

                if(noOfLines > 0)
                {
                    jsonS = "{\"detailsid\":\"" + detailsIdArr + "\",\"requisitionid\":\"" + reqIdArr + "\",\"quantityissued\":\"" + quantArr + "\",\"statusid\":\"" + statArr + "\",\"positionid\":\"" + posArr + "\"}";
                    Log.d("JOSHUA","JSON TO SEND -- "+jsonS);
                    String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/update_requisitiondetails/";
                    try
                    {
                        JSONObject rmdt = null;
                        rmdt = new JSONObject(jsonS);
                        JSONSenderVolley(urlrs, rmdt);
                    }
                    catch (JSONException e) {

                    }
                }
                else
                {
                    showAlert("Please ensure you selected checboxes & filled all fields");
                }

            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ReqDetails.this,ConsumableIssues.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        jsonS = "{\"id\":\""+urID+"\"}";
        Log.d("-jsnresponse add---",""+jsonS);
        String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/get_requisitiondetails/";
        try
        {
            JSONObject rmdt = null;
            rmdt = new JSONObject(jsonS);
            getReqDetails(urlrs, rmdt);
        }
        catch (JSONException e) {

        }
    }

    public  void showAlert(String message)
    {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ReqDetails.this);
        builder.setTitle("ERROR");
        builder.setMessage(message);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }


    //JOSHUA getREQDETAILS
    public void getReqDetails(String url, final JSONObject json)
    {
        Log.d("---url-----", "---"+url);
        Log.d("555555", "00000000"+json.toString());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONArray jsonResp = response.getJSONArray("requisition_details");//CHANGE
                            Log.d("VOLLEY", "  JSONRESP ->  " + jsonResp);
                            for (int i = 0; i < jsonResp.length(); i++)
                            {
                                String jtsPid = jsonResp.getJSONObject(i).getString("jtsproductid");
                                String jtsName = jsonResp.getJSONObject(i).getString("jtsproductname");
                                String detailsId = jsonResp.getJSONObject(i).getString("detailsid");
                                String pId = jsonResp.getJSONObject(i).getString("productid");
                                String pName = jsonResp.getJSONObject(i).getString("productname");
                                String qIssued = jsonResp.getJSONObject(i).getString("quantityissued");
                                String qReq = jsonResp.getJSONObject(i).getString("quantityrequested");
                                String reqId = jsonResp.getJSONObject(i).getString("requisitionid");
                                String status = jsonResp.getJSONObject(i).getString("status");
                                int balance = 0;
                                if(qIssued != "0")
                                {
                                    balance = Integer.parseInt(qReq) - Integer.parseInt(qIssued);
                                }
                                reqDetailsDict.add(new ReqDetailsDict(false,detailsId,jtsPid,jtsName,pId,pName,qIssued,qReq,reqId,status,Integer.toString(balance)));

                                int index = -1;

                                for(ReqHeaderDict m: listDataHeader)
                                {
                                    if(m.JtsProdId.equals(jtsPid) && m.JtsProdName.equals(jtsName))
                                    {
                                        index = listDataHeader.indexOf(m);
                                        break;
                                    }
                                }

                                if(index == -1)
                                {
                                    listDataHeader.add(new ReqHeaderDict(jtsPid,jtsName));
                                }
                            }

                            for(ReqHeaderDict m: listDataHeader)
                            {
                                ArrayList<ReqChildDict> childDetails = new ArrayList<ReqChildDict>();
                                for(ReqDetailsDict d: reqDetailsDict)
                                {
                                    if(m.JtsProdId.equals(d.JtsPid) && m.JtsProdName.equals(d.JtsName))
                                    {
                                        childDetails.add(new ReqChildDict(d.Checked,d.DetailsId,d.Pid,d.Pname,d.Qissued,d.Qreq,d.ReqId,d.Status,d.Balance,"NONE","NONE"));
                                    }
                                }
                                int pos = listDataHeader.indexOf(m);
                                listDataChild.put(Integer.toString(pos),childDetails);
                            }
                            updateList();
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

    public void updateList()
    {
        rAdapter = new ReqExpAdapter(ReqDetails.this,listDataHeader,listDataChild);
        expListReq.setAdapter(rAdapter);
        expListReq.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        expListReq.setItemsCanFocus(true);

    }

    //UPDATE
    public void JSONSenderVolley(String url, final JSONObject json)
    {
        Log.d("---url-----", "---"+url);
        Log.d("555555", "00000000"+json.toString());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, json,

                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(" ", response.toString());
                        Log.d("----JSONSenderVolley--", "---"+response.toString());
                        try {
                            String errorCode = response.getString("error_code");
                            String errorDesc = response.getString("error_desc");

                                Log.d("errorCode","" + errorCode+"--" + errorDesc);

                                final AlertDialog.Builder builder = new AlertDialog.Builder(ReqDetails.this);
                                builder.setTitle("Info");
                                builder.setMessage(errorDesc);

                                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        jsonS = "{\"id\":\""+urID+"\"}";
                                        Log.d("-jsnresponse add---",""+jsonS);
                                        String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/get_requisitiondetails/";
                                        try
                                        {
                                            JSONObject rmdt = null;
                                            rmdt = new JSONObject(jsonS);
                                            getReqDetails(urlrs, rmdt);
                                        }
                                        catch (JSONException e) {
                                            Log.d("-ERROR---", "" + ""+e);
                                        }
                                        dialogInterface.dismiss();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(" Error---------", "Tabitha: " + String.valueOf(error));
                Log.d("my test error-----","Tabitha: " + String.valueOf(error));
                Toast.makeText(getApplicationContext(), "connection error ", Toast.LENGTH_LONG).show();
            }
        }) {
            /**
             * Passing some request headers
             * */
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

        // Adding request to request queue
        jsonObjReq.setTag("");
        addToRequestQueue(jsonObjReq);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        if (sch_RequestQueue == null) {

            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());

        }
        sch_RequestQueue.add(req);
        //getRequestQueue().add(req);
    }

}
