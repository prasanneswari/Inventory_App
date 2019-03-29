package com.example.shekinah.inventory;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
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
import java.util.Map;

import static com.example.shekinah.inventory.Return_main.rtnid;

public class user_history extends AppCompatActivity {
    private static final String TAG ="volly" ;
    RequestQueue sch_RequestQueue;
    String jsonval;
    ListView rtrnval;
    Boolean check=false;
    ArrayList<HashMap<String, String>> contactList;
    int poslist=0;
    Dialog openDialog;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history);
        progressBar=(ProgressBar)findViewById(R.id.progid);
        contactList = new ArrayList<>();
        rtrnval=(ListView)findViewById(R.id.rtrnlstid) ;
        // {"userid":"10","returnid":"1"}

        // tmp hash map for single contact

       /* for(int i=0;i<6;i++){
            Random random = new Random();

// generate a random integer from 0 to 899, then add 100
            int x = random.nextInt(10);

            Log.d("----Random", String.valueOf(x));
            HashMap<String, String>contact= new HashMap<>();
        // adding each child node to HashMap key => value
        contact.put("pid", String.valueOf(i));
        contact.put("pname", "vinay");
        contact.put("qtake", String.valueOf(10));
        contact.put("qreturn", String.valueOf(i));
        contact.put("qtybal",String.valueOf(x));
        // adding contact to contact list
        contactList.add(contact);
        }

        ListAdapter adapter = new SimpleAdapter(
                Return_user.this, contactList,
                R.layout.retun_user_store, new String[]{"pid", "pname",
                "qtake","qreturn","qtybal"}, new int[]{R.id.pid,
                R.id.pnamid, R.id.qntakeid, R.id.qntyreturnid, R.id.qntybalid});

        rtrnval.setAdapter(adapter);*/
        get_basic();
      /*  rtrnval.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                poslist = position;
                // contact.get("second");
                // System.out.println(contactList.get(position).get("name"));


                add_espdtdialog();


            }

        });*/

    }
    public void get_basic(){

        //  http://cld003.jts-prod.in:20105/InventoryApp/get_userreturnhistory/




        jsonval = deldev_jsonstr("9",rtnid);


        Log.d("----jsnresponse ", "---" + jsonval);

        //trigger[asycount]=new SendJsonDataToServer1().execute(String.valueOf(water_status));
        String urlrs = "http://cld003.jts-prod.in:20105/InventoryApp/get_userreturnhistory/";
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

    public  String deldev_jsonstr(String usr,String devid) {
        //  {"returnid":"2","userid":"9"}
        // {"userid":"10","returnid":"1"}
        JSONObject post_dict = new JSONObject();
        try {

            post_dict.put("userid", usr);
            post_dict.put("returnid", devid);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return (String.valueOf(post_dict));
    }
    public void dev_respons(JSONObject response) {
        progressBar.setVisibility(View.GONE);
        JSONArray login_jarray;

        try {
            Log.d("....iam in on post", "....check it out...2");

            login_jarray = response.getJSONArray("get_user_return_history");
                /*if(new_array!=null){
                    mPrefs1.edit().putString("mylog", jstr).commit();}
*/
            Log.d("...arraysize...", String.valueOf(login_jarray.length()));

            //  logindt.clear();
            //   {"get_returns":[{"detailsid":67,"productid":1,"productname":"Pen","quantityissued":0,"quantityrequested":10,
            // "requisitionid":20,"returnid":1,"returntype":"short term","status":"Not Issued"}
            for (int i = 0, count = login_jarray.length(); i < count; i++) {

                String quantitychecked = login_jarray.getJSONObject(i).getString("quantitychecked");
                String quantitytaken = login_jarray.getJSONObject(i).getString("quantitytaken");
                String qnttreq = login_jarray.getJSONObject(i).getString("quantityreturned");
                String historyid = login_jarray.getJSONObject(i).getString("historyid");
                // String retrned = login_jarray.getJSONObject(i).getString("returnid");
                String status = login_jarray.getJSONObject(i).getString("status");


                HashMap<String, String>contact= new HashMap<>();
                // adding each child node to HashMap key => value
                contact.put("historyid",historyid);
                contact.put("status",status);
                contact.put("quantitytaken",quantitytaken);
                contact.put("quantitychecked", quantitychecked);
                contact.put("quantityreturned", qnttreq);
                int  bal= Integer.parseInt(quantitytaken)-Integer.parseInt(qnttreq);
                contact.put("qtybal",String.valueOf(bal));
                // adding contact to contact list
                contactList.add(contact);
            }

            ListAdapter adapter = new SimpleAdapter(
                    user_history.this, contactList,
                    R.layout.retun_user_store, new String[]{"historyid", "status",
                    "quantitytaken","quantityreturned","qtybal"}, new int[]{R.id.pid,
                    R.id.pnamid, R.id.qntakeid, R.id.qntyreturnid, R.id.qntybalid});
            rtrnval.setAdapter(adapter);

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

                        if(check){
                            get_basic();
                            openDialog.dismiss();
                            check=false;
                        }

                        dev_respons(response);

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
    public void add_espdtdialog()
    {
        final String[] finalva = {""};
        openDialog = new Dialog(user_history.this,R.style.DialogTheme);

        openDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //dialog.setTitle("please enter room names");
        openDialog.setContentView(R.layout.quntity_edit);
        openDialog.setCanceledOnTouchOutside(false);

        TextView positiotxt =(TextView) openDialog.findViewById(R.id.qposidid);

        TextView quantity_take =(TextView) openDialog.findViewById(R.id.qntyid);
        final ProgressBar progresbr =(ProgressBar) openDialog.findViewById(R.id.prgbrid);

        final TextView quantity_return =(TextView) openDialog.findViewById(R.id.qtrnid);
        final TextView quantity_bal =(TextView) openDialog.findViewById(R.id.qntbalid);
        Button incrz =(Button)openDialog.findViewById(R.id.incrid);
        Button decrz =(Button)openDialog.findViewById(R.id.decreid);
        Button svbtn =(Button)openDialog.findViewById(R.id.saveid);
        Button cancelbtn =(Button)openDialog.findViewById(R.id.canclid);
        // {"pid", "pname","qtake","qreturn","qtybal"}

        System.out.println(contactList.get(poslist).get("quantityissued"));
        positiotxt.setText(contactList.get(poslist).get("position"));

        quantity_take.setText(contactList.get(poslist).get("quantityissued"));
        quantity_return.setText(contactList.get(poslist).get("qreturn"));

        quantity_bal.setText(contactList.get(poslist).get("qtybal"));
        final int[] k = {Integer.parseInt(contactList.get(poslist).get("qreturn"))};
        final int[] i = {Integer.parseInt(contactList.get(poslist).get("qtybal"))};
        final int j = Integer.parseInt(contactList.get(poslist).get("qtybal"));

        incrz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                if(i[0] !=0&&i[0]>=0) {
                    --i[0];

                    ++k[0];
                    quantity_bal.setText(String.valueOf(i[0]));
                    quantity_return.setText(String.valueOf(k[0]));
//                    finalva[0] =String.valueOf(i[0]);
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
                if(k[0]>=0&&i[0]<j) {
                    ++i[0];


                    quantity_return.setText(String.valueOf(k[0]));
                    quantity_bal.setText(String.valueOf(i[0]));
                    k[0]--;
//                    finalva[0] =String.valueOf(i[0]);
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
                progresbr.setVisibility(View.VISIBLE);
                contactList.get(poslist).get("detailsid");
                contactList.get(poslist).get("quantityissued");
                String.valueOf(k[0]);

                jsonval =  request_return(contactList.get(poslist).get("quantityissued"),String.valueOf(k[0]),contactList.get(poslist).get("quantityissued"));

                Log.d("----jsnresponse ", "---" + jsonval);
                check=true;
                //trigger[asycount]=new SendJsonDataToServer1().execute(String.valueOf(water_status));
                String urlrs = "http://cld003.jts-prod.in:20105/InventoryApp/add_returnproducts/";
                // String urlrs= "https://jtsha.in/service/validate_web";
                JSONObject rmdt = null;
                // try {
                try {
                    rmdt = new JSONObject(jsonval);
                } catch (JSONException e) {
                }
                Log.d("----jsnresponse", "---" + jsonval);
                JSONSenderVolley(urlrs, rmdt);

                String json= returnedit_jsonstr(contactList.get(poslist).get("quantityissued"),finalva[0]);
                Toast.makeText(getApplicationContext(), json, Toast.LENGTH_LONG).show();

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
    public  String request_return(String qntkn,String qntrn,String reqdtl) {

        // {"userid":"10","returnid":"1"}
        JSONObject post_dict = new JSONObject();
        try {

            post_dict.put("quantitytaken", "["+ qntkn +"]");
            post_dict.put("quantityreturned", "["+qntrn+"]");
            post_dict.put("requisitiondetailsid", "["+reqdtl+"]");
            post_dict.put("returnby", "9");
            post_dict.put("returntype", rtnid);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return (String.valueOf(post_dict));
    }
    public  String returnedit_jsonstr(String usr,String devid) {

        // {"userid":"10","returnid":"1"}
        JSONObject post_dict = new JSONObject();
        try {

            post_dict.put("quantityissued", usr);
            post_dict.put("qreturn", devid);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return (String.valueOf(post_dict));
    }
  /*  @Override
    public void onBackPressed() {
        Intent intent = new Intent(user_history.this, Return_user.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }*/
}
