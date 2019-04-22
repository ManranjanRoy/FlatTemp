package com.example.flattemp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.flattemp.Adaptor.ImageAdapter;
import com.example.flattemp.Model.Config;
import com.example.flattemp.Model.UrlsList;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AccountPayment extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    String semail;
    //String URL_PRODUCTS="http://pivotnet.co.in/SocietyManagement/Android/pay_rent_android.php";
    //String URL_member="http://pivotnet.co.in/SocietyManagement/Android/fetchmemberdata.php";
    TextView tname,temail,tflatno,tammount,tremaining,twarning;
    Spinner  spin;
    EditText epaid;
    Button submit;
    String id1,name1,flatno1,flattype1,mno1,mnth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_payment);
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        semail = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");
        load();

        init();
        printdata();

    }

    private void printdata() {


    }

    private void init() {
        twarning=findViewById(R.id.twarning);
        tname=(TextView)findViewById(R.id.tname);
        temail=(TextView)findViewById(R.id.temail);
        tflatno=(TextView)findViewById(R.id.tflatno);
        tammount=(TextView)findViewById(R.id.tammount);
        tremaining=(TextView)findViewById(R.id.tremaining);
        epaid=(EditText) findViewById(R.id.epaid);
        submit=findViewById(R.id.submit);
        spin=findViewById(R.id.tmonth);
        epaid.setText("5000");
        String[] objects = { "January", "Feburary", "March", "April", "May",
                "June", "July", "August", "September", "October", "November","December" };

// Declaring an Adapter and initializing it to the data pump
        ArrayAdapter adapter = new ArrayAdapter(
                getApplicationContext(),android.R.layout.simple_list_item_1 ,objects);

// Setting Adapter to the Spinner
        spin.setAdapter(adapter);

// Setting OnItemClickListener to the Spinner
        spin.setOnItemSelectedListener(this);


        epaid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String m=epaid.getText().toString();
                if (!m.isEmpty()) {
                    Double a = Double.parseDouble(m);
                    Double b = Double.parseDouble(tammount.getText().toString());
                    if (a>b){
                        twarning.setVisibility(View.VISIBLE);
                        submit.setEnabled(false);

                    }
                    else{
                        Double c = b - a;
                        tremaining.setText(c.toString());
                        twarning.setVisibility(View.GONE);
                        submit.setEnabled(true);}
                }
                else{

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        final Date date1= Calendar.getInstance().getTime();
        DateFormat setdate=new SimpleDateFormat("dd/MM/yy");
        final String date=setdate.format(date1);
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlsList.pay_rent_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //If we are getting success from server
                        Toast.makeText(getApplicationContext(), "Sucessfully Submitted", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(AccountPayment.this,ViewRecipt.class));

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // progressDialog.dismiss();
                        //You can handle error here if you want
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put("mem_id", id1);
                params.put("mem_name",name1);
                params.put("mem_flat_num",flatno1);
                params.put("mem_flat_type", flattype1);
                params.put("pay_fixed",tammount.getText().toString());
                params.put("pay_remaining",tremaining.getText().toString());
                params.put("pay_deposit", epaid.getText().toString());
                params.put("pay_month",mnth);
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
                            flatno1=user.getString("mem_flat_num");
                            flattype1=user.getString("mem_flat_type");
                            mno1=user.getString("mem_phone_num");

                            tname.setText(name1);
                            temail.setText(flattype1);
                            tflatno.setText(flatno1);
                            if (flattype1.equals("1 BHK")){
                                tammount.setText("5500");
                            }
                            else if (flattype1.equals("2 BHK")){
                                //tammount.setText("5500");
                                tammount.setText("10500");
                            }
                            else if (flattype1.equals("3 BHK")){
                                //tammount.setText("5500");
                                tammount.setText("15500");
                            }

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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        mnth= spin.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Adding our menu to toolbar
        getMenuInflater().inflate(R.menu.view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuview) {
            //calling logout method when the logout button is clicked
            startActivity(new Intent(AccountPayment.this,ViewRecipt.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
