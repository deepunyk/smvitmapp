package com.xoi.smvitm.academics;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.xoi.smvitm.R;

import java.util.HashMap;
import java.util.Map;

public class addevent extends AppCompatActivity {
    EditText etdate,ettitle,etday;
    Button btnupdate;
    String date,title,day;
    String url = "http://smvitmapp.xtoinfinity.tech/php/addcalendarevent.php";

    TextView checkTxt;

    int num = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addevent);

        etdate=(EditText) findViewById(R.id.etdate);
        etdate.setText("2020-MM-DD");
        ettitle=(EditText) findViewById(R.id.ettitle);
        etday=(EditText) findViewById(R.id.etday);
        btnupdate=(Button) findViewById(R.id.btnadd);

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title=ettitle.getText().toString();
                date=etdate.getText().toString();
                day=etday.getText().toString();
                if(title.equals("") || date.equals("") || day.equals(""))
                {
                    Toast.makeText(addevent.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }else
                {
                    ProgressDialog progressdialog = new ProgressDialog(addevent.this);
                    progressdialog.setMessage("Please Wait....");
                    progressdialog.setCancelable(false);
                    progressdialog.show();
                    update();
                    progressdialog.dismiss();
                }
            }
        });

    }
    public void update(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(addevent.this, ""+response, Toast.LENGTH_SHORT).show();
                        if(response.equals("success")){
                            Intent intent = new Intent(addevent.this, MainActivitycal.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(addevent.this, ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("title",title);
                // params.put("pass",pass);
                params.put("date",date);
                params.put("day",day);

                return params;
            };

        };
        int socketTimeOut = 50000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

}
