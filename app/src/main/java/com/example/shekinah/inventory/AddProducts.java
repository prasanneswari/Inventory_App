package com.example.shekinah.inventory;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static com.example.shekinah.inventory.MainActivity.domain_name;
import static com.example.shekinah.inventory.MainActivity.port;

public class AddProducts extends AppCompatActivity {

    com.android.volley.RequestQueue sch_RequestQueue;
    private ProgressDialog dialog_progress ;
    boolean typ = false,uom = false, s = false;
    private ImageView imgPreview, imgPreviewL;
    RequestQueue mRequestQueue;
    private static final int CAMERA_REQUEST = 1888;
    private Button btnCapturePicture, btnGetPicture, btnCapturePictureL, btnGetPictureL;
    String [] sIdA, statusNameA, mIdA, materialtypeA, uomIdA, measurementA, yesNo = {"yes", "no"};
    Spinner sellableSp, serialcontrolledSp, batchtrackedSp, typeSp, uomSp, statusSp;
    String pic, encodedImage, encodedImageL, imageid, uid, mid,sid,jsonS, noNPS, nameNPS, descNPS, modelNPS, companyNPS, PackageNPS, purchaseableS, listpriceNPS, tolerancepercentNPS, sellableS, serialcontrolledS, batchtrackedS, standardcostS, reorderlevelS, reorderqtyS, typeS, uomS, statusS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);

        Button addPB = (Button) findViewById(R.id.addP);
        Button Cancel = (Button) findViewById(R.id.addNPC);

        // alertdialog = builder.create();

        //final EditText noNPE = (EditText) findViewById(R.id.pNo);
        btnCapturePicture = (Button) findViewById(R.id.btnCapturePicture);
        imgPreview = (ImageView) findViewById(R.id.imgPreview);
        btnGetPicture = (Button) findViewById(R.id.gtpctrid);
        btnCapturePictureL = (Button) findViewById(R.id.btnCapturePictureL);
        imgPreviewL = (ImageView) findViewById(R.id.imgPreviewL);
        btnGetPictureL = (Button) findViewById(R.id.gtpctridL);

        final EditText nameNPE = (EditText) findViewById(R.id.name);
        final EditText descNPE = (EditText) findViewById(R.id.desc);
        final EditText modelNPE = (EditText) findViewById(R.id.model);
        final EditText companyNPE = (EditText) findViewById(R.id.company);
        final EditText PackageNPE = (EditText) findViewById(R.id.Package);
        final Spinner purchaseableSp = (Spinner)findViewById(R.id.purchaseable);
        final EditText listpriceNPE = (EditText) findViewById(R.id.listprice);
        final EditText tolerancepercentNPE = (EditText) findViewById(R.id.tolerancepercent);
        sellableSp = (Spinner)findViewById(R.id.sellable);
        serialcontrolledSp = (Spinner)findViewById(R.id.serialcontrolled);
        batchtrackedSp = (Spinner)findViewById(R.id.batchtracked);
        final EditText standardcostE = (EditText) findViewById(R.id.standardcost);
        final EditText reorderlevelE = (EditText) findViewById(R.id.reorderlevel);
        final EditText reorderqtyE = (EditText) findViewById(R.id.reorderqty);
        typeSp = (Spinner)findViewById(R.id.type);
        uomSp = (Spinner)findViewById(R.id.uom);
        statusSp = (Spinner)findViewById(R.id.status);

        Button LocB = (Button) findViewById(R.id.btn_loc);
        final Button Btnlogout = (Button) findViewById(R.id.btn_logout);
        // alertdialog = builder.create();
        dialog_progress = new ProgressDialog(AddProducts.this);
        dialog_progress.setMessage("connecting ...");
        dialog_progress.show();
        getUOM();
        getStatus();
        getMaterialType();
        ArrayAdapter<String> yesNoA= new ArrayAdapter<String>(AddProducts.this,android.R.layout.simple_spinner_item, yesNo);
        yesNoA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        purchaseableSp.setAdapter(yesNoA);
        sellableSp.setAdapter(yesNoA);
        serialcontrolledSp.setAdapter(yesNoA);
        batchtrackedSp.setAdapter(yesNoA);

        btnCapturePicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // capture picture
                try {
                    if (nameNPE.getText().toString().length() != 0) {
                        imageid = nameNPE.getText().toString();
                        //  captureImage();
                        sampletest();
                    } else {
                        Toast.makeText(getApplicationContext(), "please enter product name", Toast.LENGTH_LONG).show();
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        btnGetPicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Get picture
                try {
                    if (nameNPE.getText().toString().length() != 0) {
                        imageid = nameNPE.getText().toString();
                        imgPreview.setVisibility(View.VISIBLE);
                        Picasso.with(getApplicationContext()).load("http://cld003.jts-prod.in/InventoryApp/productimage/" + imageid + ".jpg").into(imgPreview);
                        Log.d("Picasso", "http://cld003.jts-prod.in/InventoryApp/productimage/" + imageid + ".jpg");
                    } else {
                        Toast.makeText(getApplicationContext(), "please enter product name", Toast.LENGTH_LONG).show();
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });

        btnCapturePictureL.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // capture picture
                try {
                    if (nameNPE.getText().toString().length() != 0) {
                        imageid = nameNPE.getText().toString();
                        //  captureImage();
                        sampletestL();
                    } else {
                        Toast.makeText(getApplicationContext(), "please enter Position name", Toast.LENGTH_LONG).show();
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        btnGetPictureL.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Get picture
                try {
                    if (nameNPE.getText().toString().length() != 0) {
                        imageid = nameNPE.getText().toString();
                        imgPreviewL.setVisibility(View.VISIBLE);
                        Picasso.with(getApplicationContext()).load("http://cld003.jts-prod.in/InventoryApp/locationimage/" + imageid + ".jpg").into(imgPreviewL);
                        Log.d("Picasso", "http://cld003.jts-prod.in/InventoryApp/locationimage/" + imageid + ".jpg");
                    } else {
                        Toast.makeText(getApplicationContext(), "please enter Position name", Toast.LENGTH_LONG).show();
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });

        addPB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_progress.setMessage("connecting ...");
                dialog_progress.show();
                //noNPS= noNPE.getText().toString();
                nameNPS= nameNPE.getText().toString();
                descNPS= descNPE.getText().toString();
                modelNPS= modelNPE.getText().toString();
                companyNPS= companyNPE.getText().toString();
                PackageNPS= PackageNPE.getText().toString();
                purchaseableS = purchaseableSp.getSelectedItem().toString();
                listpriceNPS= listpriceNPE.getText().toString();
                tolerancepercentNPS= tolerancepercentNPE.getText().toString();
                sellableS = sellableSp.getSelectedItem().toString();
                serialcontrolledS = serialcontrolledSp.getSelectedItem().toString();
                batchtrackedS = batchtrackedSp.getSelectedItem().toString();
                standardcostS= standardcostE.getText().toString();
                reorderlevelS= reorderlevelE.getText().toString();
                reorderqtyS= reorderqtyE.getText().toString();
                typeS = typeSp.getSelectedItem().toString();
                uomS = uomSp.getSelectedItem().toString();
                statusS = statusSp.getSelectedItem().toString();

                for (int l = 0; l < uomIdA.length; l++) {

                    if( measurementA[l] == uomS){
                        uid = uomIdA[l];
                    }
                    Log.d("measurement ", measurementA[l]);
                }

                for (int l = 0; l < mIdA.length; l++) {

                    if( materialtypeA[l] == typeS){
                        mid = mIdA[l];
                    }
                    Log.d("!....", materialtypeA[l]);
                }

                for (int l = 0; l < sIdA.length; l++) {

                    if( statusNameA[l] == statusS){
                        sid = sIdA[l];
                    }
                    Log.d("statusName ", statusNameA[l]);
                }
                //"productno":"" + noNPS + "",
                jsonS = "{\"productno\":\"\",\"productname\":\"" + nameNPS + "\",\"productdescription\":\"" + descNPS + "\",\"productmodel\":\"" + modelNPS + "\",\"productcompany\":\"" + companyNPS + "\",\"productpackage\":\"" + PackageNPS + "\",\"productpurchasable\":\""+purchaseableS+"\",\"productpurchaselistprice\":\"" + listpriceNPS + "\",\"productpricetolerancepercent\":\""+tolerancepercentNPS+"\",\"productsellable\":\"" + sellableS + "\",\"productserialcontrolled\":\"" + serialcontrolledS + "\",\"productbatchtracked\":\"" + batchtrackedS + "\",\"standardcost\":\"" + standardcostS + "\",\"reorderlevel\":\"" + reorderlevelS + "\",\"reorderquantity\":\"" + reorderqtyS + "\",\"producttype\":\"" + mid + "\",\"productuom\":\"" + uid + "\",\"statusid\":\"" + sid + "\",\"productimage\":\"" + encodedImage + "\",\"locationimage\":\"" + encodedImageL + "\"}";
                Log.d("-jsnresponse add---",""+jsonS);
                String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/add_product/";
                try {
                    JSONObject rmdt = null;
                    rmdt = new JSONObject(jsonS);
                    JSONSenderVolley1(urlrs, rmdt);
                } catch (JSONException e) {

                }
                Log.d("-jsnresponse enter---", "" + jsonS);
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddProducts.this, Products.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    /*public void JSONSenderVolley(String url, final JSONObject json) {

        //RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        Log.d("---------", "---" + response.toString());

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "connection error ", Toast.LENGTH_LONG).show();
                // mySwipeRefreshLayout.setRefreshing(false);

            }
        }) {

            *//**
             * Passing some request headers
             * *//*
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        // Adding request to request queue
        // jsonObjReq.setTag("");
        addToRequestQueue1(jsonObjReq);
    }*/

    public <T> void addToRequestQueue1(Request<T> req) {
        if (mRequestQueue == null) {

            mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        }
        mRequestQueue.add(req);
//        getRequestQueue().add(req);
    }

    private void sampletest() {

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
        pic = "product";
    }

    private void sampletestL() {

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
        pic = "location";
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (pic.contentEquals("product")) {

            if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
                imgPreview.setVisibility(View.VISIBLE);
                Bitmap mphoto = (Bitmap) data.getExtras().get("data");


                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    mphoto.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] b = baos.toByteArray();

                    encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                    Log.d("-----encodedImage", encodedImage);
                    byte[] g = Base64.decode(encodedImage, Base64.DEFAULT);
                    Bitmap bitmap1 = BitmapFactory.decodeByteArray(g, 0, g.length);
                    imgPreview.setImageBitmap(bitmap1);

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
        }
        else if(pic.contentEquals("location")){
            if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
                imgPreviewL.setVisibility(View.VISIBLE);
                Bitmap mphoto = (Bitmap) data.getExtras().get("data");


                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    mphoto.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] b = baos.toByteArray();

                    encodedImageL = Base64.encodeToString(b, Base64.DEFAULT);
                    Log.d("-----encodedImage", encodedImageL);
                    byte[] g = Base64.decode(encodedImageL, Base64.DEFAULT);
                    Bitmap bitmap1 = BitmapFactory.decodeByteArray(g, 0, g.length);
                    imgPreviewL.setImageBitmap(bitmap1);

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }
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
                        if( s==true && uom == true && typ==true){
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
                            //requisition = responseJSON.getString("requisition");
                            //Log.d( " String", " requisition : " + requisition);
                            JSONArray new_array1;
                            List<String> idL = new ArrayList<String>();
                            List<String> statusNameL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("status_details");
                            //new_array1 = responseJSON.getJSONArray("fields");
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
                            ArrayAdapter<String> statusA= new ArrayAdapter<String>(AddProducts.this,android.R.layout.simple_spinner_item, statusNameA);
                            statusA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            statusSp.setAdapter(statusA);
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

    public void getMaterialType() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url=" http://"+domain_name+":"+port+"/InventoryApp/get_materialtype/";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("hello :", response.toString());
                        typ = true;
                        if( s==true && uom == true && typ==true){
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
                            //requisition = responseJSON.getString("requisition");
                            //Log.d( " String", " requisition : " + requisition);
                            JSONArray new_array1;
                            List<String> idL = new ArrayList<String>();
                            List<String> materialL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("materialtype_details");
                            //new_array1 = responseJSON.getJSONArray("fields");
                            Log.d( " Array", " materialtype_details : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String idS = new_array1.getJSONObject(i).getString("id");
                                String materialS = new_array1.getJSONObject(i).getString("materialtype");
                                idL.add(idS);
                                materialL.add(materialS);
                            }

                            mIdA = new String[idL.size()];
                            materialtypeA = new String[materialL.size()];

                            for (int l = 0; l < materialL.size(); l++) {

                                mIdA[l] = idL.get(l);
                                materialtypeA[l] = materialL.get(l);
                                Log.d("mId ", mIdA[l]);
                                Log.d("materialtype ", materialtypeA[l]);
                            }
                            ArrayAdapter<String> materialA= new ArrayAdapter<String>(AddProducts.this,android.R.layout.simple_spinner_item, materialtypeA);
                            materialA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            typeSp.setAdapter(materialA);

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

    public void getUOM() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url=" http://"+domain_name+":"+port+"/InventoryApp/get_uom/";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("hello :", response.toString());
                        uom = true;
                        if( s==true && uom == true && typ==true){
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
                            //requisition = responseJSON.getString("requisition");
                            //Log.d( " String", " requisition : " + requisition);
                            JSONArray new_array1;
                            List<String> idL = new ArrayList<String>();
                            List<String> measurementL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("uom_details");
                            Log.d( " Array", " uom_details : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String idS = new_array1.getJSONObject(i).getString("id");
                                String measurementS = new_array1.getJSONObject(i).getString("measurementname");
                                idL.add(idS);
                                measurementL.add(measurementS);
                            }
                            uomIdA = new String[idL.size()];
                            measurementA = new String[measurementL.size()];

                            for (int l = 0; l < measurementL.size(); l++) {
                                uomIdA[l] = idL.get(l);
                                measurementA[l] = measurementL.get(l);
                                Log.d("uomId ", uomIdA[l]);
                                Log.d("measurement ", measurementA[l]);
                            }
                            ArrayAdapter<String> uomA= new ArrayAdapter<String>(AddProducts.this,android.R.layout.simple_spinner_item, measurementA);
                            uomA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            uomSp.setAdapter(uomA);

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

    public void JSONSenderVolley1(String url, final JSONObject json)
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
                        dialog_progress.hide();
                        Log.d("---------", "---"+response.toString());

                        try {
                            final String errorCode = response.getString("error_code");
                            String errorDesc = response.getString("error_desc");
                            Log.d("errorCode","" + errorCode+"--" + errorDesc);

                            final AlertDialog.Builder builder = new AlertDialog.Builder(AddProducts.this);
                            builder.setTitle("Info");
                            builder.setMessage(errorDesc);

                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    if (errorCode.contentEquals("1")){
                                        Intent intent = new Intent(AddProducts.this, Products.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);

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

    public <T> void addToRequestQueue(Request<T> req) {
        if (sch_RequestQueue == null) {

            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
        //getRequestQueue().add(req);
    }
}
