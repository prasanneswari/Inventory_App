package com.example.shekinah.inventory;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.shekinah.inventory.MainActivity.domain_name;
import static com.example.shekinah.inventory.MainActivity.port;
import static com.example.shekinah.inventory.JobRolesMenu.eid;

public class Employees extends AppCompatActivity {

    public Handler handler;
    MaterialSearchView searchView;
    ListView ListEmp;
    String [] idA, eNameA, eDeptA, eRoleA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);

        Button addB = (Button) findViewById(R.id.addEmp);
        Button Cancel = (Button) findViewById(R.id.cancelEmp);
        ListEmp = (ListView)findViewById(R.id.listEmp);
        //httpRequest1();
        this.handler = new Handler();


       /* Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));*/

        ///
       /* lstviewP = (ListView)findViewById(R.id.prdView);
        ArrayAdapter adapter = new ArrayAdapter(Products.this,android.R.layout.simple_expandable_list_item_1, lstSource);
        lstviewP.setAdapter(adapter);
*/
        searchView = (MaterialSearchView)findViewById(R.id.search_view);
/*
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener(){
            @Override
            public void onSearchViewShown(){

            }

            @Override
            public void onSearchViewClosed(){

                lstviewP = (ListView)findViewById(R.id.prdView);
                ArrayAdapter adapter = new ArrayAdapter(Products.this,android.R.layout.simple_expandable_list_item_1, lstSource);
                lstviewP.setAdapter(adapter);
            }
        });*/

        /*searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != null && !newText.isEmpty()){
                    List<String> lstfound = new ArrayList<String>();
                    for (String item:productnameA) {
                        if (item.contains(newText))
                            lstfound.add(item);
                    }

                    ArrayAdapter adapter = new ArrayAdapter(Products.this,android.R.layout.simple_expandable_list_item_1, lstfound);
                    lstviewP.setAdapter(adapter);
                }
                else{
                    ArrayAdapter adapter = new ArrayAdapter(Products.this,android.R.layout.simple_expandable_list_item_1, lstSource);
                    lstviewP.setAdapter(adapter);
                }
                return true;
            }
        });
*/

        addB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Employees.this, AddEmployee.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Employees.this, Admin.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        r.run();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_item,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }


    final Runnable r = new Runnable() {
        public void run() {
            httpRequest1();
            // handler.postDelayed(this, 150000);
            // gameOver();

        }
    };

    public void httpRequest1() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url=" http://"+domain_name+":"+port+"/InventoryApp/get_employee/";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("hello :", response.toString());
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
                            List<String> eNameL = new ArrayList<String>();
                            List<String> eDeptL = new ArrayList<String>();
                            List<String> eRoleL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("user_details");
                            Log.d( " Array", " : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String idS = new_array1.getJSONObject(i).getString("id");
                                String eNameS = new_array1.getJSONObject(i).getString("fullname");
                                String eDeptS = new_array1.getJSONObject(i).getString("department");
                                String eRoleS = new_array1.getJSONObject(i).getString("jobrole");
                                idL.add(idS);
                                eNameL.add(eNameS);
                                eDeptL.add(eDeptS);
                                eRoleL.add(eRoleS);
                            }
                            idA = new String[idL.size()];
                            eNameA = new String[eNameL.size()];
                            eDeptA = new String[eDeptL.size()];
                            eRoleA = new String[eRoleL.size()];

                            for (int l = 0; l < eNameL.size(); l++) {

                                idA[l] = idL.get(l);
                                eNameA[l] = eNameL.get(l);
                                eDeptA[l] = eDeptL.get(l);
                                eRoleA[l] = eRoleL.get(l);
                                Log.d("id ", idA[l]);
                                Log.d("eName ", eNameA[l]);
                                Log.d("eDept ", eDeptA[l]);
                                Log.d("eRole ", eRoleA[l]);
                            }

                            EmployeesAdapter reqAdapter = new EmployeesAdapter(Employees.this,idA, eNameA, eDeptA, eRoleA);
                            ListEmp.setAdapter(reqAdapter);

                            //////////////////////////////////////////////////////


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
}
