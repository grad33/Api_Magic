package com.grzegorz.ad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private Button getAllCardsButton;
    private Button getCardByIdButton;
    private TextView cardNameTextView;
    private ImageView cardImageView;
    private MagicService _service;
    private static final String TAG = MainActivity.class.getName();
    private int _offset = 0;
    private static final int LIMIT = 20;
    private List<Card> _species = new ArrayList<Card>();
    private CardAdapter _adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.magicthegathering.io/v1/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        _service=retrofit.create(MagicService.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        _adapter = new CardAdapter(_species, new CardAdapter.OnSpeciesClickListener() {
            @Override
            public void onSpeciesClick(Card species) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, CardDetailsActivity.class);
                intent.putExtra(CardDetailsActivity.NAME_KEY, species.name);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(_adapter);

        Button loadMoreButton = findViewById(R.id.load_more_button);
        loadMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadChunk(_offset, LIMIT);
            }
        });

        loadChunk(_offset, LIMIT);
    }

    private void loadChunk(int offset, int limit) {
        Call<CardChunk> call = _service.listSpecies(offset, limit);
        call.enqueue(new Callback<CardChunk>() {
            @Override
            public void onResponse(Call<CardChunk> call, Response<CardChunk> response) {
                if (response.isSuccessful()) {
                    List<Card> cards = response.body().results;
                    _species.addAll(cards);
                    for (Card card : cards) {
                        Log.i(TAG, card.toString());
                    }
                    _adapter.notifyItemRangeInserted(offset, LIMIT);
                    _offset += LIMIT;
                } else {
                    Log.e(TAG, response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<CardChunk> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
            }
        });
    }

}

