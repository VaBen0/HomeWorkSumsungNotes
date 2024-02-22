package com.example.homework;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.homework.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    Adapter adapter;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.notes_view);

        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.pink_main));
        getWindow().setNavigationBarColor(ContextCompat.getColor(MainActivity.this, R.color.white1));

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        if(sPref.getBoolean("Notes", false)){
            String titles = sPref.getString("Titles", "");
            String texts = sPref.getString("Texts", "");
            String bm = sPref.getString("Paint", "");
            String types = sPref.getString("Type", "");

            String[] titlesArr = titles.split("\uD83D\uDD70");
            String[] textsArr = texts.split("\uD83D\uDD70");
            String[] bmArr = bm.split("\uD83D\uDD70");
            String[] typeArr = types.split("\uD83D\uDD70");

            for(int i = 0; i < titlesArr.length; i++){
                System.out.println("pol");
                if(typeArr[i].equals("Paint")){
                    byte[] imageAsBytes = Base64.decode(bmArr[i].getBytes(), Base64.DEFAULT);
                    Notes.addNote(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length), titlesArr[i]);
                }
                else{
                    Notes.addNote(titlesArr[i], textsArr[i]);
                }
            }
        }

        adapter = new Adapter(Notes.getArray(), MainActivity.this, rvContacts);

        rvContacts.setAdapter(adapter);

        rvContacts.setLayoutManager(new LinearLayoutManager(this));

        binding.createBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.buttons.setVisibility(View.VISIBLE);
                binding.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, CreatePaintNote.class);
                        startActivity(intent);
                        binding.buttons.setVisibility(View.GONE);
                    }
                });
                binding.button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, CreateTextNote.class);
                        startActivity(intent);
                        binding.buttons.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.notifyDataSetChanged();
    }
}
