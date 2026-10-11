package com.example.hw03;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class CustomerAdapter extends ArrayAdapter<User> {

    public CustomerAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    private int selectedPosition = -1;
    public CustomerAdapter( @NonNull Context context, @NonNull List<User> users )
    {
        super(context, 0, users);
    }
    public CustomerAdapter(
            @NonNull Context context,
            int resource,
            @NonNull List<User> users
    ) {
        super(context, resource, users);
    }

    @Override
    public View getView(int position, View convertView,
                        ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_user, parent, false);
        }

        User user = getItem(position);

        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvPhone = view.findViewById(R.id.tvPhone);
        ImageView imgAvatar = view.findViewById(R.id.imgAvatar);

        if (user != null) {
            tvName.setText(user.getName());
            tvPhone.setText(user.getPhone());
            imgAvatar.setImageResource(user.getAvatar());
        }

        // Chia đều chiều cao ListView cho 5 item
        int itemHeight = parent.getHeight() / 5;
        view.setActivated(position == selectedPosition);
        view.setMinimumHeight(itemHeight);

        return view;
    }

    public void setSelectedPosition(int position) {
        this.selectedPosition=position;
    }
}
