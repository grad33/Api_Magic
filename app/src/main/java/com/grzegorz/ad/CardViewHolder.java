package com.grzegorz.ad;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class CardViewHolder extends RecyclerView.ViewHolder {

    private final TextView _cardNameTextView;
    private final TextView _cardRarityTextView;
    private final TextView _cardTextTextView;
    private final ImageView _cardImageView;

    private final CardAdapter.OnCardClickListener _onCardClickListener;
    private Card _card;

    @SuppressLint("ResourceType")
    public CardViewHolder(@NonNull View itemView, CardAdapter.OnCardClickListener _onCardClickListener) {
        super(itemView);
        _cardNameTextView = itemView.findViewById(R.id.card_name);
        _cardRarityTextView=itemView.findViewById(R.id.card_rare);
        _cardTextTextView=itemView.findViewById(R.id.card_text);
        _cardImageView=itemView.findViewById(R.id.card_img);
        this._onCardClickListener = _onCardClickListener;
        _cardNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_card != null) {
                    _onCardClickListener.onCardClick(_card);
                }
            }
        });
    }

    public void bind(Card card) {
        _card = card;
        _cardNameTextView.setText(card.name);
        _cardRarityTextView.setText(card.rarity);
        _cardTextTextView.setText(card.text);
        String imageUrl = card.imageUrl;
        if (imageUrl != null && !imageUrl.isEmpty()) {
            System.out.println(imageUrl);
            Picasso.get().load(imageUrl).into(_cardImageView);
        }
    }
}