package com.example.memeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    ImageView imgView;
    AppCompatButton shareBtn, nextBtn;
    String uri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgView = findViewById(R.id.imgView);
        shareBtn = findViewById(R.id.shareBtn);
        nextBtn = findViewById(R.id.nextBtn);

        loadMeme();

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT,  uri + "\n Hey, Checkout this awesome meme I found on MemeApp!");
                startActivity(Intent.createChooser(share, "Choose where to share!"));
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMeme();
            }
        });


    }

    private void loadMeme(){

    // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = ("https://meme-api.com/gimme");

    // Request a string response from the provided URL.
        JsonObjectRequest JSONRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            uri = response.getString("url");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Glide.with(MainActivity.this).load(uri).into(imgView);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Something went wrong! ", Toast.LENGTH_SHORT).show();
            }
        });

    // Add the request to the RequestQueue.
        queue.add(JSONRequest);
    }

}