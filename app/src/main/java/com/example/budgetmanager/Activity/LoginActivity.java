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
import android.widget.TextView;
import android.widget.Toast;
import com.example.budgetmanager.Pojo.UserPojo;
import com.example.budgetmanager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {
    Button login;
    EditText email,password;
    TextView forgotpassword,newuser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    int count=0;
    String emailentry,passwordentry;
    String userid;
    String numberentry;
    String firebaseemail;
    String nameentry;
    String firebasepassword;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

            login = findViewById(R.id.login);
            email = findViewById(R.id.EdittextEmail);
            password = findViewById(R.id.EdittextPassword);
            forgotpassword = findViewById(R.id.forgotpassword);
            newuser = findViewById(R.id.newuser);
            emailentry = email.getText().toString();

            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("userinfo");

            final Intent intent = new Intent(LoginActivity.this,DashboardActivity.class);

            SharedPreferences sharedPreferences = getSharedPreferences("shareddata",MODE_PRIVATE);
            boolean flag = sharedPreferences.getBoolean("flag",false);
            userid = sharedPreferences.getString("userid",null);
            if (flag == true) {
                Toast.makeText(this, "Login"+userid, Toast.LENGTH_SHORT).show();
                intent.putExtra("Userid",userid);
                startActivity(intent);

                finish();
            }
            else {
                final SharedPreferences.Editor editor = sharedPreferences.edit();
                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        emailentry = email.getText().toString();
                        passwordentry = password.getText().toString();

                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Log.d("1234", "onDatachange:" + dataSnapshot);
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    Log.d("1234", "onDataChange: for" + data);
                                    UserPojo userPojo = data.getValue(UserPojo.class);
                                    Log.d("1234", "onDataChange: userPojo  " + userPojo);
                                    firebaseemail = userPojo.getEmail();
                                    firebasepassword = userPojo.getPassword();


                                    if (firebaseemail.equals(emailentry) && firebasepassword.equals(passwordentry)) {
                                        count = 1;
                                        userid = userPojo.getUserid();
                                        numberentry= userPojo.getNumber();
                                        nameentry=userPojo.getName();
                                    }


                                }
                            }

                            @Override
                            public void onCancelled(@NotNull DatabaseError databaseError) {
                                Toast.makeText(LoginActivity.this, "Database Error", Toast.LENGTH_SHORT).show();

                            }
                        });
                        if (count == 1){

                            editor.putString("name", nameentry);
                            editor.putString("number",numberentry);
                            editor.putString("email", firebaseemail);
                            editor.putString("password", passwordentry);
                            editor.putString("userid",userid);
                            editor.putBoolean("flag",true);
                            editor.commit();

                            Toast.makeText(LoginActivity.this, "WELCOME USER", Toast.LENGTH_SHORT).show();
                            intent.putExtra("userid",userid);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Invalid User Name Or Password", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                newuser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(LoginActivity.this,signupActivity.class);
                        startActivity(intent1);
                        finish();
                    }
                });





            }

        }


    }

