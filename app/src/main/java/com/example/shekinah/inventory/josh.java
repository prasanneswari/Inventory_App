package com.example.shekinah.inventory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class josh extends AppCompatActivity {

    Button B_pending,B_ordered, P_prd, P_order;
    static String domain_name = "cld003.jts-prod.in";  //cloud_3
    static String port = "20105";
    static String eid = "7";
    static String sessionId = "58";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_josh);

        B_pending = (Button) findViewById(R.id.pendingButton);
        B_ordered = (Button) findViewById(R.id.orderedButton);

        B_pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(josh.this,PurchasePending.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        B_ordered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(josh.this,OrderedRequisition.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }
}
