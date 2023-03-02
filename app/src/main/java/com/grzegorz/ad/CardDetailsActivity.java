package com.grzegorz.ad;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CardDetailsActivity extends AppCompatActivity {

    private static final String TAG = CardDetailsActivity.class.getName();
    public final static String CARD_KEY = "cardId";
    public final static String CARD_DESCRIPTION_KEY = "card_description_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);

        Intent intent = getIntent();
        String cardName = intent.getStringExtra(CARD_KEY);

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.magicthegathering.io/v1/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        MagicService _service = retrofit.create(MagicService.class);
        Call<CardDetails> speciesDetailsCall = _service.cardDetails(cardName);
        speciesDetailsCall.enqueue(new Callback<CardDetails>() {
            @Override
            public void onResponse(Call<CardDetails> call, Response<CardDetails> response) {
                if (response.isSuccessful()) {
                    CardDetails cardDetails = response.body();
                    TextView cardDetailName = findViewById(R.id.card_detail_name);
                    TextView cardDetailDescription = findViewById(R.id.card_detail_description);
                    if(cardDetails.name!=null&&cardDetails.text!=null) {
                        cardDetailName.setText(cardDetails.name);
                        cardDetailDescription.setText(cardDetails.text);
                    }
                } else {
                    Log.e(TAG, response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<CardDetails> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
            }
        });
    }
}