package com.example.homework;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.RangeSlider;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class CreatePaintNote extends AppCompatActivity {

    private DrawView paint;
    private Button save;
    private ImageView undo;
    private EditText title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_paint_note);

        paint = findViewById(R.id.drawView);
        undo = findViewById(R.id.undo_btn);
        save = findViewById(R.id.saveBut);
        title = findViewById(R.id.noteTitle);

        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paint.undo();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!title.getText().toString().isEmpty()) {
                    Bitmap bmp = paint.save();
                    Notes.addNote(bmp, title.getText().toString());

                    StringBuilder titles = new StringBuilder();
                    StringBuilder texts = new StringBuilder();
                    StringBuilder types = new StringBuilder();
                    StringBuilder bm = new StringBuilder();

                    for (Notes lol : Notes.getArray()) {
                        if (lol.getType().equals("Paint")) {
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

                        } else {
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
            }
        });

        ViewTreeObserver vto = paint.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                paint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = paint.getMeasuredWidth();
                int height = paint.getMeasuredHeight();
                paint.init(height, width);
            }
        });
    }
}