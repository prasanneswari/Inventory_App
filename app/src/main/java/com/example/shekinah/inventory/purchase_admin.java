package com.example.shekinah.inventory;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
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

import static android.content.ContentValues.TAG;

public class purchase_admin extends AppCompatActivity {
    ListView rtrnval;
    Button orderbtn,cancle,history;
    Boolean respo = false;
    EditText switchid_col,swtchname_col;
    // String []addnw;
    JSONArray schad_jarrayad;
    RequestQueue sch_RequestQueue;
    static List<String> detail_add=new ArrayList<String>();
    static List<String> requsteddt=new ArrayList<String>();
    static List<Boolean> check_select=new ArrayList<Boolean>();
    static List<String> recive_qty=new ArrayList<String>();
    static List<String> need_send=new ArrayList<String>();
    static List<String> ednw=new ArrayList<String>();
    public someadpter sadapter;
    RequestQueue smRequestQueue;
    int position1=0;
    static RequestQueue phoneque;
    ProgressBar progressBar;
    static  ArrayList<HashMap<String, String>> contactList;
    int inli=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_admin);
        orderbtn = (Button) findViewById(R.id.orderid);
        progressBar=(ProgressBar)findViewById(R.id.progid);

        rtrnval = (ListView) findViewById(R.id.lstid);

        recive_qty.clear();
        need_send.clear();
        ednw.clear();

        contactList = new ArrayList<>();
        Purchase_Reqisition("http://cld003.jts-prod.in:20105/InventoryApp/get_requestedpurchases/");
       /* for (int i = 0; i < 6; i++) {
            check_select.add(false);

            Random random = new Random();
            // generate a random integer from 0 to 899, then add 100
            int x = random.nextInt(10);
            Log.d("----Random", String.valueOf(x));
            HashMap<String, String> contact = new HashMap<>();
            // adding each child node to HashMap key => value
            contact.put("pid", String.valueOf(i));
            contact.put("pname", "vinay");
            contact.put("qtake", String.valueOf(10));
            ednw.add(String.valueOf(x));
            // recive_qty.add();
            need_send.add(String.valueOf(10 - x));
            contact.put("status", "status");
            contactList.add(contact);

        }
        sadapter = new someadpter(purchase_admin.this, ednw);
        rtrnval.setAdapter(sadapter);*/


      /*  lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                position1=position;
                add_espdtdialog();


            }

        });*/
  /*      lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

              //  add_espdtdialog();

            }

        });*/

        // http://cld003.jts-prod.in:20105/InventoryApp/add_purchaseorders/




        orderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                requsteddt.clear();
                detail_add.clear();
                for(int i=0;i<ednw.size();i++){
                    if(check_select.get(i)){
                        requsteddt.add(ednw.get(i));
                        detail_add.add(contactList.get(i).get("purchasedetailsid"));



                    }
                }
                //   detail_add requsteddt


                String  jsonval=  returnedit_jsonstr(detail_add,requsteddt);
                Toast.makeText(getApplicationContext(), jsonval, Toast.LENGTH_LONG).show();



                Log.d("----jsnresponse ", "---" + jsonval);

                //trigger[asycount]=new SendJsonDataToServer1().execute(String.valueOf(water_status));
                String urlrs = "http://cld003.jts-prod.in:20105/InventoryApp/add_purchaseorders/";
                // String urlrs= "https://jtsha.in/service/validate_web";
                JSONObject rmdt = null;
                // try {
                try {
                    rmdt = new JSONObject(jsonval);
                } catch (JSONException e) {
                }
                Log.d("----jsnresponse", "---" + jsonval);
                JSONSenderVolley(urlrs, rmdt);

            }
        });
    }



    public void Purchase_Reqisition(String urlstr){
        progressBar.setVisibility(View.VISIBLE);
        // final String url ="http://cld002.jts-prod.in/service/get_cust_support_num";
        final String url =  urlstr;
        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("Response", String.valueOf(response));
                        dev_respons(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("Error.Response", String.valueOf(error));
                        Toast.makeText(purchase_admin.this, "no data avilable", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        addToRequestQueuevc(getRequest);
    }
    public <T> void addToRequestQueuevc(Request<T> req) {
        if (phoneque == null) {
            phoneque = Volley.newRequestQueue(getApplicationContext());
        }
        phoneque.add(req);
    }


    public void add_espdtdialog()
    {
        final String[] finalva = {""};
        final Dialog openDialog = new Dialog(purchase_admin.this,R.style.DialogTheme);

        openDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //dialog.setTitle("please enter room names");
        openDialog.setContentView(R.layout.quntity_edit);
        openDialog.setCanceledOnTouchOutside(false);

        final TextView quantity_take =(TextView) openDialog.findViewById(R.id.qntyid);
        final TextView quantity_return =(TextView) openDialog.findViewById(R.id.qtrnid);
        final TextView quantity_bal =(TextView) openDialog.findViewById(R.id.qntbalid);
        Button incrz =(Button)openDialog.findViewById(R.id.incrid);
        Button decrz =(Button)openDialog.findViewById(R.id.decreid);
        Button svbtn =(Button)openDialog.findViewById(R.id.saveid);
        Button cancelbtn =(Button)openDialog.findViewById(R.id.canclid);
        // {"pid", "pname","qtake","qreturn","qtybal"}

     /*   contact.put("qtake", String.valueOf(10));
        ednw.add(String.valueOf(x));
        // recive_qty.add();
        need_send.add(String.valueOf(10 - x));
        contactList.add(contact);*/
        System.out.println(contactList.get(position1).get("qtake"));

        quantity_take.setText(contactList.get(position1).get("qtake"));
        quantity_return.setText(ednw.get(position1));
        quantity_bal.setText(need_send.get(position1));
        final int[] i = {Integer.parseInt(contactList.get(position1).get("qtybal"))};
        final int j = Integer.parseInt(contactList.get(position1).get("qtybal"));
        incrz.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub



                if(i[0] !=0&&i[0]>=0) {
                    --i[0];

                    quantity_bal.setText(String.valueOf(i[0]));
                    finalva[0] =String.valueOf(i[0]);
                }

                // openDialog.dismiss();
            }

            // Toast.makeText(getApplicationContext(), "please fill all details", Toast.LENGTH_LONG).show();

        });
        openDialog.show();
        decrz.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(i[0]<=j) {
                    ++i[0];

                    quantity_bal.setText(String.valueOf(i[0]));
                    finalva[0] =String.valueOf(i[0]);
                }

                //openDialog.dismiss();
            }

            // Toast.makeText(getApplicationContext(), "please fill all details", Toast.LENGTH_LONG).show();

        });
        openDialog.show();

        svbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ednw.set(position1,quantity_return.getText().toString());
                need_send.set(position1,quantity_bal.getText().toString());
                rtrnval.setAdapter(sadapter);
               /* String json= returnedit_jsonstr(contactList.get(poslist).get("quantityissued"),finalva[0]);
                Toast.makeText(getApplicationContext(), json, Toast.LENGTH_LONG).show();*/
                openDialog.dismiss();
            }

            // Toast.makeText(getApplicationContext(), "please fill all details", Toast.LENGTH_LONG).show();

        });
        openDialog.show();

        cancelbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                openDialog.dismiss();

                //  Toast.makeText(getApplicationContext(), spinner_item + " - " + edittext1.getText().toString().trim(), Toast.LENGTH_LONG).show();
            }
        });
        openDialog.show();
    }
    public void dev_respons(JSONObject response) {
        progressBar.setVisibility(View.GONE);
        JSONArray login_jarray;
        contactList.clear();
        check_select.clear();
        ednw.clear();
        need_send.clear();
        try {
            Log.d("....iam in on post", "....check it out...2");

            login_jarray = response.getJSONArray("requested_purchases");
                /*if(new_array!=null){
                    mPrefs1.edit().putString("mylog", jstr).commit();}
*/
            Log.d("...arraysize...", String.valueOf(login_jarray.length()));

            //  logindt.clear();
            //   {"get_returns":[{"detailsid":67,"productid":1,"productname":"Pen","quantityissued":0,"quantityrequested":10,
            // "requisitionid":20,"returnid":1,"returntype":"short term","status":"Not Issued"}
            for (int i = 0, count = login_jarray.length(); i < count; i++) {

                String detailid = login_jarray.getJSONObject(i).getString("purchasedetailsid");
                Log.d("detailid",detailid);
                String productid = login_jarray.getJSONObject(i).getString("purchaseorderid");
                Log.d("productid",productid);

                String prodtnm = login_jarray.getJSONObject(i).getString("productname");
                Log.d("prodtnm",prodtnm);

                String quantityreceived = login_jarray.getJSONObject(i).getString("quantityreceived");
                Log.d("quantityreceived",quantityreceived);

                String qnttreq = login_jarray.getJSONObject(i).getString("quantityrequested");
                Log.d("qnttreq",qnttreq);

//                String reqstid = login_jarray.getJSONObject(i).getString("requisitionid");
                String status = login_jarray.getJSONObject(i).getString("status");
                Log.d("status",status);

                check_select.add(false);
                ednw.add(quantityreceived);

                HashMap<String, String>contact= new HashMap<>();
                // adding each child node to HashMap key => value
                contact.put("purchasedetailsid",detailid);
                contact.put("purchaseorderid",productid);
                contact.put("productname", prodtnm);
                contact.put("quantityreceived", quantityreceived);
                contact.put("quantityrequested", qnttreq);
                contact.put("status", status);
                contact.put("qreturn", String.valueOf(0));
                int  bal= Integer.parseInt(qnttreq)- Integer.parseInt(quantityreceived);
                contact.put("qtybal",String.valueOf(bal));
                need_send.add(String.valueOf(bal));
                // adding contact to contact list
                contactList.add(contact);
            }

          /*  ListAdapter adapter = new SimpleAdapter(
                    purchase_admin.this, contactList,
                    R.layout.retun_user_store, new String[]{"purchaseorderid",
                    "quantityissued","qreturn","qtybal"}, new int[]{R.id.pid, R.id.qntakeid, R.id.qntyreturnid, R.id.qntybalid});
*/             sadapter = new someadpter(purchase_admin.this, ednw);
            rtrnval.setAdapter(sadapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void JSONSenderVolley(String url, final JSONObject json)
    {
        progressBar.setVisibility(View.VISIBLE);

        Log.d("---url-----", "---"+url);
        Log.d("555555", "00000000"+json.toString());

        //RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, json,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("---------", "---"+response.toString());

                        Purchase_Reqisition("http://cld003.jts-prod.in:20105/InventoryApp/get_requestedpurchases/");
                        // dev_respons(response);



                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "connection error ", Toast.LENGTH_LONG).show();
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

            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());

        }
        sch_RequestQueue.add(req);
        //getRequestQueue().add(req);
    }

    // {"purchasedetailsid":"[3,4]","quantityordered":"[10,20]"}
    public  String returnedit_jsonstr(List<String> usr,List<String> devid) {
        // {"userid":"10","returnid":"1"}
        JSONObject post_dict = new JSONObject();
        try {
            post_dict.put("purchasedetailsid", usr);
            post_dict.put("quantityordered", devid);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return (String.valueOf(post_dict));
    }
}




