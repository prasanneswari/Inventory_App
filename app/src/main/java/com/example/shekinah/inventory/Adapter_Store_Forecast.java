package com.example.shekinah.inventory;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.shekinah.inventory.MainActivity.domain_name;
import static com.example.shekinah.inventory.MainActivity.port;

public class Adapter_Store_Forecast extends ArrayAdapter<String> {

    private Context context;
    private String[] id;
    private String[] Name;
    private String[] date, Qty, qtygiven, Projectname;
    public static String urID, urStatus;
    public static String urName;
    public static String urDueDate, urqtygiven;
    Point p;
    TextView NameT, idT, dateT, QtyT, qtygivenT, ProjectT;
    List<String> forecastidL = new ArrayList<String>();
    List<String> nameidL = new ArrayList<String>();
    List<String> qtygivenidL = new ArrayList<String>();
    com.android.volley.RequestQueue sch_RequestQueue;


    public Adapter_Store_Forecast(Context context, String[] idS, String[] ProjectS, String[] NameS, String[] DateS, String[] QtyS, String[] qtygivenS) {

        super(context, R.layout.activity_store__forecast, idS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter

        this.context = context;
        this.id = idS;
        this.Projectname = ProjectS;
        this.Name = NameS;
        this.date = DateS;
        this.Qty = QtyS;
        this.qtygiven = qtygivenS;

    }


    public Adapter_Store_Forecast(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter__store__forecast, parent, false);  //Setting content view of xml
        // ImageView imageView=(ImageView)rowView.findViewById(R.id.image);

        //Assigning IDs from xml
        NameT = (TextView) rowView.findViewById(R.id.reqUser);
        idT = (TextView) rowView.findViewById(R.id.reqId);
        dateT = (TextView) rowView.findViewById(R.id.reqDate);
        QtyT = (TextView) rowView.findViewById(R.id.qtyid);
        qtygivenT = (TextView) rowView.findViewById(R.id.qtygiven);
        ProjectT = (TextView) rowView.findViewById(R.id.projectid);


        try {

            //Assigning values from array to individual layouts in list view
            NameT.setText(Name[position]);
            idT.setText(id[position]);
            dateT.setText(date[position]);
            QtyT.setText(Qty[position]);
            qtygivenT.setText(qtygiven[position]);
            ProjectT.setText(Projectname[position]);


        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(getContext(), Store_Forecast.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("id", id[position]);
                intent.putExtra("name", Name[position]);
                intent.putExtra("qtygiven", qtygiven[position]);

                context.startActivity(intent);*/
                urID = id[position];
                urName = Name[position];
                urDueDate = date[position];
                urStatus = Qty[position];
                urqtygiven = qtygiven[position];

                Log.d("Req", " Id :" + urID);
                Log.d("Req", " Name :" + urName);
                Log.d("Req", " DueDate :" + urDueDate);
                Log.d("Req", " Status :" + urStatus);
                Log.d("Req", " urqtygiven :" + urqtygiven);

                popup();


            }
        });

        return rowView;
}
    public void popup(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.update_popup_forecast,
                null);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.setView(dialogLayout, 0, 0, 0, 0);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        WindowManager.LayoutParams wlmp = dialog.getWindow()
                .getAttributes();
        wlmp.gravity = Gravity.CENTER;


       /* final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //dialog.setTitle("please enter room names");
        dialog.setContentView(R.layout.update_popup_forecast);*/

        final Button updatebtn = (Button) dialogLayout.findViewById(R.id.update);
        final Button backbtn = (Button) dialogLayout.findViewById(R.id.cancelB);
        final EditText forecastEdt = (EditText) dialogLayout.findViewById(R.id.forecastEdt);
        final EditText prdEdt = (EditText) dialogLayout.findViewById(R.id.prdEdt);
        final EditText qtygivenEdt = (EditText) dialogLayout.findViewById(R.id.qtygivenEdt);

        builder.setView(dialogLayout);

        forecastEdt.setText(urID);
        prdEdt.setText(urName);
        qtygivenEdt.setText(urqtygiven);


        updatebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //delgroup(grpnm);
                String forecastidS = forecastEdt.getText().toString();
                String nameidS= prdEdt.getText().toString();
                String qtygivenidS= qtygivenEdt.getText().toString();
                Log.d("Req" ," forecastidS :" + forecastidS);
                Log.d("Req" ,"nameidS" + nameidS);
                Log.d("Req" ," qtygivenidS :" + qtygivenidS);
                forecastidL.add(forecastidS);
                nameidL.add(nameidS);
                qtygivenidL.add(qtygivenidS);
                update_post();

                dialog.dismiss();
                  Toast.makeText(getContext(), "value updated", Toast.LENGTH_LONG).show();
            }
        });
        dialog.show();
        backbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                //  Toast.makeText(getApplicationContext(), spinner_item + " - " + edittext1.getText().toString().trim(), Toast.LENGTH_LONG).show();
            }
        });
        dialog.show();


    }
    public void update_post(){
        // String pur_prds = "{\"username\":\"admin\",\"password\":\"admin\"}";
        //String pur_prds = "{\"orderid\":\"" + lID + "\"}";
        String updates = "{\"forecastid\":\"" + forecastidL + "\",\"quantitygiven\":\"" + qtygivenidL + "\"}";
        Log.d("jsnresponse pur_prds111", "---" + updates);
        //trigger[asycount]=new SendJsonDataToServer1().execute(String.valueOf(water_status));
        //String room_ssid_url = "http://cld003.jts-prod.in:5906/AssetTrackerApp/get_rooms_ssid/";
        String pur_url = "http://"+domain_name+":"+port+"/InventoryApp/update_forecast/";
        // String urlrs= "https://jtsha.in/service/validate_web";
        JSONObject lstrmdt = null;
        // try {
        try {
            lstrmdt = new JSONObject(updates);
        } catch (JSONException e) {

        }
        Log.d("jsnresponse....", "---" + updates);
        JSONSenderVolley(pur_url, lstrmdt);
    }

    public void JSONSenderVolley(String pur_url, final JSONObject json)
    {
        Log.d("update_received_post-", "---"+pur_url);
        Log.d("555555", "00000000"+json.toString());

        //RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                pur_url, json,

                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
/*
                        Log.d(Bitmap.Config.TAG, response.toString());
*/
                        Log.d("----update popup values-----", "---"+response.toString());


                        //pur_request(response);
                        //get_room_ssid(response);
                        //startedScanner();
                        //get_spinner_response(response);
                        //  edit_response(response);
//                        try {
//                            login_code = response.getInt("error_code");
//                            String er_discp=response.getString("error_desc");
//
//                            String[] separated = er_discp.split("=");
//                            if(login_code==0){
//                                Toast.makeText(getApplicationContext(), separated[1], Toast.LENGTH_LONG).show();
//                            }else
//                                Toast.makeText(getApplicationContext(), separated[1], Toast.LENGTH_LONG).show();
//
//
//                        } catch (JSONException e) {
//
//                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(" ", "Error: " + error.getMessage());
                //Toast.makeText(getApplicationContext(), "connection error ", Toast.LENGTH_LONG).show();

            }

        }) {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        // Adding request to request queue
        // jsonObjReq.setTag("");
        addToRequestQueue(jsonObjReq);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        if (sch_RequestQueue == null) {


            sch_RequestQueue = Volley.newRequestQueue(getContext());

        }
        sch_RequestQueue.add(req);
        //getRequestQueue().add(req);
    }

}


