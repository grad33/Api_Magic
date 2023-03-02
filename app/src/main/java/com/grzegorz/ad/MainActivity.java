package com.grzegorz.ad;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grzegorz.ad.MagicService;
import com.grzegorz.ad.Card;
import com.grzegorz.ad.CardChunk;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private CardAdapter _adapter;
    private MagicService _service;
    private RecyclerView mRecyclerView;
    private List<Card> _cards = new ArrayList<Card>();

    private int mTotalItemCount;
    private int mLastVisibleItemPosition;
    private boolean mIsLoading = false;
    private int _page = 0;
    private static final int pageSize = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.magicthegathering.io/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        _service = retrofit.create(MagicService.class);
        mRecyclerView=findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        _adapter = new CardAdapter(_cards, new CardAdapter.OnCardClickListener(){
            @Override
            public void onCardClick(Card card) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, CardDetailsActivity.class);
                intent.putExtra(CardDetailsActivity.CARD_KEY, card.getId());
                startActivity(intent);
            }


        });
        mRecyclerView.setAdapter(_adapter);

        Button loadMoreButton = findViewById(R.id.load_more_button);
        loadMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadChunk(_page, pageSize);
            }
        });

        loadChunk(_page, pageSize);
    }

    private void loadChunk(int page, int pageSize) {
        Call<CardChunk> call = _service.listCards(page, pageSize);
        call.enqueue(new Callback<CardChunk>() {
            @Override
            public void onResponse(Call<CardChunk> call, Response<CardChunk> response) {
                if (response.isSuccessful()) {
                    List<Card> cards = response.body().cards;
                    if (cards != null) {
                        List<Card> filteredCards = new ArrayList<>();
                        for (Card card : cards) {
                            if (card.imageUrl != null) {
                                filteredCards.add(card);
                            }
                        }
                        _cards.addAll(filteredCards);
                        for (Card card : filteredCards) {
                            Log.i(TAG, card.toString());
                        }
                        _adapter.notifyItemRangeInserted(_page, pageSize);
                        _page += pageSize;
                    }
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
