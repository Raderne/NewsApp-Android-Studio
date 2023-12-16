package com.red.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.red.newsapp.Services.ApiInitialize;
import com.red.newsapp.Services.Auth.AuthUser;
import com.red.newsapp.Services.Auth.RegisterRequest;
import com.red.newsapp.api_response.API;

import retrofit2.Call;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText, firstNameEditText, lastNameEditText;
    private Button registerButton;
    private TextView loginTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        intiViews();
        
        loginTextView.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
        
        registerUser();
    }

    private void registerUser() {
        registerButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String name = firstNameEditText.getText().toString();
            String lastName = lastNameEditText.getText().toString();

            if (email.isEmpty() || password.isEmpty() || name.isEmpty() || lastName.isEmpty()) {
                Toast.makeText(this, "Lütfen tüm alanları doldurunuz", Toast.LENGTH_SHORT).show();
            } else {
                KayitIslemi(email, password, name, lastName);
            }
        });
    }

    private void KayitIslemi(String email, String password, String firstName, String lastName) {
        Retrofit retrofit = ApiInitialize.apiCall();
        API api = retrofit.create(API.class);
        RegisterRequest registerApiRequest = new RegisterRequest(firstName, lastName, email, password);
        Call<AuthUser> call = api.register(registerApiRequest);
        call.enqueue(new retrofit2.Callback<AuthUser>() {
            @Override
            public void onResponse(Call<AuthUser> call, retrofit2.Response<AuthUser> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Kayıt başarılı", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Kayıt başarısız", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthUser> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Kayıt başarısız"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void intiViews() {
        emailEditText = findViewById(R.id.idEtRegisterEmail);
        passwordEditText = findViewById(R.id.idEtRegisterPassword);
        firstNameEditText = findViewById(R.id.idEtRegisterName);
        lastNameEditText = findViewById(R.id.idEtRegisterLastName);
        registerButton = findViewById(R.id.idBtnRegister);
        loginTextView = findViewById(R.id.idTvLogin);
    }
}