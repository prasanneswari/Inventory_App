package com.example.shekinah.inventory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.shekinah.inventory.JobRolesMenu.val;

public class Return_main extends AppCompatActivity {
    public static boolean user_product= false;
    RequestQueue phoneque;
    ProgressBar progressBar;
    ArrayList<HashMap<String, String>> contactList;
    private ListView lv;
    static String rtnid="";

    Button adminh,userr,userh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_main);
//        adminh=(Button) findViewById(R.id.adhid);
//        userr=(Button) findViewById(R.id.usrid);
//        userh=(Button) findViewById(R.id.ushid);
      /*  TextView shrtrm_return=(TextView)findViewById(R.id.srttrmid);
        TextView lngtrm_return=(TextView)findViewById(R.id.lngtrmid);*/
        //   http://cld003.jts-prod.in:20105/InventoryApp/get_returntypes/
        contactList = new ArrayList<>();
        progressBar=(ProgressBar)findViewById(R.id.proid);
        lv = (ListView) findViewById(R.id.lstid);
        get_sun_rs_dt("http://cld003.jts-prod.in:20105/InventoryApp/get_returntypes/");

        /*adminh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                val=1;
            }
        });




        userr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                val=2;
            }
        });
        userh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                val=3;
            }
        });*/

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                user_product=true;
                //  poslist = position;
                // contact.get("second");

                if(val==1){
                    System.out.println(contactList.get(position).get("returnid"));
                    System.out.println(contactList.get(position).get("returnname"));
                    rtnid=contactList.get(position).get("returnid");

                    Intent intent = new Intent(Return_main.this, admin_history.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else if( val==2){
                    System.out.println(contactList.get(position).get("returnid"));
                    System.out.println(contactList.get(position).get("returnname"));
                    rtnid=contactList.get(position).get("returnid");

                    Intent intent = new Intent(Return_main.this, Return_user.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else if(val==3){
                    System.out.println(contactList.get(position).get("returnid"));
                    System.out.println(contactList.get(position).get("returnname"));
                    rtnid=contactList.get(position).get("returnid");

                    Intent intent = new Intent(Return_main.this, user_history.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

                //  add_espdtdialog();


            }

        });

       /* shrtrm_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_product=true;
                Intent intent = new Intent(Return_main.this, Return_user.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        lngtrm_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_product=false;

                Intent intent = new Intent(Return_main.this, Return_user.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });*/
    }
    public void get_sun_rs_dt(String urlstr){
        progressBar.setVisibility(View.VISIBLE);
        // final String url ="http://cld002.jts-prod.in/service/get_cust_support_num";
        final String url =  urlstr;
        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        JSONArray login_jarray;

                        try {
                            Log.d("....iam in on post", "....check it out...2");

                            login_jarray = response.getJSONArray("return_type");
                /*if(new_array!=null){
                    mPrefs1.edit().putString("mylog", jstr).commit();}
*/
                            Log.d("...arraysize...", String.valueOf(login_jarray.length()));

                            //  logindt.clear();
                            //   {"get_returns":[{"detailsid":67,"productid":1,"productname":"Pen","quantityissued":0,"quantityrequested":10,
                            // "requisitionid":20,"returnid":1,"returntype":"short term","status":"Not Issued"}
                            for (int i = 0, count = login_jarray.length(); i < count; i++) {

                                rtnid = login_jarray.getJSONObject(i).getString("returnid");

                                String returnname = login_jarray.getJSONObject(i).getString("returnname");
                                HashMap<String, String> contact = new HashMap<>();

                                // adding each child node to HashMap key => value
                                contact.put("returnid", rtnid);
                                contact.put("returnname", returnname);

                                // adding contact to contact list
                                contactList.add(contact);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // String siteUrl_ = "http://" + exterip+"/"+roomswitch[position] + "/ON";
                        //  Toast.makeText(getContext(),siteUrl_, Toast.LENGTH_LONG).show();

                        //   Toast.makeText(getContext(), gextIpAdd, Toast.LENGTH_LONG).show();
                        // (new MainActivity.ParseURL()).execute(new String[]{siteUrl_});
                        lv = (ListView) findViewById(R.id.lstid);

                        ListAdapter adapter = new SimpleAdapter(
                                Return_main.this, contactList,
                                R.layout.returnmain_listview, new String[]{"returnid", "returnname"}, new int[]{R.id.retrnid,
                                R.id.rtnnmid});
                        progressBar.setVisibility(View.GONE);
                        lv.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", String.valueOf(error));
                        Toast.makeText(Return_main.this, "sorry no data avilable", Toast.LENGTH_SHORT).show();
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
}
