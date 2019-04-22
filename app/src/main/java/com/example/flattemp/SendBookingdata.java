package com.example.flattemp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.flattemp.Model.Config;
import com.example.flattemp.Model.UrlsList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SendBookingdata extends AppCompatActivity {
    //String URL_member="http://pivotnet.co.in/SocietyManagement/Android/fetchmemberdata.php";
    //String url_booking="http://pivotnet.co.in/SocietyManagement/Android/booking_request.php";
    //String url_date="http://pivotnet.co.in/SocietyManagement/Android/fetch_booking_date.php";
    String cat,semail;
    TextView tdate,tfacility,tid,tname,tmobno,warningtext;
    EditText ereason;
    ImageButton datepicker;
    String id1,name1,mno1,datepicked="",reason;
    Button submit;
    ProgressDialog progressDialog;
    private DatePickerDialog.OnDateSetListener mDatesetlistner;

    @Override
    protected void onStart() {
        super.onStart();
        load();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_bookingdata);
        Intent intent = getIntent();
        cat = intent.getStringExtra("icon");
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        semail = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");
        warningtext=findViewById(R.id.warningtxt);
        tdate=findViewById(R.id.tdate);
        tdate.setText("");
        tfacility=findViewById(R.id.tfacility);
        tid=findViewById(R.id.tid);
        tname=findViewById(R.id.tname);
        tmobno=findViewById(R.id.tmobno);
        ereason=findViewById(R.id.ereason);
        submit=findViewById(R.id.submit);
        progressDialog=new ProgressDialog(SendBookingdata.this);
        progressDialog.setMessage("Please wait while sending  your request");
        tfacility.setText(cat);


        datepicker=findViewById(R.id.datebutton);
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DATE);


                DatePickerDialog dailog= new DatePickerDialog(SendBookingdata.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDatesetlistner,year,month,day);
                dailog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dailog.show();
            }
        });
        mDatesetlistner=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                datepicked=  dayOfMonth+"/"+month+"/"+year;
                tdate.setText(datepicked);
                loaddate(datepicked);


            }
        };


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reason=ereason.getText().toString();
                if (datepicked.equals("") || reason.equals("")){
                    Toast.makeText(getApplicationContext(),"Please Enter Reason or seletct date",Toast.LENGTH_SHORT).show();
                }
                else{
                    register();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        load();
    }

    private void register() {
        //Getting values from edit texts
        progressDialog.show();



        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST,UrlsList.send_booking_request,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //If we are getting success from server
                        Toast.makeText(getApplicationContext(),"Sucessfully Submitted your request",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        startActivity(new Intent(SendBookingdata.this,BookingHistory.class));
                        //Starting profile activity


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        //You can handle error here if you want
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put("mem_id", id1);
                params.put("mem_name", name1);
                params.put("mob", mno1);
                params.put("facility", cat);
                params.put("date",tdate.getText().toString());
                params.put("reason", reason);
                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }




    private void load() {

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlsList.fetch_members_details_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            JSONObject user = array.getJSONObject(0);
                            // JSONObject user=new JSONObject(response);
                            id1=user.getString("mem_id");
                            name1=user.getString("mem_name");
                            mno1=user.getString("mem_phone_num");

                            tname.setText(name1);
                            tid.setText(id1);
                            tmobno.setText(mno1);


                        }
                        catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //You can handle error here if you want
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put("email", semail);
                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void loaddate(final String datepicked) {

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlsList.fetch_booking_dates,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equalsIgnoreCase(Config.LOGIN_SUCCESS)){
                            warningtext.setVisibility(View.VISIBLE);
                            submit.setEnabled(false);

                            //Creating a shared preference


                        }else{

                            //If the server response is not success
                            //Displaying an error message on toast
                            warningtext.setVisibility(View.GONE);
                            submit.setEnabled(true);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //You can handle error here if you want
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put("email", datepicked);
                params.put("cat",cat);
                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
