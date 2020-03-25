package com.example.budgetmanager.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.budgetmanager.Pojo.addpojo;
import com.example.budgetmanager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddActivity extends AppCompatActivity {
    EditText edittextamount,edittextexpense,edittextdescription;
    Button buttonadd,buttonclear;
    DatabaseReference firebaseDatabase,Addreference;
    //DatabaseReference databaseReference;
    String emailentered,phonenumber;
    int maxid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final String userid = getIntent().getStringExtra("Userid");

        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Adddata").child(userid);
        Addreference = FirebaseDatabase.getInstance().getReference().child("Adddata");

        edittextamount = findViewById(R.id.edittextamount);
        edittextdescription = findViewById(R.id.edittextdescription);
        edittextexpense = findViewById(R.id.editextexpensename);
        buttonadd = findViewById(R.id.buttonadd);
        buttonclear = findViewById(R.id.buttonclear);

        Toast.makeText(AddActivity.this, "Add"+userid, Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences = getSharedPreferences("shareddata",MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        emailentered = sharedPreferences.getString("email",null);
        phonenumber = sharedPreferences.getString("number",null);
        Addreference.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                maxid = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                maxid = maxid+1;

                String expense = edittextexpense.getText().toString();
                String amount = edittextamount.getText().toString();
                String Description = edittextdescription.getText().toString();
                Log.d("1234", "onClick:");
                Intent intent = new Intent(AddActivity.this,DashboardActivity.class);
                addpojo addpojo1 = new addpojo();
                addpojo1.setExpensename(expense);
                addpojo1.setAmount(amount);
                addpojo1.setDescription(Description);
                addpojo1.setEmailentered(emailentered);
                addpojo1.setNumber(phonenumber);
                addpojo1.setIndex(maxid);
                editor.putString("expense",expense);
                editor.putString("amount",amount);
                editor.putString("Description",Description);
                editor.putBoolean("flag",true);
                editor.commit();

                Addreference.child(userid).child(expense).setValue(addpojo1);
                Toast.makeText(AddActivity.this, "Added", Toast.LENGTH_SHORT).show();
                intent.putExtra("Currentuserid",userid);
                startActivity(intent);
                finish();
            }
        });
    }
}
