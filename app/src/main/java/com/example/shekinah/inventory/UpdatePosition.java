package com.example.shekinah.inventory;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.shekinah.inventory.MainActivity.domain_name;
import static com.example.shekinah.inventory.MainActivity.port;
import static com.example.shekinah.inventory.PositionsAdapter.poWH;
import static com.example.shekinah.inventory.PositionsAdapter.poID;
import static com.example.shekinah.inventory.PositionsAdapter.poName;
import static com.example.shekinah.inventory.PositionsAdapter.poStatus;

public class UpdatePosition extends AppCompatActivity {

    String[] sIdA, statusNameA, widA, WHNameA, new_array;
    Spinner statusULE, WHSp;
    String sid, urlrs, wid, productnameS, quantityS;
    JSONObject rmdt;
    Boolean s=false, w=false;
    private ProgressDialog dialog_progress ;
    static String from= "", quantityidS;
    Boolean hasProduct = false;
    TextView product, QtyP;
    com.android.volley.RequestQueue sch_RequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_position);
        final EditText nameULE = (EditText) findViewById(R.id.nameUL);
        WHSp = (Spinner) findViewById(R.id.addrUW);
        Button change = (Button) findViewById(R.id.change);
        Button addP = (Button) findViewById(R.id.addP);
        statusULE = (Spinner) findViewById(R.id.statusUL);
        product = (TextView) findViewById(R.id.Prod);
        QtyP = (TextView) findViewById(R.id.qty);

        final Button Update = (Button) findViewById(R.id.updateLoc);
        Button Cancel = (Button) findViewById(R.id.cancelUL);
        dialog_progress = new ProgressDialog(UpdatePosition.this);
        dialog_progress.setMessage("connecting ...");
        dialog_progress.show();
        getStatus();
        getWH();
        nameULE.setText(poName);

        String jsonS1 = "{\"positionid\":\""+poID+"\"}";
        Log.d("-jsnresponse update---",""+jsonS1);
        String url ="http://"+domain_name+":"+port+"/InventoryApp/get_productposition/";
        try {
            JSONObject rmdt = null;

            rmdt = new JSONObject(jsonS1);
            VolleyProdPos(url, rmdt);
        } catch (JSONException e) {

        }
        Log.d("-jsnresponse enter---", "" + jsonS1);
        //addrULE.setText(poAddr);
        //statusULE.setSelected();

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = poID;
                String name = nameULE.getText().toString();
                String WHS = WHSp.getSelectedItem().toString();
                String statuSs = statusULE.getSelectedItem().toString();

                for (int l = 0; l < widA.length; l++) {

                    if( WHNameA[l] == WHS){
                        wid = widA[l];
                    }
                    Log.d("WH Name ", WHNameA[l]);
                }

                for (int l = 0; l < sIdA.length; l++) {

                    if( statusNameA[l] == statuSs){
                        sid = sIdA[l];
                    }
                    Log.d("statusName ", statusNameA[l]);
                }
                String jsonS = "{\"id\":\""+id+"\",\"position\":\""+name+"\",\"statusid\":\""+sid+"\",\"warehouseid\":\""+wid+"\"}";
                Log.d("-jsnresponse update---",""+jsonS);
                String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/update_position/";
                try {
                    JSONObject rmdt = null;

                    rmdt = new JSONObject(jsonS);
                    JSONSenderVolley(urlrs, rmdt);
                } catch (JSONException e) {

                }
                Log.d("-jsnresponse enter---", "" + jsonS);
            }
        });

        addP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                from = "add";
                if (hasProduct==true){
                    final AlertDialog.Builder builder = new AlertDialog.Builder(UpdatePosition.this);
                    builder.setTitle("Info");
                    builder.setMessage("Product already exists at this position. To replace it, please select \"CHANGE ITEM\".");

                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    Intent intent = new Intent(UpdatePosition.this, PosPrdSearch.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                from = "change";
                if (hasProduct==true){

                    Intent intent = new Intent(UpdatePosition.this, PosPrdSearch.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(UpdatePosition.this);
                    builder.setTitle("Info");
                    builder.setMessage("There is no Product at this position. Please select \"ADD ITEM\".");

                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        /*DelLocB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String jsonDel = "{\"id\":\""+ poID+"\"}";
                Log.d("----jsnresponse delete-", "" + jsonDel);

                //trigger[asycount]=new SendJsonDataToServer1().execute(String.valueOf(water_status));
                urlrs ="http://"+domain_name+":"+port+"/InventoryApp/del_position/";
                // String urlrs= "https://jtsha.in/service/validate_web";

                // try {
                try {
                    rmdt = new JSONObject(jsonDel.toString());
                } catch (JSONException e) {

                }

                final AlertDialog.Builder builder = new AlertDialog.Builder(UpdatePosition.this);
                builder.setTitle("Info");
                builder.setMessage("Do you want to Delete ??");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        JSONSenderVolleyD(urlrs, rmdt);
                        Intent intent = new Intent(UpdatePosition.this, Positions.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Not now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
*/
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdatePosition.this, Positions.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    public void VolleyProdPos(String url, final JSONObject json)
    {
        Log.d("---url-----", "---"+url);
        Log.d("555555", "00000000"+json.toString());

        //RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(" ", response.toString());

                        Log.d("---------", "---"+response.toString());

                        JSONObject responseJSON = null;
                        JSONObject jsonReq;
                        try {
                            responseJSON = new JSONObject(String.valueOf(response));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            JSONArray new_array1;
                            new_array1 = responseJSON.getJSONArray("product_position");
                            Log.d( " Array", " : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String positionS = new_array1.getJSONObject(i).getString("position");
                                quantityS = new_array1.getJSONObject(i).getString("quantity");
                                quantityidS = new_array1.getJSONObject(i).getString("quantityid");
                                String productidS = new_array1.getJSONObject(i).getString("productid");
                                productnameS = new_array1.getJSONObject(i).getString("productname");
                            }

                            product.setText(productnameS);
                            hasProduct = true;
                            Log.d( " hasProduct", " : " + hasProduct);
                            QtyP.setText(quantityS);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(" Error---------", "Tabitha: " + String.valueOf(error));
                Log.d("my test error-----","Tabitha: " + String.valueOf(error));

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

    public void JSONSenderVolley(String url, final JSONObject json)
    {
        Log.d("---url-----", "---"+url);
        Log.d("555555", "00000000"+json.toString());

        //RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(" ", response.toString());

                        Log.d("---------", "---"+response.toString());

                        try {
                            String errorCode = response.getString("error_code");
                            String errorDesc = response.getString("error_desc");
                            Log.d("errorCode","" + errorCode+"--" + errorDesc);

                            final AlertDialog.Builder builder = new AlertDialog.Builder(UpdatePosition.this);
                            builder.setTitle("Info");
                            builder.setMessage(errorDesc);

                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

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
//        AppController.getInstance().addToRequestQueue(jsonObjReq,tag_json_obj);
    }

    public void getStatus() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url=" http://"+domain_name+":"+port+"/InventoryApp/get_status/";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("hello :", response.toString());
                        s = true;
                        if( s==true && w == true){
                            dialog_progress.hide();
                        }
                        JSONObject responseJSON = null;
                        JSONObject jsonReq;
                        try {
                            responseJSON = new JSONObject(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            Log.d( " Array", " response ->  " + response);
                            JSONArray new_array1;
                            List<String> idL = new ArrayList<String>();
                            List<String> statusNameL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("status_details");
                            Log.d( " Array", " requisition : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String idS = new_array1.getJSONObject(i).getString("id");
                                String statusNameS = new_array1.getJSONObject(i).getString("statusname");
                                idL.add(idS);
                                statusNameL.add(statusNameS);
                            }

                            sIdA = new String[idL.size()];
                            statusNameA = new String[statusNameL.size()];

                            for (int l = 0; l < statusNameL.size(); l++) {

                                sIdA[l] = idL.get(l);
                                statusNameA[l] = statusNameL.get(l);
                                Log.d("sId ", sIdA[l]);
                                Log.d("statusName ", statusNameA[l]);
                            }
                            ArrayAdapter<String> statusA= new ArrayAdapter<String>(UpdatePosition.this,android.R.layout.simple_spinner_item, statusNameA);
                            statusA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            statusULE.setAdapter(statusA);

                            Log.d(" obtained statusId ","from adapter-- " + poStatus);
                            for (int n = 0; n < statusNameL.size(); n++) {
                                if (statusNameA[n].contentEquals(poStatus)) {
                                    statusULE.setSelection(n);
                                    Log.d(" statusId ", statusNameA[n]);
                                }
                                else {
                                    Log.d(" statusId else", statusNameA[n]);
                                }
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
                Log.d("hello1 ","error.......");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void getWH() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url="http://"+domain_name+":"+port+"/InventoryApp/get_warehouse/";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response.substring(0,500));
                        Log.d("hello :", response.toString());
                        w = true;
                        if( s==true && w == true){
                            dialog_progress.hide();
                        }
                        JSONObject responseJSON = null;
                        JSONObject jsonReq;
                        try {
                            responseJSON = new JSONObject(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            Log.d( " Array", " response ->  " + response);
                            JSONArray new_array1;
                            List<String> idL = new ArrayList<String>();
                            List<String> WHNameL = new ArrayList<String>();

                            new_array1 = responseJSON.getJSONArray("warehouse_details");
                            Log.d( " Array", " requisition : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String idS = new_array1.getJSONObject(i).getString("id");
                                String warehouseNameS = new_array1.getJSONObject(i).getString("warehousename");

                                idL.add(idS);
                                WHNameL.add(warehouseNameS);
                            }
                            widA = new String[idL.size()];
                            WHNameA = new String[idL.size()];

                            for (int l = 0; l < idL.size(); l++) {
                                widA[l] = idL.get(l);
                                WHNameA[l] = WHNameL.get(l);
                                Log.d("id ", widA[l]);
                                Log.d("WHName ", WHNameA[l]);
                            }
                            ArrayAdapter<String> whA= new ArrayAdapter<String>(UpdatePosition.this,android.R.layout.simple_spinner_item, WHNameA);
                            whA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            WHSp.setAdapter(whA);

                            Log.d(" obtained statusId ","from adapter-- " + poWH);
                            for (int n = 0; n < WHNameL.size(); n++) {
                                if (WHNameA[n].contentEquals(poWH)) {
                                    WHSp.setSelection(n);
                                    Log.d(" WH Id ", WHNameA[n]);
                                }
                                else {
                                    Log.d(" WH Id else", WHNameA[n]);
                                }
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
                Log.d("hello1 ","error.......");

            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void JSONSenderVolleyD(String url, final JSONObject json)
    {
        Log.d("---url-----", "---"+url);
        Log.d("JSON", "string ---" +json.toString());

        //RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(" ", response.toString());
                        Log.d("---------", "---"+response.toString());
                        try {
                            String errorCode = response.getString("error_code");
                            String errorDesc = response.getString("error_desc");
                            Log.d("errorCode","-------" + errorCode+"--");

                            final AlertDialog.Builder builder = new AlertDialog.Builder(UpdatePosition.this);
                            builder.setTitle("Info");
                            builder.setMessage(errorDesc);

                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    dialogInterface.dismiss();
                                }
                            });

                            AlertDialog dialog = builder.create();
                            dialog.show();
                            /*if (errorCode.contentEquals("0")){

                                Log.d(" Position Deleted ","Successfully... :)");
                                Intent intent = new Intent(UpdatePosition.this, Positions.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                            else {
                                Log.d("error-----"," Position NOT deleted Successfully!!!!! ");
                              }*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(" Error---------", "Tabitha: " + String.valueOf(error));
                Log.d("my test error-----","Tabitha: " + String.valueOf(error));

            }
        }) {
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

            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
        //getRequestQueue().add(req);
    }
}
