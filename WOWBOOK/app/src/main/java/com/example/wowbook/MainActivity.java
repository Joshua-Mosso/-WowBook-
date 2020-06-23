package com.example.wowbook;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;


public class MainActivity extends android.app.Activity {

    private static final int RC_SIGN_IN = 101 ;
    EditText emailInput, passwordInput;
    Button loginButton;
    Button  signUpButton;
    ImageView wowbook;

    private FirebaseAuth firebaseAuth;
    private Button btnGoogle;
    private FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //imagem do logotipo
        wowbook = (ImageView) findViewById(R.id.logoWow);
        Picasso.get().load(R.drawable.logo_wowbook).into(wowbook);

        //identificação das variaveis com os IDs
        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.pass);
        loginButton = findViewById(R.id.login);
        signUpButton = (Button) findViewById(R.id.login2);
        btnGoogle = findViewById(R.id.googleLogin);

        //declaração da firebase
        firebaseAuth = FirebaseAuth.getInstance();

        //função de login com google

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient= GoogleSignIn.getClient(this, gso);

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

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

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
                startActivity(new Intent(MainActivity.this,HomePage.class));

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, user.getEmail()+ user.getDisplayName(), Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        Intent intent = new Intent( MainActivity.this, HomePage.class);
        startActivity(intent);
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
