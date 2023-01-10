package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    private ListView listView;
    ArrayAdapter mAdapter;
    ArrayList arrayList;
    DatabaseReference firebaseDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView1);
        firebaseDB = FirebaseDatabase.getInstance().getReference().child("movies");
        arrayList = new ArrayList();
        mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
                arrayList);
        listView.setAdapter(mAdapter);
        firebaseDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mAdapter.clear();
                arrayList= new ArrayList();
                for (DataSnapshot item : snapshot.getChildren()){
                    String id = (String) item.child("id").getValue();
                    String name = (String) item.child("name").getValue();
                    arrayList.add(id + " " + name);
                }
                mAdapter.addAll(arrayList);
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long
                    l) {
                String item = (String) ((ListView) adapterView).getItemAtPosition(i);
                String[] strArray = item.split(" ");
                int id = Integer.parseInt(strArray[0]);
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                bundle.putString("action", "update");
                Intent intent = new Intent(getApplicationContext(), DisplayMovie.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    public void onClick(View target) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", arrayList.size());
        bundle.putString("action", "insert");
        Intent intent = new Intent(getApplicationContext(), DisplayMovie.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}