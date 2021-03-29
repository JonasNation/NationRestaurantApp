 package com.example.android.nationrestaurantapp;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Set;

public class Settings extends AppCompatActivity {
    private BluetoothAdapter bluetooth;
    private Set<BluetoothDevice>pairedDevices;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);

        bluetooth = BluetoothAdapter.getDefaultAdapter();
        // checks if bluetooth is available
        Toast toast;
        if (bluetooth == null) {
            toast = Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG);
        }else {
            toast = Toast.makeText(this, "Bluetooth is available", Toast.LENGTH_LONG);
        }
        toast.show();

        list = (ListView) findViewById(R.id.listView);
    }

    // create a broadcast receiver for action found
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //discovery has found a device and gets the bluetooth device info
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress();
            }
        }
    };

    public void onSwitchClickOn(View view) {
        if (!bluetooth.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
            Toast.makeText(getApplicationContext(), "Bluetooth turned on", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getApplicationContext(), "Already on", Toast.LENGTH_LONG).show();
        }
    }

    public void onSwitchClickOff(View view) {
        if (bluetooth.isEnabled()) {
            bluetooth.disable();
            Toast.makeText(getApplicationContext(), "Bluetooth turned off", Toast.LENGTH_LONG).show();
        }
    }

    public void makeVisibleButton(View view) {
        if (!bluetooth.isDiscovering()) {
            Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            getVisible.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivityForResult(getVisible, 0);
        }
    }

    public void viewPairedDevices(View view) {
        if (bluetooth.isEnabled()) {
            pairedDevices = bluetooth.getBondedDevices();
            ArrayList blueList = new ArrayList();

            if (pairedDevices.size() > 0) {
                //gets name and address of every paired device
                for (BluetoothDevice device : pairedDevices) {
                    blueList.add(device.getName());
                    blueList.add(device.getAddress());
                    Toast.makeText(getApplicationContext(), "Showing Paired Devices", Toast.LENGTH_SHORT).show();
                }
            }

            final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, blueList);
            list.setAdapter(adapter);
        }else {
            Toast toast = Toast.makeText(this, "Turn on bluetooth", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @SuppressLint("ShowToast")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 0) {
            Toast toast;
            if (resultCode == RESULT_OK) {
                toast = Toast.makeText(this, "Bluetooth is on", Toast.LENGTH_SHORT);
            } else {
                toast = Toast.makeText(this, "Bluetooth is not on", Toast.LENGTH_SHORT);
            }
            toast.show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(receiver);
    }
}

