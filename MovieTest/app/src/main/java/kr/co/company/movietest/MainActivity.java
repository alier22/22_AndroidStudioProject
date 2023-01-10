package kr.co.company.movietest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import kr.co.company.movietest.DBHelper;
import kr.co.company.movietest.DisplayMovie;
import kr.co.company.movietest.R;

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
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long arg4) {
                String item =(String)((ListView)mAdapter).getItemAtPosition(i);
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