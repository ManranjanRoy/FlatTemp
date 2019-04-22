package com.example.flattemp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class BookingActivity extends AppCompatActivity {
    Button garden,benquete,theater,swimmingpool,resturent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        garden=findViewById(R.id.bgarden);
        benquete=findViewById(R.id.bbenquete);
        theater=findViewById(R.id.btheater);
        swimmingpool=findViewById(R.id.bswimmingpool);
        resturent=findViewById(R.id.bresturent);
        garden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(BookingActivity.this, SendBookingdata.class);
                i.putExtra("icon","Garden");
                startActivity(i);
            }
        });
        benquete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(BookingActivity.this, SendBookingdata.class);
                i.putExtra("icon","Banquet Hall");
                startActivity(i);
            }
        });
        theater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(BookingActivity.this, SendBookingdata.class);
                i.putExtra("icon","Theatre");
                startActivity(i);
            }
        });
        swimmingpool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(BookingActivity.this, SendBookingdata.class);
                i.putExtra("icon","Swimming Pool");
                startActivity(i);
            }
        });
        resturent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(BookingActivity.this, SendBookingdata.class);
                i.putExtra("icon","Restaurent");
                startActivity(i);
            }
        });



    }
}
