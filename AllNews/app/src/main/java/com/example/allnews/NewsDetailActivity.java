package com.example.allnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {

    String title,subDesc,content,url,imageUrl;
    TextView titleText,subDecText,contentText;
    ImageView imageView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        subDesc = getIntent().getStringExtra("desc");
        url = getIntent().getStringExtra("url");
        imageUrl = getIntent().getStringExtra("image");

        titleText = findViewById(R.id.title);
        subDecText = findViewById(R.id.subDesc);
        contentText = findViewById(R.id.content);
        imageView = findViewById(R.id.newsImage);
        button = findViewById(R.id.button);

        titleText.setText(title);
        subDecText.setText(subDesc);
        contentText.setText(content);
        Picasso.get().load(imageUrl).into(imageView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}