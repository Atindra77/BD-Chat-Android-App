package com.example.bdchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.bdchat.databinding.ActivitySignupBinding;
import com.example.bdchat.models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    ActivitySignupBinding binding;

    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth= FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        getSupportActionBar().hide();
        progressDialog=new ProgressDialog(SignupActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We Are Creating Your Account");
        binding.BtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.TextUserName.getText().toString().isEmpty()
                        && !binding.TextEmailId.getText().toString().isEmpty()&& !binding.TextPassword.getText().toString().isEmpty())
                {
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(binding.TextEmailId.getText().toString(),binding.TextPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();

                            if(task.isSuccessful()){
                                Users users=new Users(binding.TextUserName.getText().toString(),
                                        binding.TextEmailId.getText().toString(),binding.TextPassword.getText().toString());

                                String id=task.getResult().getUser().getUid();
                                database.getReference().child("Users").child(id).setValue(users);
                                Toast.makeText(SignupActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(SignupActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(SignupActivity.this, "Enter Credential", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.textAlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });

    }
}