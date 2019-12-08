package com.xoi.smvitm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

public class facultyRegister extends AppCompatActivity {

    ExtendedEditText nameTxt, passTxt, conPassTxt, fidTxt, emailTxt, mobileTxt;
    MaterialSpinner brSpin;
    String brAr[] = {"Computer Science","Electronics","Mechanical","Civil","Basic Science"};
    Button regBut;
    TextView proPicTxt, loadTxt;
    String name, pass, conpass, fid, email, br, mobile;
    String url ="http://smvitmapp.xtoinfinity.tech/php/facultyRegister.php";
    CircularImageView profileImg;
    int IMG_REQUEST = 1;
    Bitmap bitmap;
    String chckMail;
    int select = 0;
    Animation _translateAnimation;
    ImageView bckImg;
    ConstraintLayout loadLayout;
    ScrollView scroll;
    LottieAnimationView loadAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_register);

        nameTxt = (ExtendedEditText)findViewById(R.id.nameTxt);
        mobileTxt = (ExtendedEditText)findViewById(R.id.mobileTxt);
        passTxt = (ExtendedEditText)findViewById(R.id.passTxt);
        conPassTxt = (ExtendedEditText)findViewById(R.id.conPassTxt);
        fidTxt = (ExtendedEditText)findViewById(R.id.fidTxt);
        emailTxt = (ExtendedEditText)findViewById(R.id.emailTxt);
        brSpin = (MaterialSpinner)findViewById(R.id.brSpin);
        regBut = (Button)findViewById(R.id.regBut);
        proPicTxt = (TextView) findViewById(R.id.proPicTxt);
        profileImg = (CircularImageView)findViewById(R.id.profileImg);
        bckImg = (ImageView)findViewById(R.id.bckImg);

        loadAnim = (LottieAnimationView)findViewById(R.id.loadanim);
        scroll = (ScrollView)findViewById(R.id.scrollView);
        loadLayout = (ConstraintLayout)findViewById(R.id.loadLayout);
        loadTxt = (TextView)findViewById(R.id.loadtxt);

        _translateAnimation = new TranslateAnimation(TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, -100f, TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, 0f);
        _translateAnimation.setDuration(10000);
        _translateAnimation.setRepeatCount(-1);
        _translateAnimation.setRepeatMode(Animation.REVERSE);
        _translateAnimation.setInterpolator(new LinearInterpolator());
        bckImg.setAnimation(_translateAnimation);

        proPicTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImage();
            }
        });

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImage();
            }
        });

        brSpin.setItems(brAr);


        regBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                br = Integer.toString(brSpin.getSelectedIndex()+1);
                name = nameTxt.getText().toString().trim();
                pass = passTxt.getText().toString();
                conpass = conPassTxt.getText().toString();
                fid = fidTxt.getText().toString().trim().toUpperCase();
                mobile = mobileTxt.getText().toString().trim();
                email = emailTxt.getText().toString().trim();

                if(name.equals("")|| pass.equals("")||fid.equals("")||email.equals("")){
                    Toast.makeText(facultyRegister.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        chckMail = email.substring(email.indexOf("@") + 1, email.indexOf(".in"));
                        if(chckMail.equals("sode-edu")) {
                            if (pass.equals(conpass)) {
                                load();
                                register();
                            } else {
                                Toast.makeText(facultyRegister.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(facultyRegister.this, "Please enter your sode-edu email", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                        Toast.makeText(facultyRegister.this, "Please enter your sode-edu email", Toast.LENGTH_SHORT).show();
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
                profileImg.setImageBitmap(bitmap);
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
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

    public void register(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String finResponse = response.substring(0,4);
                        if(finResponse.equals("1062")){
                            Toast.makeText(facultyRegister.this, "You have already registered with this FID.", Toast.LENGTH_LONG).show();
                        }
                        else if(finResponse.equals("1452")){
                            Toast.makeText(facultyRegister.this, "Please check your branch.", Toast.LENGTH_LONG).show();
                        }
                        else if(response.equals("success;")){
                            Toast.makeText(facultyRegister.this, "Registered Successfully, please login to continure.", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(facultyRegister.this, facultyLogin.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(facultyRegister.this, ""+response, Toast.LENGTH_SHORT).show();
                        }
                        doneLoad();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(facultyRegister.this, ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("fid",fid);
                params.put("mobile",mobile);
                params.put("pass",pass);
                params.put("name",name);
                params.put("email",email);
                params.put("br",br);
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

    public void load(){
        loadTxt.setVisibility(View.VISIBLE);
        loadLayout.setVisibility(View.VISIBLE);
        loadAnim.setVisibility(View.VISIBLE);
        scroll.setVisibility(View.GONE);
    }

    public void doneLoad(){
        loadTxt.setVisibility(View.GONE);
        loadLayout.setVisibility(View.GONE);
        loadAnim.setVisibility(View.GONE);
        scroll.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(facultyRegister.this, facultyLogin.class);
        startActivity(intent);
        finish();
    }
}
