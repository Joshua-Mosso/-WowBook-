package com.example.wowbook;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SignUp extends android.app.Activity {
    //declaração das variaveis
    private EditText emailInput , passwordInput , repeatPassword;
    private Button login;
    ImageView wowbook;

    //metodos declarados
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //imagem do logotipo
        wowbook = (ImageView) findViewById(R.id.logoWow);
        Picasso.get().load(R.drawable.logo_wowbook).into(wowbook);

        //identificação das variaveis
        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.pass);


        login = findViewById(R.id.sign_up);


        //declaração da Base de dados
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("users");

        //função para registrar novo usuario
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();



                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            firebaseAuth.getCurrentUser().sendEmailVerification();
                            firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                 if (task.isSuccessful()){

                                     startActivity(new Intent(SignUp.this,MainActivity.class));
                                     firebaseAuth.getInstance().signOut();

                                 }else{
                                     Toast.makeText(SignUp.this,"Por favor verifica o seu email de verificação", Toast.LENGTH_SHORT).show();
                                     startActivity(new Intent(SignUp.this,MainActivity.class));

                                     firebaseAuth.getInstance().signOut();
                                 }
                                }
                            });


                        }
                    }
                });
            }

        });

    }

}
