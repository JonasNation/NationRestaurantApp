package com.example.android.nationrestaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }
    // button that moves to menu activity
    public void onOrderButtonClick(View view) {
        Intent order = new Intent(this, Orders.class);
        startActivity(order);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

 /*   public boolean onOptionsItemSelected(MenuItem item) {
        // handles item selection
        switch (item.getItemId()) {
            case R.id.action_maps:
                return true;
            case R.id.action_reserve:
                return true;
            case R.id.action_deals:
                return true;
            case R.id.action_coupons:
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }*/
    // button in menu that navigates to the maps activity
   public void onLocationClick(MenuItem item) {
        Intent maps = new Intent(this, MapsActivity.class);
        startActivity(maps);
    }
    // button in menu that navigates user to the promotional deals
    public void onPromoClick(MenuItem item) {
       Intent promo = new Intent(this, Deals.class);
       startActivity(promo);
    }
    // button in menu that navigates user to the coupons activity
    public void onCouponClick(MenuItem item) {
       Intent coupon = new Intent(this, Deals.class);
       startActivity(coupon);
    }
    // button in menu that navigates user to the reservation activity
    public void onClickReserve(MenuItem item) {
       Intent reserve = new Intent(this, ReservationForm.class);
       startActivity(reserve);
    }
    // button in menu that navigates user to settings
    public void onSettingsClick(MenuItem item) {
       Intent settings = new Intent(this, Settings.class);
       startActivity(settings);
    }


    // --Commented out by Inspection START (10/27/2020 10:11 PM):
//    public void onOrder_ButtonClick(View view) {
//        Intent order = new Intent(this, Orders.class);
//        startActivity(order);
//    }
// --Commented out by Inspection STOP (10/27/2020 10:11 PM)
}