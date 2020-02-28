package com.xoi.smvitm.home.student;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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
import android.widget.ImageButton;
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
import com.mikhaellopez.circularimageview.CircularImageView;
import com.xoi.smvitm.R;
import com.xoi.smvitm.auth.facultyLogin;
import com.xoi.smvitm.auth.facultyRegister;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class feedPostActivity extends AppCompatActivity {

    Button postBut;
    ImageButton imgUpBut;
    ImageView backBut, imgSelect;
    EditText postTxt, titleTxt;
    int IMG_REQUEST = 1;
    Bitmap bitmap;
    int select = 0;
    String url = "http://smvitmapp.xtoinfinity.tech/php/home/feedPost.php";
    String id, title, desc, img, pName, bName;
    SharedPreferences sp;
    ConstraintLayout postLayout, loadLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_post);

        postBut = (Button) findViewById(R.id.postBut);
        backBut = (ImageView) findViewById(R.id.backBut);
        imgUpBut = (ImageButton) findViewById(R.id.imgUpBut);
        imgSelect = (ImageView) findViewById(R.id.selectImg);
        postTxt = (EditText) findViewById(R.id.postTxt);
        titleTxt = (EditText) findViewById(R.id.titleTxt);
        loadLayout = (ConstraintLayout)findViewById(R.id.loadLayout);
        postLayout = (ConstraintLayout)findViewById(R.id.postLayout);

        sp = this.getSharedPreferences("com.xoi.smvitm",MODE_PRIVATE);

        if(sp.contains("usn")){
            id = sp.getString("usn","");
        }else{
            id = sp.getString("fid","");
        }

        backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imgUpBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setImage();
            }
        });

        postBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                desc = postTxt.getText().toString();
                title = titleTxt.getText().toString();
                pName = "";
                bName = "";
                if (title.equals("")) {
                    Toast.makeText(feedPostActivity.this, "Please enter the title", Toast.LENGTH_SHORT).show();
                } else {
                    feedPost();
                }
            }
        });
    }


    public void setImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
                bitmap = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                imgSelect.setImageBitmap(bitmap);
                //Glide.with(this).load(path).into(profileImg);
                select = 1;
            } catch (IOException e) {
                select = 0;
            }
        }
    }

    public String imgString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

    public void feedPost() {
        postLayout.setVisibility(View.GONE);
        loadLayout.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(feedPostActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("desc", desc);
                params.put("title", title);
                params.put("pName", pName);
                params.put("bName", bName);
                params.put("id", id);
                if (select == 1) {
                    params.put("img", imgString(bitmap));
                } else {
                    params.put("img", "no");
                }
                return params;
            }

            ;

        };
        int socketTimeOut = 50000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
