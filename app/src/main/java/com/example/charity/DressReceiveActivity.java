package com.example.charity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DressReceiveActivity extends Activity {
    private DatabaseReference reff;
    private ListView mListView;
    private ArrayAdapter adapter;
    private ArrayList<String> arrayList;
    private long N=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dressreceive);

        Toast.makeText(DressReceiveActivity.this,"Please Wait.....",Toast.LENGTH_SHORT).show();

        mListView = (ListView) findViewById(R.id.listview1);


        reff = FirebaseDatabase.getInstance().getReference().child("Child");
        arrayList = new ArrayList<String>();

        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                arrayList.clear();
                N=dataSnapshot.getChildrenCount();

                for( int i = 1 ; i <= N ; i++ ){
                    arrayList.add(dataSnapshot.child(String.valueOf(i)).child("DressName").getValue(String.class));
                }

                adapter = new ArrayAdapter(DressReceiveActivity.this, android.R.layout.simple_expandable_list_item_1,arrayList);
                mListView.setAdapter(adapter);

                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Cache c = new Cache();
                        i++;

                        c.setUserName(dataSnapshot.child(String.valueOf(i)).child("userName").getValue(String.class));
                        c.setDressName(dataSnapshot.child(String.valueOf(i)).child("DressName").getValue(String.class));
                        c.setQuantity(dataSnapshot.child(String.valueOf(i)).child("quantity").getValue(String.class));
                        c.setNumber(dataSnapshot.child(String.valueOf(i)).child("number").getValue(String.class));
                        c.setAddress(dataSnapshot.child(String.valueOf(i)).child("Address").getValue(String.class));

                        Intent intent = new Intent(DressReceiveActivity.this,DisplayActivity.class);

                        intent.putExtra("name",c.getUserName());
                        intent.putExtra("dress",c.getDressName());
                        intent.putExtra("quantity",c.getQuantity());
                        intent.putExtra("number",c.getNumber());
                        intent.putExtra("Address",c.getAddress());

                        startActivity(intent);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
