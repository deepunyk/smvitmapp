package com.xoi.smvitm.home.student;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.xoi.smvitm.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class feedEditActivity extends AppCompatActivity {

    String url = "http://smvitmapp.xtoinfinity.tech/php/home/updateFeed.php";
    SharedPreferences sp;
    String id, title, desc, img, pName, bName, fid;
    ConstraintLayout postLayout, loadLayout;
    Button postBut;
    ImageButton imgUpBut;
    ImageView backBut, imgSelect;
    EditText postTxt, titleTxt;
    int IMG_REQUEST = 1;
    Bitmap bitmap;
    int select = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_edit);

        sp = this.getSharedPreferences("com.xoi.smvitm", MODE_PRIVATE);
        postBut = (Button) findViewById(R.id.postBut);
        backBut = (ImageView) findViewById(R.id.backBut);
        imgUpBut = (ImageButton) findViewById(R.id.imgUpBut);
        imgSelect = (ImageView) findViewById(R.id.selectImg);
        postTxt = (EditText) findViewById(R.id.postTxt);
        titleTxt = (EditText) findViewById(R.id.titleTxt);
        loadLayout = (ConstraintLayout) findViewById(R.id.loadLayout);
        postLayout = (ConstraintLayout) findViewById(R.id.postLayout);

        fid = getIntent().getStringExtra("fid");
        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("description");
        img = getIntent().getStringExtra("imgurl");

        if(!img.equals("")) {
            Glide.with(this)
                    .load(img)
                    .fitCenter()
                    .signature(new ObjectKey(img))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(imgSelect);
        }

        titleTxt.setText(title);
        postTxt.setText(desc);
        if (sp.contains("usn")) {
            id = sp.getString("usn", "");
        } else {
            id = sp.getString("fid", "");
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
                    Toast.makeText(feedEditActivity.this, "Please enter the title", Toast.LENGTH_SHORT).show();
                    closeKeyBoard();
                } else {
                    closeKeyBoard();
                    feedPost();
                }
            }
        });
    }

    public void feedPost() {
        postLayout.setVisibility(View.GONE);
        loadLayout.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                Glide.get(feedEditActivity.this).clearDiskCache();
                            }
                        });
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(feedEditActivity.this, "" + error, Toast.LENGTH_SHORT).show();
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
                params.put("fid", fid);
                if (select == 1) {
                    params.put("img", imgString(bitmap));
                    params.put("check", "yes");
                } else {
                    if(img.equals("")){
                        params.put("check", "no");
                        params.put("img", img);
                    }
                    else {
                        params.put("check", "old");
                        params.put("img", img);
                    }
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

    private void closeKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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
}
