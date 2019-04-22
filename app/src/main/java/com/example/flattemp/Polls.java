package com.example.flattemp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.flattemp.Adaptor.ReciptAdapter;
import com.example.flattemp.Model.Recipt;
import com.example.flattemp.Model.UrlsList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Polls extends AppCompatActivity {
    TextView tquestion;
    String pollid,selectedpoll,selectedpollid;
ArrayList<String> rbutton;
ArrayList<String> roption;
Button  vote,result;
RadioGroup radioGroup;

RadioButton radioButton,radioButton1,radioButton2,radioButton3,radioButton4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polls);
        tquestion=findViewById(R.id.tquestion);
        rbutton=new ArrayList<>();
        roption=new ArrayList<>();
        tquestion.setText("");
        load();

        vote=findViewById(R.id.bvote);


    }


    private void load() {
        //String URL_member="http://pivotnet.co.in/SocietyManagement/Android/fetch_poll_question.php";
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlsList.fetch_poll_question,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            JSONObject user = array.getJSONObject(0);
                            // JSONObject user=new JSONObject(response);
                            tquestion.setText(user.getString("subject"));
                            pollid=user.getString("id");
                            loadUsers();


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
                params.put("email", "gh");
                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void loadUsers() {

//String URL_PRODUCTS="http://pivotnet.co.in/SocietyManagement/Android/fetch_poll_options.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlsList.fetch_poll_option,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            // Toast.makeText(getActivity(),"entered"+array.length(),Toast.LENGTH_SHORT).show();
                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject user = array.getJSONObject(i);
                                 rbutton.add(user.getString("name"));
                                roption.add(user.getString("id"));
                                //adding the product to product list
                                // String pay_id,pay_date,mem_id,mem_name, mem_flat_num, mem_flat_type, pay_fixed,
                                // pay_deposit,pay_remaining, pay_month,pay_status;

                            }


                            toast();
                            //creating adapter object and setting it to recyclerview

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put("id", pollid);
                //returning parameter
                return params;
            }
        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

    void toast(){
        radioGroup=findViewById(R.id.radioGroup);
        radioButton1=findViewById(R.id.radio1);
         radioButton2=findViewById(R.id.radio2);
        radioButton3=findViewById(R.id.radio3);
     radioButton4=findViewById(R.id.radio4);


        radioButton1.setText(rbutton.get(0));
        radioButton2.setText(rbutton.get(1));
        radioButton3.setText(rbutton.get(2));
        radioButton4.setText(rbutton.get(3));
        vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
votepoll();
            }
        });


    }



    public void checkreadio(View view) {
        int bid=radioGroup.getCheckedRadioButtonId();

        radioButton=findViewById(bid);
        selectedpoll=radioButton.getText().toString();

        if (bid == R.id.radio1){
            selectedpollid=roption.get(0);
        }
        else if (bid == R.id.radio2){
            selectedpollid=roption.get(1);
            Toast.makeText(getApplicationContext(),selectedpollid,Toast.LENGTH_SHORT).show();
        }
        else
        if (bid == R.id.radio3){
            selectedpollid=roption.get(2);
            Toast.makeText(getApplicationContext(),selectedpollid,Toast.LENGTH_SHORT).show();
        }
        else
        if (bid == R.id.radio4){
            selectedpollid=roption.get(3);
            Toast.makeText(getApplicationContext(),selectedpollid,Toast.LENGTH_SHORT).show();
        }


    }


    private void votepoll() {

        //String URL_PRODUCTS="http://pivotnet.co.in/SocietyManagement/Android/vote_poll.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlsList.vote_poll_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),"sucessfully",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put("id", selectedpollid);
                params.put("pid", pollid);
                //returning parameter
                return params;
            }
        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }
}
