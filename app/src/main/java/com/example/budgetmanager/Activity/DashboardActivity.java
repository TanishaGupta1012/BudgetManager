package com.example.budgetmanager.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.budgetmanager.CustomAdapter;
import com.example.budgetmanager.Pojo.addpojo;
import com.example.budgetmanager.R;
import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView textview;
    ListView listview;
    FloatingActionButton fab;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ArrayList<addpojo> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        String userid;


        textview = findViewById(R.id.textview);
        listview = findViewById(R.id.listview);
        fab = findViewById(R.id.fab);
        toolbar=findViewById(R.id.toolbardashboard);

        userid = getIntent().getStringExtra("userid");
        if (userid == null)
        {
            userid = getIntent().getStringExtra("Userid");

            if (userid == null)
                userid = getIntent().getStringExtra("Currentuserid");

        }

       // Toast.makeText(this, "Dashboard "+userid, Toast.LENGTH_SHORT).show();


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Adddata");

        final SharedPreferences sharedPreferences = getSharedPreferences("shareddata",MODE_PRIVATE);
        final String q = sharedPreferences.getString("email",null);
        final String phonenumber = sharedPreferences.getString("number",null);
        databaseReference.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("1234", "onDataChange: " + dataSnapshot);
                for (DataSnapshot data : dataSnapshot.getChildren())
                {

                    Log.d("1234", "onDataChange: snap " + data);

                    addpojo addpojo1 = data.getValue(addpojo.class);
                    int index = addpojo1.getIndex();
                    Toast.makeText(DashboardActivity.this, ""+index, Toast.LENGTH_SHORT).show();


                    String Amount = addpojo1.getAmount();
                    String expensename = addpojo1.getDescription();

                    addpojo addpojo2 = new addpojo();
                    addpojo2.setExpensename(expensename);
                    addpojo2.setAmount(Amount);
                    arrayList.add(addpojo1);

                }



                CustomAdapter customAdapter = new CustomAdapter(DashboardActivity.this, R.layout.listview_item, arrayList);
                listview.setAdapter(customAdapter);


            }








            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DashboardActivity.this, "" +databaseError.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });


        final String finalUserid = userid;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this,AddActivity.class);
                intent.putExtra("Userid", finalUserid);
                startActivity(intent);
                finish();

            }
        });



        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.user:
                Intent intent = new Intent(DashboardActivity.this,UserprofileActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.graph:
                Intent intent1 = new Intent(DashboardActivity.this,AddActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.calendar:
                Toast.makeText(this, "History", Toast.LENGTH_SHORT).show();
                break;
            default:
                return super.onContextItemSelected(item);

        }
        return true;
    }
}



