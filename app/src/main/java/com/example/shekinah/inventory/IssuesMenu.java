package com.example.shekinah.inventory;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import static com.example.shekinah.inventory.JobRolesMenu.eid;

public class IssuesMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues_menu);


        //TextView Temail = (TextView) findViewById(R.id.txt_success_email);
        Button backB = (Button) findViewById(R.id.back);
        Button consumableB = (Button) findViewById(R.id.consumable);
        Button productDevB = (Button) findViewById(R.id.productDev);
        Button productionB = (Button) findViewById(R.id.production);
        Button replacementB = (Button) findViewById(R.id.replacement);
        Button returnPB = (Button) findViewById(R.id.returnP);
        Button finishedPB = (Button) findViewById(R.id.finishedP);


        Button Btnlogout = (Button) findViewById(R.id.btn_logout);
        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(IssuesMenu.this, StockIncharge1.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        consumableB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(IssuesMenu.this, ConsumableIssues.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        productDevB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(IssuesMenu.this, Products.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
            }
        });

        productionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(IssuesMenu.this, Positions.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
            }
        });

        replacementB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(IssuesMenu.this, Status.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
            }
        });

        returnPB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(IssuesMenu.this, IssuesMenu.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
            }
        });

        finishedPB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//            Intent intent = new Intent(IssuesMenu.this,MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);

            }
        });
    }
}

