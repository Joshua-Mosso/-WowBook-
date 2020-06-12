package com.example.wowbook;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Category extends android.app.Activity {

    Button next ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        next = findViewById(R.id.seguinte);

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Category.this,MainActivity.class));
            }
        });
    }
}
