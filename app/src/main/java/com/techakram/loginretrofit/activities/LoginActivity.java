package com.techakram.loginretrofit.activities;

import android.content.Intent;
import android.os.Bundle;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.techakram.loginretrofit.Api.RetrofitClient;
import com.techakram.loginretrofit.R;
import com.techakram.loginretrofit.models.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity  {


    private EditText editTextEmail;
    private EditText editTextPassword;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        button=findViewById(R.id.buttonLogin);
        button.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

       // findViewById(R.id.buttonLogin).setOnClickListener(this);


    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
//            Intent intent = new Intent(this, ProfileActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        }
   // }

    private void userLogin() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Password should be atleast 6 character long");
            editTextPassword.requestFocus();
            return;
        }

        Call<LoginResponse> call = RetrofitClient
                .getInstance().getApi().userLogin(email, password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                  if(response.isSuccessful())
                  {
                      Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                      startActivity(intent);
                     // Toast.makeText(LoginActivity.this,loginResponse.getMessage(), Toast.LENGTH_SHORT).show( );
                  }

//                if (!loginResponse.isError()) {
//
//                    SharedPrefManager.getInstance(LoginActivity.this)
//                            .saveUser(loginResponse.getUser());
//
//                    Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//
//
//                } //else {
                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                //}
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                Toast.makeText(LoginActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show( );
            }
        });

    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.buttonLogin:
//                userLogin();
//                break;
////            case R.id.textViewRegister:
////                startActivity(new Intent(this, MainActivity.class));
////                break;
//        }
    }

