package com.example.shekinah.inventory;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import static com.example.shekinah.inventory.JobRolesMenu.eid;

public class FamilyDetails extends AppCompatActivity {

    EditText memNum;
    String num;
    Integer num1;
    Button ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_details);

        memNum = (EditText) findViewById(R.id.MemNum);
        ok = (Button) findViewById(R.id.ok);
        num = memNum.getText().toString();

        Log.d("num   ",num);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("num   ",num);
//                try {
//                    Log.d("num   ",num);
//                    num1 = Integer.parseInt(num);
//                    Log.d("",num+" is a number");
//                }
//                catch (NumberFormatException e) {
//                    Log.d("", num + " is not a number");
//
//                    final AlertDialog.Builder builder = new AlertDialog.Builder(FamilyDetails.this);
//                    builder.setTitle("Info");
//                    builder.setMessage("Please enter Integer Value!!");
//
//                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//
//                            dialogInterface.dismiss();
//                        }
//                    });
//
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                }
            }
        });


    }
}
