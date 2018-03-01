package com.example.dev.firebaseanalyticsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv;
    private FirebaseAnalytics firebaseAnalytics;
    String[] foods;
    private Button btn, btn2, btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        btn = (Button) findViewById(R.id.btn);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        foods = new String[]{"Apple", "Banana", "Grape", "Mango", "Orange"};

        // Obtain the Firebase Analytics instance.
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Food food = new Food();
        food.setId(1);
        // choose random food name from the list
        food.setName(foods[randomIndex()]);
        tv.setText(food.getName());
        Bundle bundle = new Bundle();
        bundle.putInt(FirebaseAnalytics.Param.ITEM_ID, food.getId());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, food.getName());

        //Logs an app event.
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        //Sets whether analytics collection is enabled for this app on this device.
        firebaseAnalytics.setAnalyticsCollectionEnabled(true);

        //Sets the minimum engagement time required before starting a session. The default value is 10000 (10 seconds). Let's make it 20 seconds just for the fun
        firebaseAnalytics.setMinimumSessionDuration(20000);

        //Sets the duration of inactivity that terminates the current session. The default value is 1800000 (30 minutes).
        firebaseAnalytics.setSessionTimeoutDuration(500);

        //Sets the user ID property.
        firebaseAnalytics.setUserId(String.valueOf(food.getId()));

        //Sets a user property to a given value.
        firebaseAnalytics.setUserProperty("Food", food.getName());
    }

    private int randomIndex() {
        int min = 0;
        int max = foods.length - 1;
        Random rand = new Random();
        return min + rand.nextInt((max - min) + 1);
    }

    @Override
    public void onClick(View view) {
        Bundle param = new Bundle();
        param.putInt("ButtonId", view.getId());
        String btnName = null;
        if (view.getId() == R.id.btn) {
            btnName = "one";
            Toast.makeText(MainActivity.this, btnName, Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.btn2) {
            btnName = "two";
            Toast.makeText(MainActivity.this, btnName, Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.btn3) {
            btnName = "three";
            Toast.makeText(MainActivity.this, btnName, Toast.LENGTH_SHORT).show();
        }
        firebaseAnalytics.logEvent(btnName, param);
    }
}
