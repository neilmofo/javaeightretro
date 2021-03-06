package com.mobileforming.javaeightretro.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.mobileforming.javaeightretro.R;
import com.mobileforming.javaeightretro.model.Joke;
import com.mobileforming.javaeightretro.net.JokeRequest;
import com.mobileforming.javaeightretro.net.JokeResolver;
import com.mobileforming.javaeightretro.net.RandomJokeResolver;

public class MainActivity extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    private TextView mJokeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mJokeTextView = (TextView) findViewById(R.id.joke);

        mRequestQueue = Volley.newRequestQueue(this);

    }

    public void btnClick(View view) {
        getNewJokeOld();
    }

    private void getNewJokeOld() {
        JokeRequest request = new JokeRequest(getJokeResolver(), new Response.Listener<Joke>() {
            @Override
            public void onResponse(Joke response) {
                Log.d("joke", "success!");
                displayNewJoke(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("joke", "failure! " + error);
                displayError(error);
            }
        });

        mRequestQueue.add(request);
    }

    private void getNewJokeLambda() {
        JokeRequest request = new JokeRequest(getJokeResolver(), response -> displayNewJoke(response), error -> displayError(error));

        mRequestQueue.add(request);
    }

    private void getNewJokeLambdaReadable() {
        JokeRequest request = new JokeRequest(getJokeResolver(),
                (Joke response) -> displayNewJoke(response),
                (VolleyError error) -> displayError(error));

        mRequestQueue.add(request);
    }

    private void getNewJokeLambdaMultiline() {
        JokeRequest request = new JokeRequest(getJokeResolver(), response -> {
            Log.d("joke", "success!");
            displayNewJoke(response);
        }, error -> {
            Log.d("joke", "failure!");
            displayError(error);
        });

        mRequestQueue.add(request);
    }

    private void getNewJokeMethodRef() {
        JokeRequest request = new JokeRequest(getJokeResolver(), this::displayNewJoke, this::displayError);

        mRequestQueue.add(request);
    }

    private void displayError(VolleyError error) {
        Toast.makeText(MainActivity.this, "couldn't load the joke", Toast.LENGTH_SHORT).show();
    }

    private void displayNewJoke(Joke response) {
        mJokeTextView.setText(Html.fromHtml(response.value.joke));
    }

    private JokeResolver getJokeResolver() {
        return new RandomJokeResolver();
        //        return new BestJokeResolver();
    }
}
