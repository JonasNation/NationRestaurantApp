package com.example.android.nationrestaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Deals extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals);
    }
    // button that leads to the codes
    public void onPromoButtonClick(View view) {
        Intent promo = new Intent(this, PromotionalCodes.class);
        startActivity(promo);
    }
    // button that leads to the coupons
    public void onCouponButtonClick(View view) {
        Intent coupon = new Intent(this, CouponDeals_.class);
        startActivity(coupon);
    }
}