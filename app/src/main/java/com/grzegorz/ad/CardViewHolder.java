package com.grzegorz.ad;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CardViewHolder extends RecyclerView.ViewHolder {

    private final TextView _cardNameTextView;
    private final CardAdapter.OnCardClickListener _onCardClickListener;
    private Card _card;

    public CardViewHolder(@NonNull View itemView, CardAdapter.OnCardClickListener _onCardClickListener) {
        super(itemView);
        _cardNameTextView = itemView.findViewById(R.id.card_name);
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
    }
}