package com.example.homework;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.homework.databinding.ActivityChangeNoteBinding;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

public class ChangeNoteActivity extends AppCompatActivity {

    ActivityChangeNoteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChangeNoteBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(ChangeNoteActivity.this,R.color.pink_main));
        getWindow().setNavigationBarColor(ContextCompat.getColor(ChangeNoteActivity.this, R.color.pink));

        int i = Objects.requireNonNull(getIntent().getExtras()).getInt("index");
        Notes note = Notes.getNoteFromArray(i);
        String title = note.getTitle();
        String text = note.getText();

        binding.noteText.setText(text);
        binding.noteTitle.setText(title);

        binding.saveBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notes.changeNote(i, new Notes(binding.noteTitle.getText().toString(), binding.noteText.getText().toString()));

                StringBuilder titles = new StringBuilder();
                StringBuilder texts = new StringBuilder();
                StringBuilder types = new StringBuilder();
                StringBuilder bm = new StringBuilder();
                for(Notes lol : Notes.getArray()){
                    if(lol.getType().equals("Paint")){
                        String s = lol.getTitle() + "\uD83D\uDD70";
                        titles.append(s);
                        String k = "Изображение" + "\uD83D\uDD70";
                        texts.append(k);
                        String j = lol.getType() + "\uD83D\uDD70";
                        types.append(j);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        lol.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
                        byte[] b = baos.toByteArray();

                        String encoded = Base64.encodeToString(b, Base64.DEFAULT) + "\uD83D\uDD70";
                        bm.append(encoded);

                    }else{
                        String s = lol.getTitle() + "\uD83D\uDD70";
                        titles.append(s);
                        String k = lol.getText() + "\uD83D\uDD70";
                        texts.append(k);
                        String j = lol.getType() + "\uD83D\uDD70";
                        types.append(j);
                        String encoded = "...\uD83D\uDD70";
                        bm.append(encoded);
                    }

                }
                System.out.println(titles);
                SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putBoolean("Notes", true);
                ed.putString("Titles", titles.toString());
                ed.putString("Texts", texts.toString());
                ed.putString("Type", types.toString());
                ed.putString("Paint", bm.toString());
                ed.apply();

                finish();
            }
        });
        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notes.deleteNote(i);

                StringBuilder titles = new StringBuilder();
                StringBuilder texts = new StringBuilder();
                StringBuilder types = new StringBuilder();
                StringBuilder bm = new StringBuilder();
                for(Notes lol : Notes.getArray()){
                    if(lol.getType().equals("Paint")){
                        String s = lol.getTitle() + "\uD83D\uDD70";
                        titles.append(s);
                        String k = "Изображение" + "\uD83D\uDD70";
                        texts.append(k);
                        String j = lol.getType() + "\uD83D\uDD70";
                        types.append(j);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        lol.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] b = baos.toByteArray();

                        String encoded = Base64.encodeToString(b, Base64.DEFAULT) + "\uD83D\uDD70";
                        bm.append(encoded);

                    }else{
                        String s = lol.getTitle() + "\uD83D\uDD70";
                        titles.append(s);
                        String k = lol.getText() + "\uD83D\uDD70";
                        texts.append(k);
                        String j = lol.getType() + "\uD83D\uDD70";
                        types.append(j);
                        String encoded = "...\uD83D\uDD70";
                        bm.append(encoded);
                    }

                }
                System.out.println(titles);
                SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putBoolean("Notes", true);
                ed.putString("Titles", titles.toString());
                ed.putString("Texts", texts.toString());
                ed.putString("Type", types.toString());
                ed.putString("Paint", bm.toString());
                ed.apply();

                finish();
            }
        });
    }
}