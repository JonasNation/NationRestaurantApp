package com.example.android.nationrestaurantapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class Orders extends AppCompatActivity {
     double total = 0d;
     int[] checkBoxes = {R.id.checkBoxAppleJuice, R.id.checkBoxBaconBurger, R.id.checkBoxCoca_Cola, R.id.checkBoxSprite, R.id.checkBoxFanta, R.id.checkBoxHi_C,
                                R.id.checkBoxBargs, R.id.checkBoxIce_Tea, R.id.checkBoxSweetTea, R.id.checkBoxWater, R.id.checkBoxCheeseBurger, R.id.checkBoxHouseBurger,
                                R.id.checkBoxBLT, R.id.checkBoxClub, R.id.checkBoxChicken, R.id.checkBoxReuben};

     String completeTotal;
     String label;
     String drink;
     TextView totalPurchase;
     DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        totalPurchase = findViewById(R.id.totalPurchase);

        myDb = new DatabaseHelper(this);
    }


    // back to menu button
    public void backToMenuButton(View view) {
        Intent backToMenu = new Intent(this, Menu.class);
        startActivity(backToMenu);
    }


   @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    public void onCheckBoxClicked(View view) {
        // checks for checked buttons
        boolean checked = ((CheckBox) view).isChecked();
        // shows which button was checked
        switch (view.getId()) {
            // Hamburger menu options check box options
            case R.id.checkBoxBaconBurger:
                if (checked) {
                    label = getString(R.string.burgerBacon);
                    total += 9.45;
                }else
                    total -= 9.45;
                break;
            case R.id.checkBoxCheeseBurger:
                if (checked) {
                    label = getString(R.string.burgerCheese);
                    total += 8.45;
                }else
                    total -= 8.45;
                break;
            case R.id.checkBoxHouseBurger:
                if (checked) {
                    label = getString(R.string.burgerHouse);
                    total += 11.45;
                }else
                    total -= 11.45;
                break;
            // Sandwiches menu check box options
            case R.id.checkBoxBLT:
                if (checked) {
                    label = getString(R.string.sandBLT);
                    total += 3.99;
                }else
                    total -= 3.99;
                break;
            case R.id.checkBoxClub:
                if (checked) {
                    label = getString(R.string.sandClub);
                    total += 4.30;
                }else
                    total -= 4.30;
                break;
            case R.id.checkBoxChicken:
                if (checked) {
                    label = getString(R.string.sandChicken);
                    total += 4.25;
                }else
                    total -= 4.25;
                break;
            case R.id.checkBoxReuben:
                if (checked) {
                    label = getString(R.string.sandReuben);
                    total += 5.25;
                }else
                    total -= 5.25;
                break;
            // Drinks menu check box options
            case R.id.checkBoxCoca_Cola:
                if (checked) {
                    drink = getString(R.string.sodaCola);
                    total += 2.00;
                }else
                    total -= 2.00;
                break;
            case R.id.checkBoxSprite:
                if (checked) {
                    drink = getString(R.string.sodaSprite);
                    total += 2.00;
                }else
                    total -= 2.00;
                break;
            case R.id.checkBoxFanta:
                if (checked) {
                    drink = getString(R.string.sodaFanta);
                    total += 2.00;
                }else
                    total -= 2.00;
                break;
            case R.id.checkBoxHi_C:
                if (checked) {
                    drink = getString(R.string.sodaHi);
                    total += 2.00;
                }else
                    total -= 2.00;
                break;
            case R.id.checkBoxBargs:
                if (checked) {
                    drink = getString(R.string.sodaBargs);
                    total += 2.00;
                }else
                    total -= 2.00;
                break;
            case R.id.checkBoxIce_Tea:
                if (checked) {
                    drink = getString(R.string.teaIce);
                    total += 2.00;
                }else
                    total -= 2.00;
                break;
            case R.id.checkBoxSweetTea:
                if (checked) {
                    drink = getString(R.string.tea);
                    total += 2.00;
                }else
                    total -= 2.00;
                break;
            case R.id.checkBoxAppleJuice:
                if (checked) {
                    drink = getString(R.string.juice);
                    total += 1.25;
                }else
                    total -= 1.25;
                break;
            case R.id.checkBoxWater:
                if (checked) {
                    drink = getString(R.string.water);
                    total += 0.00;
                }
                break;
        }
        // sets the format for price of menu items and shows selected checkbox in a textView
        DecimalFormat buy = new DecimalFormat("#0.00");
        completeTotal = buy.format(total);
        totalPurchase.setText(label + "\n" + drink + "\n" + "Amount due: $" + completeTotal);
    }
    // button for submitting data into SQLite database
    public void onSubmitOrderButton(View view) {
        boolean isInserted = myDb.insertOrder(totalPurchase.getText().toString());
        if(isInserted)
            Toast.makeText(this,"Order Submitted",Toast.LENGTH_LONG).show();
        else
            Toast.makeText( this,"Order failed",Toast.LENGTH_LONG).show();

    }
    // button for retrieving data from SQLite database
    public void onReceiptButtonClick(View view) {
        Cursor res = myDb.getAllOrders();
        if(res.getCount() == 0) {
            // show message
            showMessage("Error", "Nothing found");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("Order #: " + res.getString(0) + "\n");
            buffer.append(res.getString(1) + "\n\n");
        }
        // Show all data
        showMessage("Thanks for Your Order",buffer.toString());
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
    // deletes data from SQLite database
    public void deleteOrder(View view) {
       boolean isDeleted = myDb.deleteOrder(totalPurchase.getText().toString());
        if (isDeleted)
            Toast.makeText(this,"Order Canceled",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this,"Order not Canceled",Toast.LENGTH_LONG).show();
    }
}