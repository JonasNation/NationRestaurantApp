 package com.example.android.nationrestaurantapp;

 import android.content.Intent;
 import android.database.Cursor;
 import android.os.Bundle;
 import android.speech.RecognizerIntent;
 import android.speech.tts.TextToSpeech;
 import android.view.View;
 import android.widget.EditText;
 import android.widget.ImageButton;
 import android.widget.Toast;

 import androidx.appcompat.app.AlertDialog;
 import androidx.appcompat.app.AppCompatActivity;

 import java.util.List;
 import java.util.Locale;

 public class ReservationForm extends AppCompatActivity {
     EditText First_Name, Last_Name, Email_Address, Phone_Number, Number_Of_Guest, Date_Of_Reservation, Time_Of_Reservation;
     ImageButton speak;
     TextToSpeech speech;

     DatabaseHelper myDb;

    private static final int SPEECH_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_form);

        myDb = new DatabaseHelper(this);

        First_Name = findViewById(R.id.editTextTextPersonNameFirst);
        Last_Name = findViewById(R.id.editTextTextPersonNameLast);
        Email_Address = findViewById(R.id.editTextTextEmailAddress);
        Phone_Number = findViewById(R.id.editTextPhone);
        Number_Of_Guest = findViewById(R.id.editTextNumberOfGuest);
        Date_Of_Reservation = findViewById(R.id.editTextDate);
        Time_Of_Reservation = findViewById(R.id.editTextTime);
        speak = findViewById(R.id.imageButtonVoice);
        // set language to english
        speech = new TextToSpeech(getApplicationContext(), i -> {
            if (i != TextToSpeech.ERROR) {
                speech.setLanguage(Locale.US);
            }
        });

        speak.setOnClickListener(view -> {
            Intent speaking = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            speaking.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            startActivityForResult(speaking, SPEECH_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // sends captured audio to editText
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            First_Name.setText(results.get(0));
            Last_Name.setText(results.get(0));
            Email_Address.setText(results.get(0));
            Phone_Number.setText(results.get(0));
            Number_Of_Guest.setText(results.get(0));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

     public void backToMenuButton(View view) {
        Intent menu = new Intent(this, Menu.class);
        startActivity(menu);
     }

     public void makeReservation(View view) {
         boolean nowInserted = myDb.insertReservation(First_Name.getText().toString(), Last_Name.getText().toString(), Email_Address.getText().toString(), Phone_Number.getText().toString(), Number_Of_Guest.getText().toString(), Date_Of_Reservation.getText().toString(), Time_Of_Reservation.getText().toString());
         if(nowInserted)
             Toast.makeText(this,"Reservation Submitted",Toast.LENGTH_LONG).show();
         else
             Toast.makeText( this,"Reservation failed",Toast.LENGTH_LONG).show();

     }

     public void viewReservation(View view) {
         Cursor res = myDb.getAllReservations();
         if(res.getCount() == 0) {
             // show message
             showMessage("Error", "No Reservation");
             return;
         }

         StringBuffer buffer = new StringBuffer();
         while (res.moveToNext()) {
             buffer.append("Reservation: " + res.getString(0) + "\n");
             buffer.append("First Name: " + res.getString(1) + "\t");
             buffer.append("Last Name: " + res.getString(2) + "\n");
             buffer.append("Email Address: " + res.getString(3) + "\n");
             buffer.append("Phone Number: " + res.getString(4) + "\n");
             buffer.append("Number of Guest: " + res.getString(5) + "\n");
             buffer.append("Date of Reservation: " + res.getString(6) + "\n");
             buffer.append("Time of Reservation: " + res.getString(7) + "\n\n");
         }
         // Show all data
         showMessage("Thanks for Your Reservation",buffer.toString());
     }

     public void showMessage(String title,String Message){
         AlertDialog.Builder builder = new AlertDialog.Builder(this);
         builder.setCancelable(true);
         builder.setTitle(title);
         builder.setMessage(Message);
         builder.show();
     }

     public void cancelReservation(View view) {
         boolean isDeleted = myDb.deleteReservation(Date_Of_Reservation.getText().toString());
         if (isDeleted)
             Toast.makeText(this,"Reservation Canceled",Toast.LENGTH_LONG).show();
         else
             Toast.makeText(this,"Reservation not Canceled",Toast.LENGTH_LONG).show();
     }
 }