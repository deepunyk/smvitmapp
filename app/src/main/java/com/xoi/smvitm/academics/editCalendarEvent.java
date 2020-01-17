package com.xoi.smvitm.academics;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
public class editCalendarEvent extends AppCompatActivity {
    EditText etdate,ettitle,etday;
    Button btnupdate;
    String date,title,day,dtitle,ddate,dday;
    String url = "http://smvitmapp.xtoinfinity.tech/php/editcalendarevent.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_calendar_event);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tbedit);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit event");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dtitle = getIntent().getExtras().getString("dtitle1");
        ddate = getIntent().getExtras().getString("ddate1");
        dday = getIntent().getExtras().getString("dday1");
        etdate=(EditText) findViewById(R.id.etdate1);
        ettitle=(EditText) findViewById(R.id.ettitle1);
        etday=(EditText) findViewById(R.id.etday1);
        btnupdate=(Button) findViewById(R.id.btnupdate1);
        etdate.setText(ddate);
        ettitle.setText(dtitle);
        etday.setText(dday);
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title=ettitle.getText().toString();
                date=etdate.getText().toString();
                day=etday.getText().toString();
                if(title.equals("") || date.equals("") || day.equals(""))
                {
                    Toast.makeText(editCalendarEvent.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }else
                {
                   ProgressDialog progressdialog = new ProgressDialog(editCalendarEvent.this);
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
                        Toast.makeText(editCalendarEvent.this, ""+response, Toast.LENGTH_SHORT).show();
                        if(response.equals("success")){
                            Intent intent = new Intent(editCalendarEvent.this, MainActivitycal.class);
                            intent.putExtra("refreshbit","Y");
                            startActivity(intent);
                            finish();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(editCalendarEvent.this, ""+error, Toast.LENGTH_SHORT).show();
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
                params.put("dtitle",dtitle);
                params.put("ddate",ddate);
                return params;
            };

        };
        int socketTimeOut = 50000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
