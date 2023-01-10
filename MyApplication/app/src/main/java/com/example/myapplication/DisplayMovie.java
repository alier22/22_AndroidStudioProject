package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class DisplayMovie extends AppCompatActivity {

    EditText editname;
    EditText editdirector;
    EditText edityear;
    EditText editnation;
    EditText editrating;
    int id = 0;
    DatabaseReference firebaseDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_movie);
        editname = (EditText) findViewById(R.id.editTextName);
        editdirector = (EditText) findViewById(R.id.editTextDirector);
        edityear = (EditText) findViewById(R.id.editTextYear);
        editnation = (EditText) findViewById(R.id.editTextNation);
        editrating = (EditText) findViewById(R.id.editTextRating);
        firebaseDB = FirebaseDatabase.getInstance().getReference().child("movies");
        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("id");
        String action = bundle.getString("action");
        if (action.equals("update")){
            Button b = (Button) findViewById(R.id.button1);
            b.setVisibility(View.INVISIBLE);
            DatabaseReference movieElement = firebaseDB.child(Integer.toString(id));
            movieElement.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String n = (String) snapshot.child("name").getValue();
                    String d = (String) snapshot.child("director").getValue();
                    String y = (String) snapshot.child("year").getValue();
                    String na = (String) snapshot.child("nation").getValue();
                    String r = (String) snapshot.child("rating").getValue();
                    editname.setText(n);
                    editdirector.setText(d);
                    edityear.setText(y);
                    editnation.setText(na);
                    editrating.setText(r);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }
    public void insert(View view) {
        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("id");
        String name = editname.getText().toString();
        String director = editdirector.getText().toString();
        String year = edityear.getText().toString();
        String nation = editnation.getText().toString();
        String rating = editrating.getText().toString();

        firebaseDB.child(Integer.toString(id)).child("id").setValue(Integer.toString(id));
        firebaseDB.child(Integer.toString(id)).child("name").setValue(name);
        firebaseDB.child(Integer.toString(id)).child("director").setValue(director);
        firebaseDB.child(Integer.toString(id)).child("year").setValue(year);
        firebaseDB.child(Integer.toString(id)).child("nation").setValue(nation);
        firebaseDB.child(Integer.toString(id)).child("rating").setValue(rating);
        finish();
    }
    public void delete(View view) {
        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("id");
        DatabaseReference deleteData = firebaseDB.child(Integer.toString(id));
        deleteData.removeValue();
        finish();
    }
    public void edit(View view) {
        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("id");
        String name = editname.getText().toString();
        String director = editdirector.getText().toString();
        String year = edityear.getText().toString();
        String nation = editnation.getText().toString();
        String rating = editrating.getText().toString();

        firebaseDB.child(Integer.toString(id)).child("id").setValue(Integer.toString(id));
        firebaseDB.child(Integer.toString(id)).child("name").setValue(name);
        firebaseDB.child(Integer.toString(id)).child("director").setValue(director);
        firebaseDB.child(Integer.toString(id)).child("year").setValue(year);
        firebaseDB.child(Integer.toString(id)).child("nation").setValue(nation);
        firebaseDB.child(Integer.toString(id)).child("rating").setValue(rating);
        finish();
    }
}
