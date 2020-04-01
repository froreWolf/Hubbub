package com.apathyforge.hubbub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

class MessageListAdapter extends ArrayAdapter<ChatMessage>
{
    private  Context mContext;
    private int mResource;

    MessageListAdapter(@NonNull Context context, int resource,
                       ArrayList<ChatMessage> objects)
    {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,
                        @NonNull ViewGroup parent)
    {
        ChatMessage CM = new ChatMessage();
        CM.setUserName(Objects.requireNonNull(getItem(position)).
                getUserName());
        CM.setMessage(Objects.requireNonNull(getItem(position)).
                getMessage());
        CM.setMessageTime(Objects.requireNonNull(getItem(position)).
                getMessageTime());

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        //textViews
        TextView messageView, userView, dateView;
        messageView = convertView.findViewById(R.id.messageContent);
        userView = convertView.findViewById(R.id.messageUser);
        dateView = convertView.findViewById(R.id.messageDate);

        Date date = new Date(CM.getMessageTime() * 1000);

        messageView.setText(CM.getMessage());
        userView.setText(CM.getUserName());
        dateView.setText(date.toString());

        return convertView;
    }
}
