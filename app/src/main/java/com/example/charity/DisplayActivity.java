package com.example.charity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DisplayActivity extends Activity {
    private EditText uname, fname, quantity, number,address;
    private Button locatebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        uname = (EditText) findViewById(R.id.etname);
        fname = (EditText) findViewById(R.id.etfoodname);
        quantity = (EditText) findViewById(R.id.etquantity);
        number = (EditText) findViewById(R.id.etnum);
        address=(EditText) findViewById(R.id.addr);
        locatebutton=(Button)findViewById(R.id.btnlocate);

        Intent i = getIntent();

        String name = i.getStringExtra("name");
        String food = i.getStringExtra("food");
        String quant = i.getStringExtra("quant");
        String num = i.getStringExtra("num");
        String add=i.getStringExtra("add");


        uname.setText("Donor Name- "+name);
        fname.setText("Food Name- "+food);
        quantity.setText("Food Quantity- "+quant);
        number.setText("Donor Number- "+num);
        address.setText("Donor Address-"+add);

        uname.setEnabled(false);
        fname.setEnabled(false);
        quantity.setEnabled(false);
        number.setEnabled(false);
        address.setEnabled(false);

        //adding geolocation functionality
        locatebutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri=Uri.parse("geo:0,0?q="+ Uri.encode(String.valueOf(address)));
                Intent mapIntent=new Intent(Intent.ACTION_VIEW,gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });


    }
}
