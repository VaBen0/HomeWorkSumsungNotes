package com.example.homework;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Notes {
    private String title, text, type;
    private Bitmap bitmap;
    private static ArrayList<Notes> NotesArray = new ArrayList<Notes>();

    public Notes(Bitmap bitmap, String title){
        this.title = title;
        this.bitmap = bitmap;
        this.type = "Paint";
    }

    public Notes(String title, String text){
        this.text = text;
        this.title = title;
        this.type = "Text";
    }


    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }

    public static void addNote(String title, String text) {
        NotesArray.add(new Notes(title, text));
    }

    public static void addNote(Bitmap bmp, String text) {
        NotesArray.add(new Notes(bmp, text));
    }

    public static ArrayList<Notes> getArray(){
        return NotesArray;
    }

    public static Notes getNoteFromArray(int i){
        return NotesArray.get(i);
    }

    public static void changeNote(int i, Notes note){
        NotesArray.set(i, note);
    }

    public static void deleteNote(int i){NotesArray.remove(i);}
}
