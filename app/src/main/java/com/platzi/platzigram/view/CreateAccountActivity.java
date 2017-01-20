package com.platzi.platzigram.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.platzi.platzigram.LoginActivity;
import com.platzi.platzigram.R;

public class CreateAccountActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    boolean validRecords = true;

    TextInputLayout etEmail;
    TextInputLayout etName;
    TextInputLayout etUser;
    TextInputLayout etPassword;
    TextInputLayout etConfirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        showToolbar(getResources().getString(R.string.toolbar_tittle_createaccount), true);

        firebaseAuth = FirebaseAuth.getInstance();

        Button btnCreateAccount = (Button) findViewById(R.id.joinUs);
        etEmail = (TextInputLayout) findViewById(R.id.email);
        etName = (TextInputLayout) findViewById(R.id.name);
        etUser = (TextInputLayout) findViewById(R.id.user);
        etPassword = (TextInputLayout) findViewById(R.id.password_createaccount);
        etConfirmPass = (TextInputLayout) findViewById(R.id.confirmPassword);

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = getText(etEmail);
                String userName = getText(etName);
                String user = getText(etUser);
                String password = getText(etPassword);
                String confirmation = getText(etConfirmPass);
                cleanElements();

                if (isEmpty(email)){
                    etEmail.setError(getString(R.string.create_account_error_email_address));
                    validRecords = false;
                }

                if (isEmpty(userName)){
                    etName.setError(getString(R.string.create_account_error_name));
                    validRecords = false;
                }

                if (isEmpty(user)){
                    etUser.setError(getString(R.string.create_account_error_user_name));
                    validRecords = false;
                }

                if (isEmpty(password)){
                    etPassword.setError(getString(R.string.create_account_error_password));
                    validRecords = false;
                }

                if (!password.equals(confirmation)){
                    etPassword.setError(getString(R.string.create_acccount_error_confirm_password));
                    validRecords = false;
                }

                if(validRecords){

                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (!task.isSuccessful()) {
                                        Toast.makeText(CreateAccountActivity.this, "Auth failed" + task.getException(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
                                        finish();
                                    }
                                }
                            });
                }

            }
        });

    }

    private void cleanElements() {
        etEmail.setError(null);
        etName.setError(null);
        etUser.setError(null);
        etPassword.setError(null);
        etConfirmPass.setError(null);
    }

    private boolean isEmpty(String text) {
        return text.equals("") || text == null;
    }

    @NonNull
    private String getText(TextInputLayout etEmail) {
        return etEmail.getEditText().getText().toString().trim();
    }

    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }
}
