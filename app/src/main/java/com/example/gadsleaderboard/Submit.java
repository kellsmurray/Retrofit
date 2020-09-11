package com.example.gadsleaderboard;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.gadsleaderboard.Api.HoursApi;
import com.example.gadsleaderboard.Retrofit.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Submit extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText firstName, lastName, email, link;
    private boolean isOkToContinue = true;
    private HoursApi hoursApi;
    private ProgressDialog progressDialog;
    private HoursApi HoursApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit);

        init();
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!Patterns.EMAIL_ADDRESS.matcher(charSequence.toString()).matches()){
                    email.setError("Email not valid");
                    isOkToContinue = false;
                }else {
                    isOkToContinue = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        link.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!Patterns.WEB_URL.matcher(charSequence.toString()).matches()){
                    link.setError("Link not valid");
                    isOkToContinue = false;
                }else {
                    isOkToContinue = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void init() {
        firstName = findViewById(R.id.first);
        lastName = findViewById(R.id.last);
        email = findViewById(R.id.email);
        link = findViewById(R.id.github_link);
        progressDialog = new ProgressDialog(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Retrofits.POST_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HoursApi = retrofit.create(HoursApi.class);
    }

    public void work(View view) {
        finish();
    }

    public void sumitForm(View view) {
        if (TextUtils.isEmpty(firstName.getText().toString()) &&
                TextUtils.isEmpty(lastName.getText().toString()) &&
                TextUtils.isEmpty(email.getText().toString()) &&
                TextUtils.isEmpty(link.getText().toString())){
            Toast.makeText(this, "Something is not right, Please check the fields", Toast.LENGTH_LONG).show();
        }else {
            if (isOkToContinue){
                addForm();
            }else {
                Toast.makeText(this, "Did you use a valid email or github link", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void addForm() {
        selectAction(this);
    }

    public void selectAction(Context context){
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.popup, null);
        Button yes = dialogView.findViewById(R.id.yes);
        ImageView cancel = dialogView.findViewById(R.id.cancel_button);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                    uploadForm();
                }
            });
    }

    public void dialogSucsess(Context context){
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.popup_successful, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void dialogfail(Context context){
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.popup_failed, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void uploadForm() {
        progressDialog.show();
        String first = firstName.getText().toString();
        String last = lastName.getText().toString();
        String email_ = email.getText().toString();
        String github_link = link.getText().toString();

        Call<Void> call = HoursApi.createPost(email_, first, last, github_link);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                progressDialog.hide();
                if (!response.isSuccessful()) {
                    return;
                }
                if (response.code() == 200){
                    progressDialog.hide();
                    dialogSucsess(Submit.this);
                }else {
                    progressDialog.hide();
                    dialogfail(Submit.this);
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progressDialog.hide();
                dialogfail(Submit.this);
            }
        });
    }
}