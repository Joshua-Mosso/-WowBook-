package com.example.wowbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Direção para a pagina de signUp
        Button signButton = (Button) findViewById(R.id.sign_up);
        Intent intent = new Intent(this, SignUp.class);

    }

}
