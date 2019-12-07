package com.xoi.smvitm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class studRegisterActivity extends AppCompatActivity {

    EditText nameTxt, passTxt, conPassTxt, usnTxt, emailTxt;
    MaterialSpinner semSpin, secSpin, brSpin;
    String semAr[] = {"2","4","6","8"};
    String secAr[] = {"A","B","C"};
    String brAr[] = {"Computer Science","Electronics","Mechanical","Civil"};
    Button regBut;
    TextView proPicTxt;
    String name, pass, conpass, usn, email, sem, sec, br;
    String url ="http://smvitmapp.xtoinfinity.tech/php/register.php";
    CircularImageView profileImg;
    int IMG_REQUEST = 1;
    Bitmap bitmap;
    String chckMail;
    int select = 0;
    Animation _translateAnimation;
    ImageView bckImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud_register);

        nameTxt = (EditText)findViewById(R.id.nameTxt);
        passTxt = (EditText)findViewById(R.id.passTxt);
        conPassTxt = (EditText)findViewById(R.id.conPassTxt);
        usnTxt = (EditText)findViewById(R.id.usnTxt);
        emailTxt = (EditText)findViewById(R.id.emailTxt);
        semSpin = (MaterialSpinner)findViewById(R.id.semSpin);
        secSpin = (MaterialSpinner)findViewById(R.id.secSpin);
        brSpin = (MaterialSpinner)findViewById(R.id.brSpin);
        regBut = (Button)findViewById(R.id.regBut);
        proPicTxt = (TextView) findViewById(R.id.proPicTxt);
        profileImg = (CircularImageView)findViewById(R.id.profileImg);
        bckImg = (ImageView)findViewById(R.id.bckImg);

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

        semSpin.setItems(semAr);

        secSpin.setItems(secAr);

        brSpin.setItems(brAr);


        regBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                br = Integer.toString(brSpin.getSelectedIndex()+1);
                sem = semAr[semSpin.getSelectedIndex()];
                sec = secAr[secSpin.getSelectedIndex()];
                name = nameTxt.getText().toString().trim();
                pass = passTxt.getText().toString();
                conpass = conPassTxt.getText().toString();
                usn = usnTxt.getText().toString().trim().toUpperCase();
                email = emailTxt.getText().toString().trim();

                if(name.equals("")|| pass.equals("")||usn.equals("")||email.equals("")){
                    Toast.makeText(studRegisterActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        chckMail = email.substring(email.indexOf("@") + 1, email.indexOf(".in"));
                        if(chckMail.equals("sode-edu")) {
                            if (pass.equals(conpass)) {
                                register();
                            } else {
                                Toast.makeText(studRegisterActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(studRegisterActivity.this, "Please enter your sode-edu email", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                        Toast.makeText(studRegisterActivity.this, "Please enter your sode-edu email", Toast.LENGTH_SHORT).show();
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
                bitmap = scaleDown(bitmap, 10000, true);
                profileImg.setImageBitmap(bitmap);
                select = 1;
            } catch (IOException e) {
                select = 0;
            }
        }
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
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
                            Toast.makeText(studRegisterActivity.this, "You have already registered with this USN.", Toast.LENGTH_LONG).show();
                        }
                        else if(finResponse.equals("1452")){
                            Toast.makeText(studRegisterActivity.this, "Please check your branch, sem, section.", Toast.LENGTH_LONG).show();
                        }
                        else if(response.equals("success;")){
                            Toast.makeText(studRegisterActivity.this, "Registered Successfully, please login to continure.", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(studRegisterActivity.this, loginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(studRegisterActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(studRegisterActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("usn",usn);
                params.put("pass",pass);
                params.put("name",name);
                params.put("email",email);
                params.put("br",br);
                params.put("sem",sem);
                params.put("sec",sec);
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
}
