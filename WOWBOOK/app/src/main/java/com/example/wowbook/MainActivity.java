package com.example.wowbook;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;


public class MainActivity extends android.app.Activity {

    EditText emailInput, passwordInput;
    Button loginButton;
    Button  signUpButton;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //identificação das variaveis com os IDs
        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.pass);
        loginButton = findViewById(R.id.login);
        signUpButton = (Button) findViewById(R.id.login2);

        //declaração da firebase
        firebaseAuth = FirebaseAuth.getInstance();


        //função que vai chamar a pagina de SignUp , caso o utilizador não tenha uma conta existente

        signUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SignUp.class));
            }
        });



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emailInput.getText().toString().isEmpty() && !passwordInput.getText().toString().isEmpty()){
                    //chamar a função que vai validar o login
                    validate(emailInput.getText().toString(),passwordInput.getText().toString());
                }else{

                    Toast.makeText(MainActivity.this,"Por favor preencha os campos",Toast.LENGTH_SHORT).show();
                }
            }
        });
        

    }



    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    //função para validação do email
    private void validate(String email , String password) {
        firebaseAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(!task.isSuccessful()){

                    Toast.makeText(MainActivity.this,"Este email não está registrado", Toast.LENGTH_SHORT).show();
                }else {
                    //verificação se o email existe

                    checkIfEmailVerified();

                }
            }
        });

    }

    //função de verificação do email na base de dados
    private void checkIfEmailVerified() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        assert user != null;
        if (user.isEmailVerified()){
            Toast.makeText(this, "Seja Bem Vindo", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, HomePage.class));
        }else{
            Toast.makeText(this, "Conta inválida", Toast.LENGTH_SHORT).show();
        }
    }

}
