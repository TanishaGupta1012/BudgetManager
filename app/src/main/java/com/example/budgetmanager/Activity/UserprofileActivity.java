package com.example.budgetmanager.Activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.budgetmanager.Pojo.UserPojo;
import com.example.budgetmanager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;

public class UserprofileActivity extends AppCompatActivity {
    Button logout;
    CircularImageView image;
    TextView username,email,editprofile,logbook,graph,aboutus,feedback;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        logout = findViewById(R.id.logout);
        image = findViewById(R.id.image);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        editprofile = findViewById(R.id.editprofile);
        logbook = findViewById(R.id.logbook);
        graph = findViewById(R.id.graph);
        aboutus = findViewById(R.id.aboutus);
        feedback = findViewById(R.id.feedback);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserprofileActivity.this,EditProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
        logbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserprofileActivity.this,LogbookActivity.class);
                startActivity(intent);
                finish();
            }
        });
        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserprofileActivity.this,graphActivity.class);
                startActivity(intent);
                finish();
            }
        });
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserprofileActivity.this,AboutusActivity.class);
                startActivity(intent);
                finish();
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserprofileActivity.this,FeedbackActivity.class);
                startActivity(intent);
                finish();
            }
        });




    }
}