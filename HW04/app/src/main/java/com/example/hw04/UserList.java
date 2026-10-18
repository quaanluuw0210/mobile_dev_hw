package com.example.hw04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UserList extends Fragment {

    private ListView lvStudents;

    private TextView tvHeader;
    private CustomerAdapter adapter;
    private List<User> studentList;

    // Interface để truyền dữ liệu sang MainActivity
    public interface OnStudentSelectedListener {
        void onStudentSelected(User student, int index);
    }

    private OnStudentSelectedListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnStudentSelectedListener) {
            listener = (OnStudentSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " phải implement OnStudentSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        lvStudents = view.findViewById(R.id.lvStudents);
        tvHeader=view.findViewById(R.id.tvHeader);

        // Tạo dữ liệu mẫu
        studentList = new ArrayList<>();
        studentList.add(new User("A1_9829", "Lê Thị A", "A1", 8.0, R.drawable.avatar1));
        studentList.add(new User("A1_1809", "Nguyễn Văn Q", "A1", 4.5, R.drawable.avatar2));
        studentList.add(new User("A1_2809", "Nguyễn Thi W", "A1", 9.5, R.drawable.avatar3));
        studentList.add(new User("A1_3809", "Nguyễn Văn R", "A1", 7, R.drawable.avatar1));
        studentList.add(new User("A1_4809", "Nguyễn Thi T", "A1", 7.5, R.drawable.avatar2));
        studentList.add(new User("A2_3509", "Trần Văn C", "A2", 9.0, R.drawable.avatar3));
        studentList.add(new User("A2_4100", "Phạm Thị D", "A2", 6.5, R.drawable.avatar1));
        studentList.add(new User("A2_3880", "Luu Van E", "A2", 9.8, R.drawable.avatar3));
        studentList.add(new User("A2_4380", "Le Thị F", "A2", 6.4, R.drawable.avatar1));
        studentList.add(new User("A2_3494", "Nguyen Thị G", "A2", 5.5, R.drawable.avatar2));

        adapter = new CustomerAdapter(requireContext(), studentList);
        lvStudents.setAdapter(adapter);

        int defaultIndex = 0;

        User defaultStudent = studentList.get(defaultIndex);

        tvHeader.setText(defaultStudent.getId());

        adapter.setSelectedPosition(defaultIndex);




        lvStudents.post(() -> {
            int totalHeight = lvStudents.getHeight();
            if (totalHeight > 0 && !studentList.isEmpty()) {
                int itemHeight = totalHeight / studentList.size(); // Chiều cao cha / số lượng con
                adapter.setItemHeight(itemHeight); // Truyền chiều cao này vào Adapter
            }
        });

        lvStudents.setOnItemClickListener((parent, v, position, id) -> {
            adapter.setSelectedPosition(position);
            if (listener != null) {
                listener.onStudentSelected(studentList.get(position), position);
                tvHeader.setText(studentList.get(position).getId().toString());
            }
        });

        return view;
    }

    // Hàm public cho MainActivity gọi để thay đổi vệt chọn từ nút bấm F, P, N, L
    public void setSelectionIndex(int index) {
        if (index >= 0 && index < studentList.size()) {
            adapter.setSelectedPosition(index);
            lvStudents.smoothScrollToPosition(index);
        }
    }

    public List<User> getStudentList() {
        return studentList;
    }
}