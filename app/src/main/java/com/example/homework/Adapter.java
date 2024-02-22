package com.example.homework;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    private List<Notes> mContacts;
    private Context context;
    RecyclerView mRecyclerView;
    public Adapter(List<Notes> contacts, Context mContext, RecyclerView recyclerView) {
        mContacts = contacts;
        context = mContext;
        mRecyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.note, parent, false);
        /*contactView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChangeNoteActivity.class);
                intent.putExtra("index", mRecyclerView.getChildLayoutPosition(contactView));
                context.startActivity(intent);
            }
        });*/
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notes contact = mContacts.get(position);
        int l = position;
        View back = holder.back;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contact.getType().equals("Paint")){
                    Intent intent = new Intent(context, LookAtPaint.class);
                    intent.putExtra("index", l);
                    context.startActivity(intent);
                }else{
                    Intent intent = new Intent(context, ChangeNoteActivity.class);
                    intent.putExtra("index", l);
                    context.startActivity(intent);
                }
            }
        });
        TextView textView = holder.nameTextView;
        textView.setText(contact.getTitle());
        TextView text = holder.textNote;
        if(contact.getType().equals("Paint")){
            text.setText("Изображение");
        }else{
            text.setText(contact.getText());
        }

    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView nameTextView;
            public TextView textNote;
            public View back;

            public ViewHolder(View itemView) {
                super(itemView);
                back = itemView.findViewById(R.id.view);
                textNote = itemView.findViewById(R.id.text_note);
                nameTextView = (TextView) itemView.findViewById(R.id.title_note);
            }
        }

}
