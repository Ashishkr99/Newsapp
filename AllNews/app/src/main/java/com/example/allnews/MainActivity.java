package com.example.allnews;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.CategoryOnClickInterface {

    // API key - 723c04dcc11c447aa06420bf31c44e08


    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryModal> categoryModalArrayList;
    private RecyclerView newsRecyclerView, categoryRecyclerView;
    private ProgressBar loadingPB;
    private NewsAdapter newsAdapter;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsRecyclerView = findViewById(R.id.id_news);
        categoryRecyclerView = findViewById(R.id.id_category);
        loadingPB = findViewById(R.id.id_loading);
        articlesArrayList = new ArrayList<>();
        categoryModalArrayList = new ArrayList<>();
        newsAdapter = new NewsAdapter(articlesArrayList, this);
        categoryAdapter = new CategoryAdapter(categoryModalArrayList, this, this::onCategoryClick);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsRecyclerView.setAdapter(newsAdapter);
        categoryRecyclerView.setAdapter(categoryAdapter);

        getCategories();
        getNews("All");
        newsAdapter.notifyDataSetChanged();

    }

    private void getCategories() {
        categoryModalArrayList.add(new CategoryModal("All","https://images.unsplash.com/photo-1566378246598-5b11a0d486cc?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8bmV3c3BhcGVyfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        categoryModalArrayList.add(new CategoryModal("Modi ji","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQdIEO5_UlfAj9jDVDsRAZ0JPI2LwweYb1beg&usqp=CAU"));
        categoryModalArrayList.add(new CategoryModal("Business","https://images.unsplash.com/photo-1600880292203-757bb62b4baf?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MjF8fGZpbmFuY2V8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60"));
        categoryModalArrayList.add(new CategoryModal("Technology","https://images.unsplash.com/photo-1518770660439-4636190af475?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8dGVjaG5vbG9neXxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
        categoryModalArrayList.add(new CategoryModal("Health","https://plus.unsplash.com/premium_photo-1658506951041-c407101156b6?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MjV8fGhlYWx0aHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
        categoryModalArrayList.add(new CategoryModal("Sports","https://plus.unsplash.com/premium_photo-1658506638118-524a66dc5cee?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8Zm9vdGJhbGx8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60"));
        categoryModalArrayList.add(new CategoryModal("Science","https://images.unsplash.com/photo-1628595351029-c2bf17511435?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTJ8fHNjaWVuY2V8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60"));
        categoryModalArrayList.add(new CategoryModal("Entertainment","https://images.unsplash.com/photo-1533237122199-6c24ffac01ea?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NzF8fGVudGVydGFpbm1lbnR8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60"));

        categoryAdapter.notifyDataSetChanged();
    }

    private void getNews(String category){
        loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
//        Log.i(TAG, "getNews: "+category);
        String categoryModi = "https://newsapi.org/v2/top-headlines?country=in&q=modi&apiKey=723c04dcc11c447aa06420bf31c44e08";
        String categoryURL = "https://newsapi.org/v2/top-headlines?country=in&category="+category+"&apiKey=723c04dcc11c447aa06420bf31c44e08";
        String BASE_URL = "https://newsapi.org/";
        String url = "https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=723c04dcc11c447aa06420bf31c44e08";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModal> call;
        if(category.equals("All")) {
            call = retrofitAPI.getAllNews(url);
        }
        else if(category.equals("Modi ji")) {
            call = retrofitAPI.getNewsByCategory(categoryModi);
        }
            else
         {
            call = retrofitAPI.getNewsByCategory(categoryURL);
        }
        call.enqueue(new Callback<NewsModal>() {
            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
                NewsModal newsModal = response.body();
                ArrayList<Articles> articles = newsModal.getArticles();
                loadingPB.setVisibility(View.GONE);
                for(int i=0;i<articles.size();i++)
                {
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(),articles.get(i).getDescription(),articles.get(i).getUrlToImage(),
                            articles.get(i).getUrl(),articles.get(i).getContent()));
                }
                newsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Failed to fetch news",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onCategoryClick(int position) {
        String category = categoryModalArrayList.get(position).getCategory();
        getNews(category);
    }
}