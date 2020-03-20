package com.xoi.smvitm.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.xoi.smvitm.main.faculty.facMainActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class editFacProfActivity extends AppCompatActivity {

    TextView propictext;
    CircularImageView profileImg;
    EditText nameTxt,mobileTxt,passTxt, conPassTxt;
    MaterialSpinner brSpin;
    Button updatebtn;
    String url ="http://smvitmapp.xtoinfinity.tech/php/updateFacProfile.php";
    SharedPreferences sharedPreferences;
    String name,fid,mobile,pass,conPass,branch, pic;
    String brAr[] = {"Computer Science","Electronics","Mechanical","Civil"};
    int IMG_REQUEST = 1;
    String chckMail;
    Bitmap bitmap;
    int select=0;
    String usn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_fac_prof);

        nameTxt = (EditText) findViewById(R.id.nameTxt);
        mobileTxt = (EditText) findViewById(R.id.mobileTxt);
        passTxt = (EditText) findViewById(R.id.passTxt);
        conPassTxt = (EditText) findViewById(R.id.conPassTxt);
        brSpin = (MaterialSpinner) findViewById(R.id.brSpin);
        updatebtn = (Button) findViewById(R.id.regBut);
        propictext = (TextView) findViewById(R.id.proPicTxt);
        profileImg = (CircularImageView) findViewById(R.id.profileImg);

        sharedPreferences = this.getSharedPreferences("com.xoi.smvitm",MODE_PRIVATE);

        fid=sharedPreferences.getString("fid","");
        name=sharedPreferences.getString("name","");
        mobile=sharedPreferences.getString("mobile","");
        branch=sharedPreferences.getString("branch","");
        pic=sharedPreferences.getString("profilePic","");
        pass=sharedPreferences.getString("pass","");
        Glide.with(this).load(pic).into(profileImg);
        nameTxt.setText(name);
        mobileTxt.setText(mobile);
        passTxt.setText(pass);
        conPassTxt.setText(pass);
        brSpin.setItems(brAr);

        setBranch();

        brSpin.setSelectedIndex(Arrays.asList(brAr).indexOf(branch));

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImage();
            }
        });

        propictext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImage();
            }
        });
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                branch = Integer.toString(brSpin.getSelectedIndex()+1);
                name = nameTxt.getText().toString().trim();
                mobile = mobileTxt.getText().toString().trim();
                pass = passTxt.getText().toString().trim();
                conPass = conPassTxt.getText().toString().trim();

                if(name.equals("")|| mobile.equals("")|| pass.equals("")|| conPass.equals("")){
                    Toast.makeText(editFacProfActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(pass.equals(conPass)){
                        update();
                    }else{
                        Toast.makeText(editFacProfActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void setBranch(){
        if(branch.equals("1")){
            branch = ("Computer Science");
        }
        else if(branch.equals("2")){
            branch = ("Electronics");
        }
        else if(branch.equals("3")){
            branch = ("Mechanical");
        }
        else if(branch.equals("4")){
            branch = ("Civil");
        }
        else{
            branch = ("Basic Science");
        }
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
                profileImg.setImageBitmap(bitmap);
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
                        Toast.makeText(editFacProfActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                        if(response.equals("success;")){
                            Intent intent = new Intent(editFacProfActivity.this, facMainActivity.class);
                            startActivity(intent);
                            AsyncTask.execute(new Runnable() {
                                @Override
                                public void run() {
                                    Glide.get(editFacProfActivity.this).clearDiskCache();
                                }
                            });
                            finish();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(editFacProfActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("fid",fid);
                params.put("name",name);
                params.put("mobile",mobile);
                params.put("br",branch);
                params.put("pass",pass);
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
        Intent intent = new Intent(editFacProfActivity.this, facMainActivity.class);
        startActivity(intent);
        finish();
    }
}