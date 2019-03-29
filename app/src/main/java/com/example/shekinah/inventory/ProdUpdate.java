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
import static com.example.shekinah.inventory.ProductAdapter.pName;
import static com.example.shekinah.inventory.ProductAdapter.pID;
import static com.example.shekinah.inventory.ProductAdapter.pDesc;
import static com.example.shekinah.inventory.ProductAdapter.pStatus;

public class ProdUpdate extends AppCompatActivity {

    private ProgressDialog dialog_progress ;
    String jsonS2, jsonS3, prdidS, producttypeS,productuomS,statusId_idS, productpurchasableS, productsellableS, productserialcontrolledS,productbatchtrackedS;
    String pic="", encodedImage, encodedImageL, imageid, sid, uid, mid, urlrs, jsonS="", noNPS, nameNPS, descNPS, modelNPS, companyNPS, PackageNPS, purchaseableS, listpriceNPS, tolerancepercentNPS, sellableS, serialcontrolledS, batchtrackedS, standardcostS, reorderlevelS, reorderqtyS, typeS= "default", uomS="default", statusS="default";
    int midI, sidI, uidI;
    private Button btnCapturePicture, btnGetPicture, btnCapturePictureL, btnGetPictureL;
    private ImageView imgPreview, imgPreviewL;
    private static final int CAMERA_REQUEST = 1888;
    RequestQueue mRequestQueue;
    String [] sIdA, statusNameA, mIdA, materialtypeA, uomIdA, measurementA, idA, productnoA, productnameA, productdescriptionA,productmodelA, productcompanyA,productpackageA,productpurchasableA,productpurchaselistpriceA,productPriceTolerancePercentA,productsellableA,productserialcontrolledA,productbatchtrackedA,standardcostA,reorderlevelA,reorderqtyA,producttypeA,productuomA,statusId_idA, yesNo = {"no", "yes"};
    JSONObject rmdt;
    TextView pPos;
    boolean typ = false,uom = false, s = false;
    EditText noNPE, nameNPE, descNPE, modelNPE, companyNPE, PackageNPE,listpriceNPE, tolerancepercentNPE, standardcostE, reorderlevelE, reorderqtyE;
    com.android.volley.RequestQueue sch_RequestQueue;
    List<String> idTL = new ArrayList<String>();
    List<String> materialL = new ArrayList<String>();
    List<String> idML = new ArrayList<String>();
    List<String> measurementL = new ArrayList<String>();
    List<String> idSL = new ArrayList<String>();
    List<String> statusNameL = new ArrayList<String>();
    Spinner sellableSp, serialcontrolledSp, batchtrackedSp, typeSp, uomSp, statusSp, purchaseableSp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_update);
        Log.d("productId obtained", pID);
        noNPE = (EditText) findViewById(R.id.pNo);
        nameNPE = (EditText) findViewById(R.id.name);
        descNPE = (EditText) findViewById(R.id.desc);
        modelNPE = (EditText) findViewById(R.id.model);
        companyNPE = (EditText) findViewById(R.id.company);
        PackageNPE = (EditText) findViewById(R.id.Package);
        purchaseableSp = (Spinner)findViewById(R.id.purchaseable);
        listpriceNPE = (EditText) findViewById(R.id.listprice);
        tolerancepercentNPE = (EditText) findViewById(R.id.tolerancepercent);
        sellableSp = (Spinner)findViewById(R.id.sellable);
        serialcontrolledSp = (Spinner)findViewById(R.id.serialcontrolled);
        batchtrackedSp = (Spinner)findViewById(R.id.batchtracked);
        standardcostE = (EditText) findViewById(R.id.standardcost);
        reorderlevelE = (EditText) findViewById(R.id.reorderlevel);
        reorderqtyE = (EditText) findViewById(R.id.reorderqty);
        typeSp = (Spinner)findViewById(R.id.type);
        uomSp = (Spinner)findViewById(R.id.uom);
        statusSp = (Spinner)findViewById(R.id.status);
        pPos = (TextView)findViewById(R.id.pPos);
        Button DelLocB = (Button) findViewById(R.id.DLoc);
        final Button Update = (Button) findViewById(R.id.updateLoc);
        Button Cancel = (Button) findViewById(R.id.cancelUL);
        btnCapturePicture = (Button) findViewById(R.id.btnCapturePicture);
        imgPreview = (ImageView) findViewById(R.id.imgPreview);
        btnGetPicture = (Button) findViewById(R.id.gtpctrid);
        btnCapturePictureL = (Button) findViewById(R.id.btnCapturePictureL);
        imgPreviewL = (ImageView) findViewById(R.id.imgPreviewL);
        btnGetPictureL = (Button) findViewById(R.id.gtpctridL);

        dialog_progress = new ProgressDialog(ProdUpdate.this);
        dialog_progress.setMessage("connecting ...");
        dialog_progress.show();
        getUOM();
        getStatus();
        getMaterialType();
        ArrayAdapter<String> yesNoA= new ArrayAdapter<String>(ProdUpdate.this,android.R.layout.simple_spinner_item, yesNo);
        yesNoA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        purchaseableSp.setAdapter(yesNoA);
        sellableSp.setAdapter(yesNoA);
        serialcontrolledSp.setAdapter(yesNoA);
        batchtrackedSp.setAdapter(yesNoA);
        //httpRequest1();
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
                        //Picasso.with(getApplicationContext()).load("http://cld003.jts-prod.in/InventoryApp/productimage/Pencil1.jpg").into(imgPreview);
                        Log.d("Picasso", "http://cld003.jts-prod.in/InventoryApp/productimage/" + imageid + ".jpg");
                        //Log.d("Picasso", "http://cld003.jts-prod.in/InventoryApp/productimage/Pencil1.jpg");
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
                        Toast.makeText(getApplicationContext(), "please enter Location name", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getApplicationContext(), "please enter Location name", Toast.LENGTH_LONG).show();
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });

        jsonS2 = "{\"productid\":\"" + pID+ "\"}";
        Log.d("-jsnresponse update", "jsonS2..." + jsonS2);
        String url2 = "http://"+domain_name+":"+port+"/InventoryApp/get_productdetails/";

        try {
            JSONObject rmdt2 = null;

            rmdt2 = new JSONObject(jsonS2);
            getDetailsVolly(url2, rmdt2);
        } catch (JSONException e) {

        }
        Log.d("-jsnresponse enter", "jsonS2..." + jsonS2);

        jsonS3 = "{\"productid\":\"" + pID+ "\"}";
        Log.d("-jsnresponse update", "jsonS3..." + jsonS3);
        String url3 = "http://"+domain_name+":"+port+"/InventoryApp/get_positionquantity/";

        try {
            JSONObject rmdt3 = null;

            rmdt3 = new JSONObject(jsonS3);
            getPosVolly(url3, rmdt3);
        } catch (JSONException e) {

        }
        Log.d("-jsnresponse enter", "jsonS3..." + jsonS3);

        Log.d(" Back to main", " function.");

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d( " in ", " update function ");
                noNPS= noNPE.getText().toString();
                nameNPS= nameNPE.getText().toString();
                descNPS= descNPE.getText().toString();
                modelNPS= modelNPE.getText().toString();
                companyNPS= companyNPE.getText().toString();
                PackageNPS= PackageNPE.getText().toString();
                purchaseableS= purchaseableSp.getSelectedItem().toString();
                listpriceNPS= listpriceNPE.getText().toString();
                tolerancepercentNPS= tolerancepercentNPE.getText().toString();
                sellableS= sellableSp.getSelectedItem().toString();
                serialcontrolledS= serialcontrolledSp.getSelectedItem().toString();
                batchtrackedS= batchtrackedSp.getSelectedItem().toString();
                standardcostS= standardcostE.getText().toString();
                reorderlevelS= reorderlevelE.getText().toString();
                reorderqtyS= reorderqtyE.getText().toString();
                typeS= typeSp.getSelectedItem().toString();
                uomS= uomSp.getSelectedItem().toString();
                statusS= statusSp.getSelectedItem().toString();

                Log.d( " reorderqtyS", "   " + reorderqtyS);
                for (int l = 0; l < mIdA.length; l++) {

                    if( materialtypeA[l] == typeS){
                        mid = mIdA[l];
                    }
                    Log.d("!....", materialtypeA[l]);
                }
                for (int l = 0; l < uomIdA.length; l++) {

                    if( measurementA[l] == uomS){
                        uid = uomIdA[l];
                    }
                    Log.d("!....", measurementA[l]);
                }
                for (int l = 0; l < sIdA.length; l++) {

                    if( statusNameA[l] == statusS){
                        sid = sIdA[l];
                    }
                    Log.d("!....", statusNameA[l]);
                }
                Log.d("updated product ---", "details " + nameNPS);

                    jsonS = "{\"id\":\"" + pID + "\",\"productno\":\"" + noNPS + "\",\"productname\":\"" + nameNPS + "\",\"productdescription\":\"" + descNPS + "\",\"productmodel\":\"" + modelNPS + "\",\"productcompany\":\"" + companyNPS + "\",\"productpackage\":\"" + PackageNPS + "\",\"productpurchasable\":\"" + purchaseableS + "\",\"productpurchaselistprice\":\"" + listpriceNPS + "\",\"productpricetolerancepercent\":\"" + tolerancepercentNPS + "\",\"productsellable\":\"" + sellableS + "\",\"productserialcontrolled\":\"" + serialcontrolledS + "\",\"productbatchtracked\":\"" + batchtrackedS + "\",\"standardcost\":\"" + standardcostS + "\",\"reorderlevel\":\"" + reorderlevelS + "\",\"reorderquantity\":\"" + reorderqtyS + "\",\"producttype\":\"" + mid + "\",\"productuom\":\"" + uid + "\",\"statusid\":\"" + sid + "\",\"productimage\":\"" + encodedImage + "\",\"locationimage\":\"" + encodedImageL + "\"}";
                //+ sid + "\",\"productimage\":\"" + encodedImage + "\",\"locationimage\":\"" + encodedImageL + "\"}";
                Log.d("-jsnresponse update---", "" + jsonS);
                    String urlrs = "http://"+domain_name+":"+port+"/InventoryApp/update_product/";

                try {
                    JSONObject rmdt = null;

                    rmdt = new JSONObject(jsonS);
                    JSONSenderVolley(urlrs, rmdt);
                } catch (JSONException e) {

                }
                Log.d("-jsnresponse enter---", "" + jsonS);
            }
        });

        DelLocB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d( " in ", " delete function ");
                String jsonDel = "{\"id\":\""+ pID+"\"}";
                Log.d("----jsnresponse delete-", "" + jsonDel);

                //trigger[asycount]=new SendJsonDataToServer1().execute(String.valueOf(water_status));
                urlrs ="http://"+domain_name+":"+port+"/InventoryApp/del_product/";
                // String urlrs= "https://jtsha.in/service/validate_web";

                // try {
                try {
                    rmdt = new JSONObject(jsonDel.toString());
                } catch (JSONException e) {

                }

                final AlertDialog.Builder builder = new AlertDialog.Builder(ProdUpdate.this);
                builder.setTitle("Info");
                builder.setMessage("Do you want to Delete ??");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //Log.d("----jsnresponse enterd3333-----", "---" + water_status);
                        JSONSenderVolley(urlrs, rmdt);
                        //param_delete.remove(param_delete[i]);
                        Intent intent = new Intent(ProdUpdate.this, Products.class);
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

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProdUpdate.this, Products.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void sampletest() {

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
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

        Log.d( " in ", " get status ");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url=" http://"+domain_name+":"+port+"/InventoryApp/get_status/";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject responseJSON = null;
                        JSONObject jsonReq;
                        s = true;
                        if( s==true && uom == true && typ==true){
                            dialog_progress.hide();
                        }
                        try {
                            responseJSON = new JSONObject(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            Log.d( " Array", " response ->  " + response);
                            JSONArray new_array1;
                            new_array1 = responseJSON.getJSONArray("status_details");
                            Log.d( " Array", " : " + new_array1);
                            for (int b = 0, count = new_array1.length(); b < count; b++) {
                                String idSS = String.valueOf(new_array1.getJSONObject(b).getString("id"));
                                String statusNameS = new_array1.getJSONObject(b).getString("statusname");
                                idSL.add(idSS);
                                statusNameL.add(statusNameS);
                            }

                            sIdA = new String[idSL.size()];
                            statusNameA = new String[statusNameL.size()];

                            for (int bl = 0; bl < statusNameL.size(); bl++) {

                                sIdA[bl] = idSL.get(bl);
                                statusNameA[bl] = statusNameL.get(bl);
                                Log.d("sIdA", sIdA[bl]);
                                Log.d("statusNameA", statusNameA[bl]);
                            }
                            Log.d("before status  ","Spinner");
                            ArrayAdapter<String> statusA= new ArrayAdapter<String>(ProdUpdate.this,android.R.layout.simple_spinner_item, statusNameA);
                            statusA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            statusSp.setAdapter(statusA);
                            Log.d("after status  ","Spinner");
                        }
                        catch (JSONException e) {
                            Log.d("in status  ","fn catch");
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
        Log.d("out of  ","Status fn");
    }

    public void getMaterialType() {
        Log.d( " in ", " get Material type ");
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

                            new_array1 = responseJSON.getJSONArray("materialtype_details");
                            //new_array1 = responseJSON.getJSONArray("fields");
                            Log.d( " Array", " materialtype_details : " + new_array1);
                            for (int c = 0, count = new_array1.length(); c < count; c++) {
                                String idTS = String.valueOf(new_array1.getJSONObject(c).getString("id"));
                                String materialS = new_array1.getJSONObject(c).getString("materialtype");
                                idTL.add(idTS);
                                materialL.add(materialS);
                            }

                            mIdA = new String[idTL.size()];
                            materialtypeA = new String[materialL.size()];

                            for (int cl = 0; cl < materialL.size(); cl++) {

                                mIdA[cl] = idTL.get(cl);
                                materialtypeA[cl] = materialL.get(cl);
                                Log.d("mId ", mIdA[cl]);
                                Log.d("materialtype ", materialtypeA[cl]);
                            }
                            ArrayAdapter<String> materialA= new ArrayAdapter<String>(ProdUpdate.this,android.R.layout.simple_spinner_item, materialtypeA);
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
        Log.d("out of  ","type fn");
    }

    public void getUOM() {

        Log.d( " in ", " get uom ");
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
                            JSONArray new_array1;

                            new_array1 = responseJSON.getJSONArray("uom_details");
                            Log.d( " Array", " uom_details : " + new_array1);
                            for (int a = 0, count = new_array1.length(); a < count; a++) {
                                String idMS = String.valueOf(new_array1.getJSONObject(a).getInt("id"));
                                String measurementS = new_array1.getJSONObject(a).getString("measurementname");
                                idML.add(idMS);
                                measurementL.add(measurementS);
                            }
                            uomIdA = new String[idML.size()];
                            measurementA = new String[measurementL.size()];

                            for (int al = 0; al < measurementL.size(); al++) {
                                uomIdA[al] = idML.get(al);
                                measurementA[al] = measurementL.get(al);
                                Log.d("uomId ", uomIdA[al]);
                                Log.d("measurement ", measurementA[al]);
                            }
                            ArrayAdapter<String> uomA= new ArrayAdapter<String>(ProdUpdate.this,android.R.layout.simple_spinner_item, measurementA);
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
        Log.d("out of  ","Measurement fn");
    }

    public void getPosVolly(String url, final JSONObject json)
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

                        JSONObject responseJSON = null;
                        JSONObject jsonReq;
                        try {
                            responseJSON = new JSONObject(String.valueOf(response));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            Log.d( " Array", " response ->  " + response);
                            JSONArray new_array1;
                            String allPos = "";
                            new_array1 = responseJSON.getJSONArray("position_quantity");
                            Log.d( " Array", " position_quantity : " + new_array1);
                            for (int a = 0, count = new_array1.length(); a < count; a++) {
                                String positionS = new_array1.getJSONObject(a).getString("position");
                                allPos = allPos+", "+positionS;
                            }
                            pPos.setText(allPos);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            String errorCode = response.getString("error_code");
                            String errorDesc = response.getString("error_desc");
                            Log.d("errorCode","" + errorCode+"--" + errorDesc);

                            final AlertDialog.Builder builder = new AlertDialog.Builder(ProdUpdate.this);
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
                        dialog_progress.hide();
                        Log.d("---------", "---"+response.toString());

                        try {
                            String errorCode = response.getString("error_code");
                            String errorDesc = response.getString("error_desc");
                            Log.d("errorCode","" + errorCode+"--" + errorDesc);

                            final AlertDialog.Builder builder = new AlertDialog.Builder(ProdUpdate.this);
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

    public void getDetailsVolly(String url, final JSONObject json)
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
                            Log.d( " Array", " response ->  " + response);
                            JSONArray new_array1;
                            new_array1 = response.getJSONArray("get_productdetails");

                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                //Log.d( " count", " i " + count + i);
                                prdidS = new_array1.getJSONObject(i).getString("id");
                                //Log.d("productId from "," GET details" + prdidS);
                                String productnoS = new_array1.getJSONObject(i).getString("productno");     //
                                String productnameS = new_array1.getJSONObject(i).getString("productname");     //
                                String productdescriptionS = new_array1.getJSONObject(i).getString("productdescription");   //
                                String productmodelS = new_array1.getJSONObject(i).getString("productmodel");   //
                                String productcompanyS = new_array1.getJSONObject(i).getString("productcompany");   //
                                String productpackageS = new_array1.getJSONObject(i).getString("productpackage");   //
                                String productpurchasableS = new_array1.getJSONObject(i).getString("productpurchasable");   //
                                String productpurchaselistpriceS = new_array1.getJSONObject(i).getString("productpurchaselistprice");   //
                                String productPriceTolerancePercentS = new_array1.getJSONObject(i).getString("productPriceTolerancePercent");   //
                                String productsellableS = new_array1.getJSONObject(i).getString("productsellable");     //
                                String productserialcontrolledS = new_array1.getJSONObject(i).getString("productserialcontrolled");     //
                                String productbatchtrackedS = new_array1.getJSONObject(i).getString("productbatchtracked");     //
                                String standardcostS = new_array1.getJSONObject(i).getString("standardcost");   //
                                String reorderlevelS = new_array1.getJSONObject(i).getString("reorderlevel");   //
                                String reorderqtyS = new_array1.getJSONObject(i).getString("reorderqty");   //
                                Log.d( " reorderqtyS", "   " + reorderqtyS);
                                producttypeS = new_array1.getJSONObject(i).getString("producttype");    //
                                productuomS = new_array1.getJSONObject(i).getString("productuom");  //
                                statusId_idS = new_array1.getJSONObject(i).getString("status");     //

                                noNPE.setText(productnoS);
                                nameNPE.setText(productnameS);
                                descNPE.setText(productdescriptionS);
                                modelNPE.setText(productmodelS);
                                companyNPE.setText(productcompanyS);
                                PackageNPE.setText(productpackageS);

                                for (int d = 0; d < yesNo.length; d++) {
                                    if (productpurchasableS.contentEquals(yesNo[d])) {
                                        purchaseableSp.setSelection(d);
                                        Log.d(" productpurchasable ", productpurchasableS);
                                        Log.d(" yesNo.length-- ",""+ yesNo.length);
                                    }
                                }

                                listpriceNPE.setText(productpurchaselistpriceS);
                                tolerancepercentNPE.setText(productPriceTolerancePercentS);

                                for (int e = 0; e < yesNo.length; e++) {
                                    if (productsellableS.contentEquals(yesNo[e])) {
                                        sellableSp.setSelection(e);
                                        Log.d(" productsellable ", productsellableS);
                                    }
                                }

                                for (int f = 0; f < yesNo.length; f++) {
                                    if (productserialcontrolledS.contentEquals(yesNo[f])) {
                                        serialcontrolledSp.setSelection(f);
                                        Log.d("serialcontrolled", productserialcontrolledS);
                                    }
                                }
                                //serialcontrolledSp.setText(pDesc);

                                for (int g = 0; g < yesNo.length; g++) {
                                    if (productbatchtrackedS.contentEquals(yesNo[g])) {
                                        batchtrackedSp.setSelection(g);
                                        Log.d(" productbatchtracked ", productbatchtrackedS);
                                    }
                                }
                                //batchtrackedSp.setText(pDesc);
                                standardcostE.setText(standardcostS);
                                reorderlevelE.setText(reorderlevelS);
                                reorderqtyE.setText(reorderqtyS);

                                Log.d(" obtained producttypeS ","from get" + producttypeS);
                                for (int h = 0; h < materialL.size(); h++) {
                                    if (materialtypeA[h].contentEquals(producttypeS)) {
                                        mid = mIdA[h];
                                        //mid =Integer.toString(h);
                                        midI = h;
                                        typeSp.setSelection(h);
                                        Log.d(" producttype ", materialtypeA[h]+mid);
                                    }
                                    else {
                                        Log.d(" producttype else", materialtypeA[h]+mid);
                                    }
                                }

                                Log.d(" obtained productuomS ","from get" + productuomS);
                                for (int m = 0; m < measurementL.size(); m++) {
                                    if (measurementA[m].contentEquals(productuomS)) {
                                        uid = uomIdA[m];
                                        //uid =Integer.toString(m);
                                        uidI = m;
                                        uomSp.setSelection(m);
                                        Log.d(" uomSp ", measurementA[m] +uid);
                                    }
                                    else {
                                        Log.d(" uomSp else", measurementA[m]+uid);
                                    }
                                }

                                Log.d(" obtained statusId ","from get fn -- " + statusId_idS);
                                for (int n = 0; n < statusNameL.size(); n++) {
                                    if (statusNameA[n].contentEquals(statusId_idS)) {
                                        sid = sIdA[n];
                                        //sid =Integer.toString(n);
                                        sidI = n;
                                        statusSp.setSelection(n);
                                        Log.d(" statusId ", statusNameA[n] +sid);
                                    }
                                    else {
                                        Log.d(" statusId else", statusNameA[n]+sid);
                                    }
                                }
                            }

                            //setting product image
                            try {
                                if (nameNPE.getText().toString().length() != 0) {
                                    imageid = nameNPE.getText().toString();
                                    imgPreview.setVisibility(View.VISIBLE);
                                    Picasso.with(getApplicationContext()).load("http://cld003.jts-prod.in/InventoryApp/productimage/" + imageid + ".jpg").into(imgPreview);
                                    Log.d("Picasso", "http://cld003.jts-prod.in/InventoryApp/productimage/" + imageid + ".jpg");
                                } else {
                                    Toast.makeText(getApplicationContext(), "please enter product name to set Product image", Toast.LENGTH_LONG).show();
                                }
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }

                            // setting location image
                            try {
                                if (nameNPE.getText().toString().length() != 0) {
                                    imageid = nameNPE.getText().toString();
                                    imgPreviewL.setVisibility(View.VISIBLE);
                                    Picasso.with(getApplicationContext()).load("http://cld003.jts-prod.in/InventoryApp/locationimage/" + imageid + ".jpg").into(imgPreviewL);
                                    Log.d("Picasso", "http://cld003.jts-prod.in/InventoryApp/locationimage/" + imageid + ".jpg");
//                                    Picasso.with(getApplicationContext()).load("http://cld003.jts-prod.in/InventoryApp/locationimage/Pencil1.jpg").into(imgPreviewL);
//                                    Log.d("Picasso", "http://cld003.jts-prod.in/InventoryApp/locationimage/Pencil1.jpg");
                                } else {
                                    Toast.makeText(getApplicationContext(), "please enter Location name to set location image", Toast.LENGTH_LONG).show();
                                }
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }

                        }
                        catch (JSONException e) {
                            Log.d("http  ","catch.......");
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
