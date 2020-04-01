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
import java.util.Objects;

public class FriendSearchAdapter extends ArrayAdapter<User>
{
    private Context mContext;
    private int mResource;

    FriendSearchAdapter(@NonNull Context context, int resource,
                       ArrayList<User> objects)
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
        User user = new User();
        user.setUserName(Objects.requireNonNull(getItem(position)).
                getUserName());
        user.setUserEmail(Objects.requireNonNull(getItem(position)).
                getUserEmail());
        user.setUserIntro(Objects.requireNonNull(getItem(position)).
                getUserIntro());

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        //textViews
        TextView usernameView, emailView, introView;
        usernameView = convertView.findViewById(R.id.friendUserName);
        emailView = convertView.findViewById(R.id.friendEmail);
        introView = convertView.findViewById(R.id.friendIntro);

        //set controls in view
        usernameView.setText(user.getUserName());
        emailView.setText(user.getUserEmail());
        introView.setText(user.getUserIntro());

        return convertView;

    }
}
