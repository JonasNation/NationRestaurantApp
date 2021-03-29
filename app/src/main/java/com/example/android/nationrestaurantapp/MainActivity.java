package com.example.android.nationrestaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onWelcomeButtonClick(View view) {
        Intent menu = new Intent(this, Menu.class);
        startActivity(menu);
    }

}