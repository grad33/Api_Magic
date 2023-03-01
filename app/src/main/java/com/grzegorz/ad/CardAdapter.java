package com.grzegorz.ad;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grzegorz.ad.Card;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardViewHolder> {

 public interface OnCardClickListener {
  void onCardClick(Card card);
 }

 private final List<Card> _cardList;
 private final OnCardClickListener _onCardClickListener;

 public CardAdapter(List<Card> cardList, OnCardClickListener onCardClickListener) {

  _cardList = cardList;
  _onCardClickListener = onCardClickListener;
 }

 @NonNull
 @Override
 public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row, parent, false);
  CardViewHolder cardViewHolder = new CardViewHolder(view, _onCardClickListener);
  return cardViewHolder;
 }

 @Override
 public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
  holder.bind(_cardList.get(position));
 }

 @Override
 public int getItemCount() {
  return _cardList.size();
 }
}