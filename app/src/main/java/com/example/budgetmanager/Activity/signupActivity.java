package com.example.budgetmanager.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.budgetmanager.Helper.SelectImageHelper;
import com.example.budgetmanager.Pojo.UserPojo;
import com.example.budgetmanager.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

public class signupActivity extends AppCompatActivity {
    CircularImageView circularImageView;
    EditText editTextname,editTextemail,editTextphno,editTextpassword;
    Button buttonregister;
    String name,number,password;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    String email;
    StorageReference reference;
    SelectImageHelper selectImageHelper;
    boolean flag = true;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        circularImageView = findViewById(R.id.profileimage);
        editTextname=findViewById(R.id.Edittextfullname);
        editTextemail=findViewById(R.id.Edittextemail);
        editTextphno=findViewById(R.id.Edittextphone);
        editTextpassword=findViewById(R.id.Edittextpassword);
        buttonregister=findViewById(R.id.buttonregister);

        progressDialog = new ProgressDialog(this);

        SharedPreferences sharedPreferences = getSharedPreferences("shareddata",MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("userinfo");

        reference = FirebaseStorage.getInstance().getReference();

        selectImageHelper = new SelectImageHelper(this, circularImageView);

        circularImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageHelper.selectImageOption();



            }
        });

        buttonregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userid = databaseReference.push().getKey();
                String emailpattern = "[a-zA-Z0-9]+@[a-z]+.+[a-z]+";
                name = editTextname.getText().toString();
                number = editTextphno.getText().toString();
                email=(String) editTextemail.getText().toString();
                password=(String) editTextpassword.getText().toString();
                progressDialog.setTitle("Please Wait...");
                progressDialog.show();

                Uri uri = selectImageHelper.getURI_FOR_SELECTED_IMAGE();
                if (uri == null) {
                    flag = true;
                    Toast.makeText(signupActivity.this, "Error getting Image", Toast.LENGTH_SHORT).show();
                }else if (name.equals(" ")) {
                    Toast.makeText(signupActivity.this, "Wrong Details", Toast.LENGTH_SHORT).show();
                } else if (!email.matches(emailpattern) || email.length() == 0) {
                    Toast.makeText(signupActivity.this, "Wrong Details", Toast.LENGTH_SHORT).show();
                } else if (number.length() < 10) {
                    Toast.makeText(signupActivity.this, "Wrong details", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6 || password.equals(" ")) {
                    Toast.makeText(signupActivity.this, "Wrong details", Toast.LENGTH_SHORT).show();
                } else if (flag == true) {
                    final StorageReference storageReference = reference.child("/image/"+email);
                    storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    progressDialog.cancel();
                                    UserPojo userPojo = new UserPojo();
                                    userPojo.setName("name");
                                    userPojo.setEmail(email);
                                    userPojo.setNumber(number);
                                    userPojo.setPassword(password);
                                    userPojo.setUserid(userid);
                                    userPojo.setImage(uri.toString());

                                    editor.putString("name", name);
                                    editor.putString("userid",userid);
                                    editor.putString("number", number);
                                    editor.putString("email", email);
                                    editor.putString("password", password);
                                    editor.putBoolean("flag",true);
                                    editor.commit();

                                    databaseReference.child(userid).setValue(userPojo);
                                    Toast.makeText(signupActivity.this, "Register Successfull", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(signupActivity.this,LoginActivity.class);
                                    //Toast.makeText(signupActivity.this, ""+userid, Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                    finish();



                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.cancel();
                            Toast.makeText(signupActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });



                }
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
        selectImageHelper.handleResult(requestCode, resultCode, result);
    }


    @Override
    public void onRequestPermissionsResult(final int requestCode, final @NonNull String[] permissions, final @NonNull int[] grantResults) {
        selectImageHelper.handleGrantedPermisson(requestCode, grantResults);       }

}



