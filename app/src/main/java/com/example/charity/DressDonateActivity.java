package com.example.charity;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.pusher.pushnotifications.PushNotifications;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DressDonateActivity extends Activity {
    private EditText uname, dname, quantity, number,address;
    private DatabaseReference reff;
    private Child member;
    private long maxid = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        }

        uname = (EditText) findViewById(R.id.dtname);
        dname = (EditText) findViewById(R.id.dtdressname);
        quantity = (EditText) findViewById(R.id.dtquantity);
        number = (EditText) findViewById(R.id.dtnum);
        address=(EditText)findViewById(R.id.dtaddress);

        Button btn = (Button) findViewById(R.id.btnsubmit);

        member = new Child();

        reff = FirebaseDatabase.getInstance().getReference().child("Child");

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    maxid = dataSnapshot.getChildrenCount(); }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot dataSnapshot, @Nullable @org.jetbrains.annotations.Nullable String s) {
                String un, fn, qt, nm,ad;

                un = uname.getText().toString().trim();
                fn = dname.getText().toString().trim();
                qt = quantity.getText().toString().trim();
                nm = number.getText().toString().trim();
                ad=address.getText().toString().trim();

                if (TextUtils.isEmpty(un) || TextUtils.isEmpty(fn) || TextUtils.isEmpty(qt) || TextUtils.isEmpty(nm)) {
                    Toast.makeText(DressDonateActivity.this, "Fields must not be empty", Toast.LENGTH_SHORT).show();
                } else
                    notification();
            }

            @Override
            public void onChildChanged(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot dataSnapshot, @Nullable @org.jetbrains.annotations.Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot dataSnapshot, @Nullable @org.jetbrains.annotations.Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError databaseError) {

            }
        });



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String un, fn, qt, nm,ad;

                un = uname.getText().toString().trim();
                fn = dname.getText().toString().trim();
                qt = quantity.getText().toString().trim();
                nm = number.getText().toString().trim();
                ad=address.getText().toString().trim();


                if (TextUtils.isEmpty(un) || TextUtils.isEmpty(fn) || TextUtils.isEmpty(qt) || TextUtils.isEmpty(nm)) {
                    Toast.makeText(DressDonateActivity.this, "Fields must not be empty", Toast.LENGTH_SHORT).show();
                } else {
                    member.setUserName(un);
                    member.setFoodName(fn);
                    member.setQuantity(qt);
                    member.setNumber(nm);
                    member.setAddress(ad);


                    reff.child(String.valueOf(maxid + 1)).setValue(member);

                    Toast.makeText(DressDonateActivity.this, "Donated", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(DressDonateActivity.this, SecondActivity.class);
                    startActivity(intent);
                    finish();

                }
            }
        });
    }
    private void notification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                channel = new NotificationChannel("n", "n", NotificationManager.IMPORTANCE_DEFAULT);
            }

            NotificationManager manager = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                manager = getSystemService(NotificationManager.class);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                manager.createNotificationChannel(channel);
            }
        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "n")
                .setContentText("Item Added")
                .setSmallIcon(R.drawable.give_love)
                .setAutoCancel(true)
                .setContentText("Check them out!");
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999, builder.build());


        PushNotifications.start(getApplicationContext(), "4e70391d-7719-4556-a67a-18c71abbe1ad");
        PushNotifications.addDeviceInterest("hello");


        String instanceId = "4e70391d-7719-4556-a67a-18c71abbe1ad";
        String secretKey = "8439FD847AE83CBF0BE8F3C82A2AB761745ACBE1A45BF047DA6819286CCFAADD";

        PushNotifications beamsClient = new PushNotifications();

        List<String> interests = Arrays.asList("hello", "pizza");

        Map<String, Map> publishRequest = new HashMap<String, Map>();
        Map<String, String> fcmNotification = new HashMap<String, String>();
        fcmNotification.put("title", "ITEMS ADDED");
        fcmNotification.put("body", "Check it out! New items are being added for donation");
        Map<String, Map> fcm = new HashMap<String, Map>();
        fcm.put("notification", fcmNotification);
        publishRequest.put("fcm", fcm);


    }

}

