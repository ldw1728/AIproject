package com.AIProject.howstoday;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class UserBoardAdapter extends RecyclerView.Adapter<UserBoardAdapter.Viewholder> {

    private ArrayList<BoardItem> items;
    private UserBoardActivity activity;

    public UserBoardAdapter(UserBoardActivity activity){
        this.activity = activity;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_cardview,null);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
        BoardItem bi = items.get(position);

        holder.iv_userIMG.setImageBitmap(ImgHandler.stringToBitmap(bi.getUserIMG()));

        holder.ratingBar_score.setIsIndicator(true);
        holder.ratingBar_score.setRating(Float.valueOf(bi.getScore()));

        holder.card_usercard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AcceptIMGActivity.goToOtherActivity(activity, DetailActivity.class, new HashMap(){{put("item", items.get(position));} });
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<BoardItem> items){
        this.items = items;
    }

    public class Viewholder extends RecyclerView.ViewHolder {

       // private Bitmap userIMG;
        private CardView card_usercard;
        private ImageView iv_userIMG;
        private RatingBar ratingBar_score;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            card_usercard = itemView.findViewById(R.id.card_usercard);
            iv_userIMG = itemView.findViewById(R.id.iv_userIMG);
            ratingBar_score = itemView.findViewById(R.id.ratingBar_score);
        }
    }
}
