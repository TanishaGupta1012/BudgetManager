package com.example.budgetmanager.Activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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
        databaseReference = firebaseDatabase.getReference().child("dd");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    UserPojo userPojo = dataSnapshot1.getValue(UserPojo.class);

                    String name = userPojo.getName();
                    String emailAddress = userPojo.getEmail();
                    username.setText(name);
                    email.setText(emailAddress);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UserprofileActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });




    }
}