package com.xoi.smvitm.profile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.xoi.smvitm.R;
import com.xoi.smvitm.main.student.studMainActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class editStudProfActivity extends AppCompatActivity {
    TextView updatepropictext;
    CircularImageView updateprofileImg;
    EditText updatenametext,updateemailtext;
    MaterialSpinner updatesemSpin, updatesecSpin, updatebrSpin;
    Button updatebtn;
    String url ="http://smvitmapp.xtoinfinity.tech/php/updatestudprofile.php";
    SharedPreferences sharedPreferences;
    String username,useremail,usersem,usersec,userbr,userpic,userusn;
    String semAr[] = {"2","4","6","8"};
    String secAr[] = {"A","B","C"};
    String brAr[] = {"Computer Science","Electronics","Mechanical","Civil"};
    int IMG_REQUEST = 1;
    String chckMail;
    Bitmap bitmap;
    int select=0;
    String usn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stud_prof);

        updatenametext = (EditText) findViewById(R.id.updatenameTxt);
        updateemailtext = (EditText) findViewById(R.id.updateemailTxt);
        updatesemSpin = (MaterialSpinner) findViewById(R.id.updatesemSpin);
        updatesecSpin = (MaterialSpinner) findViewById(R.id.updatesecSpin);
        updatebrSpin = (MaterialSpinner) findViewById(R.id.updatebrSpin);
        updatebtn = (Button) findViewById(R.id.updateprofileBut);
        updatepropictext = (TextView) findViewById(R.id.updateproPicTxt);
        updateprofileImg = (CircularImageView) findViewById(R.id.updateprofileImg);

        sharedPreferences = this.getSharedPreferences("com.xoi.smvitm",MODE_PRIVATE);

        userusn=sharedPreferences.getString("usn","");
        username=sharedPreferences.getString("Username","");
        useremail=sharedPreferences.getString("Useremail","");
        usersem=sharedPreferences.getString("Usersem","");
        usersec=sharedPreferences.getString("Usersec","");
        userbr=sharedPreferences.getString("Userbranch","");
        userpic=sharedPreferences.getString("Userprofilepic","");
        Glide.with(this).load(userpic).into(updateprofileImg);
        updatenametext.setText(username);
        updateemailtext.setText(useremail);

        updatesemSpin.setItems(semAr);
        updatesecSpin.setItems(secAr);
        updatebrSpin.setItems(brAr);

        updatesemSpin.setSelectedIndex(Arrays.asList(semAr).indexOf(usersem));
        updatesecSpin.setSelectedIndex(Arrays.asList(secAr).indexOf(usersec));
        updatebrSpin.setSelectedIndex(Integer.parseInt(userbr)-1);

        updatepropictext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImage();
            }
        });

        updateprofileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImage();
            }
        });
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userbr = Integer.toString(updatebrSpin.getSelectedIndex()+1);
                usersem = semAr[updatesemSpin.getSelectedIndex()];
                usersec = secAr[updatesecSpin.getSelectedIndex()];
                username = updatenametext.getText().toString().trim();
                usn=userusn.toUpperCase();
                useremail = updateemailtext.getText().toString().trim();

                if(username.equals("")|| useremail.equals("")){
                    Toast.makeText(editStudProfActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        chckMail = useremail.substring(useremail.indexOf("@") + 1, useremail.indexOf(".in"));
                        if(chckMail.equals("sode-edu")) {

                            update();

                        }
                        else{
                            Toast.makeText(editStudProfActivity.this, "Please enter your sode-edu email", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                        Toast.makeText(editStudProfActivity.this, "Please enter your sode-edu email", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void setImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMG_REQUEST && resultCode==RESULT_OK && data!=null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
                bitmap = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                updateprofileImg.setImageBitmap(bitmap);
                //Glide.with(this).load(path).into(profileImg);
                select = 1;
            } catch (IOException e) {
                select = 0;
            }
        }
    }

    public String imgString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }

    public void update(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(editStudProfActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                        if(response.equals("success;")){
                            Intent intent = new Intent(editStudProfActivity.this, studMainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(editStudProfActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("usn",usn);
                params.put("name",username);
                params.put("email",useremail);
                params.put("br",userbr);
                params.put("sem",usersem);
                params.put("sec",usersec);
                if(select == 1) {
                    params.put("proPic", imgString(bitmap));
                }else{
                    params.put("proPic", "no");
                }
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
    public void onBackPressed() {
        Intent intent = new Intent(editStudProfActivity.this, studMainActivity.class);
        startActivity(intent);
        finish();
    }
}