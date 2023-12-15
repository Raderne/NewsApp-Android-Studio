package com.red.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.red.newsapp.Services.ApiInitialize;
import com.red.newsapp.Services.Auth.AuthUser;
import com.red.newsapp.Services.Auth.LoginRequest;
import com.red.newsapp.api_response.API;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton;
    private TextView registerTextView;
    private EditText emailEditText, passwordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        Login();

        registerTextView.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void Login() {
        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            Retrofit retrofit = ApiInitialize.apiCall();
            API api = retrofit.create(API.class);
            LoginRequest loginRequest = new LoginRequest(email, password);
            Call<AuthUser> call = api.login(loginRequest);
            call.enqueue(new retrofit2.Callback<AuthUser>() {
                @Override
                public void onResponse(Call<AuthUser> call, retrofit2.Response<AuthUser> response) {
                    if (response.isSuccessful()) {
                        Log.e("response", "onResponse: " + response.body());
                        AuthUser authUser = response.body();
                        assert authUser != null;
                        String token = authUser.getToken();
                        String id = authUser.getId();

                        SharedPreferences sharedPreferences = getSharedPreferences("com.red.newsapp", MODE_PRIVATE);
                        sharedPreferences.edit().putString("token", token).apply();
                        sharedPreferences.edit().putString("id", id).apply();

                        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Giriş Başarısız", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AuthUser> call, Throwable t) {
                    Log.e("error", "onFailure: " + t.getMessage());
                    Toast.makeText(LoginActivity.this, "Login Failed " + t, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void initViews() {
        loginButton = findViewById(R.id.idBtnLogin);
        registerTextView = findViewById(R.id.idTvRegister);
        emailEditText = findViewById(R.id.idEtEmailLogin);
        passwordEditText = findViewById(R.id.idEtPasswordLogin);
    }
}