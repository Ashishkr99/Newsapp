package com.example.allnews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.viewHolder> {
    private ArrayList<Articles> articlesArrayList;
    private Context context;

    public NewsAdapter(ArrayList<Articles> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
        return new NewsAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.viewHolder holder, int position) {
        Articles articles = articlesArrayList.get(position);
        holder.titleTextView.setText(articles.getTitle());
        holder.subTitleTextView.setText(articles.getDescription());
        Picasso.get().load(articles.getUrlToImage()).into(holder.newsImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,NewsDetailActivity.class);
                intent.putExtra("title",articles.getTitle());
                intent.putExtra("content",articles.getContent());
                intent.putExtra("desc",articles.getDescription());
                intent.putExtra("url",articles.getUrl());
                intent.putExtra("image",articles.getUrlToImage());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        private ImageView newsImage;
        private TextView titleTextView, subTitleTextView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            newsImage = itemView.findViewById(R.id.id_news_image);
            titleTextView = itemView.findViewById(R.id.id_news_heading);
            subTitleTextView = itemView.findViewById(R.id.id_news_content);
        }
    }
}
