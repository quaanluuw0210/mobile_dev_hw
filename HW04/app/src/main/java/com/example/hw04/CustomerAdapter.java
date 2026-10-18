package com.example.hw04;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomerAdapter extends ArrayAdapter<User> {

    private Context context;
    private List<User> students;
    private int itemHeight = -1;
    private int selectedPosition = 0; // Vị trí được chọn mặc định

    public CustomerAdapter(@NonNull Context context, List<User> students) {
        super(context, 0, students);
        this.context = context;
        this.students = students;
    }

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged(); // Cập nhật lại giao diện để tô vệt chọn
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        }

        if (itemHeight > 0) {
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    itemHeight
            );
            convertView.setLayoutParams(params);
        }

        User student = getItem(position);

        ImageView imgAvatar = convertView.findViewById(R.id.imgAvatar);
        TextView tvStudentCode = convertView.findViewById(R.id.tvStudentCode);

        if (student != null) {
            imgAvatar.setImageResource(student.getAvatarResId());
            tvStudentCode.setText(student.getId());
        }

        // Xử lý "vệt chọn" (sáng lên) cho item đang chọn
        if (position == selectedPosition) {
            convertView.setBackgroundColor(Color.LTGRAY); // Màu nền khi chọn
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT); // Màu nền bình thường
        }

        return convertView;
    }
    public void setItemHeight(int height) {
        this.itemHeight = height;
        notifyDataSetChanged(); // Vẽ lại giao diện với chiều cao mới
    }
}
