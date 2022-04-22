package com.example.charity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DressActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dress);

        Button donate = (Button) findViewById(R.id.dressdonate);
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DressActivity.this, DressDonateActivity.class);
                startActivity(intent);
            }
        });

        Button recieve = (Button) findViewById(R.id.dressrecive);
        recieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DressActivity.this, DressReceiveActivity.class);
                startActivity(intent);
            }
        });
    }
}
