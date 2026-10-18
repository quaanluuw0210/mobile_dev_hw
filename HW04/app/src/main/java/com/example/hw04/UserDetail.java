package com.example.hw04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class UserDetail extends Fragment {

    // Interface truyền sự kiện nhấn nút điều hướng về MainActivity
    public interface OnNavigationListener {
        void onNavigate(String action); // action: "FIRST", "PREVIOUS", "NEXT", "LAST"
    }

    private OnNavigationListener navigationListener;

    private TextView tvHeaderCode;
    private TextView tvStudentName;
    private TextView tvStudentClass;
    private TextView tvStudentGpa;

    private Button btnFirst;
    private Button btnPrevious;
    private Button btnNext;
    private Button btnLast;

    public UserDetail() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnNavigationListener) {
            navigationListener = (OnNavigationListener) context;
        } else {
            throw new RuntimeException(context.toString() + " phải implement OnNavigationListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_detail, container, false);

        // Ánh xạ các thành phần giao diện
        tvHeaderCode = view.findViewById(R.id.tvHeaderCode);
        tvStudentName = view.findViewById(R.id.tvStudentName);
        tvStudentClass = view.findViewById(R.id.tvStudentClass);
        tvStudentGpa = view.findViewById(R.id.tvStudentGpa);

        btnFirst = view.findViewById(R.id.btnFirst);
        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnNext = view.findViewById(R.id.btnNext);
        btnLast = view.findViewById(R.id.btnLast);

        // Gán sự kiện click cho từng nút bấm
        btnFirst.setOnClickListener(v -> {
            if (navigationListener != null) navigationListener.onNavigate("FIRST");
        });

        btnPrevious.setOnClickListener(v -> {
            if (navigationListener != null) navigationListener.onNavigate("PREVIOUS");
        });

        btnNext.setOnClickListener(v -> {
            if (navigationListener != null) navigationListener.onNavigate("NEXT");
        });

        btnLast.setOnClickListener(v -> {
            if (navigationListener != null) navigationListener.onNavigate("LAST");
        });

        return view;
    }

    public void updateStudentDetail(User student, int index, int totalSize) {
        if (student != null) {
            tvHeaderCode.setText(student.getId());
            tvStudentName.setText("Họ tên: " + student.getName());
            tvStudentClass.setText("Lớp: " + student.getClassName());
            tvStudentGpa.setText("Điểm trung bình: " + student.getGpa());
        }

        // Cập nhật trạng thái bật/tắt (Enable/Disable) của các nút F, P, N, L theo vị trí[cite: 1]
        if (totalSize <= 1) {
            // Danh sách chỉ có 1 phần tử hoặc rỗng -> Vô hiệu hóa tất cả
            btnFirst.setEnabled(false);
            btnPrevious.setEnabled(false);
            btnNext.setEnabled(false);
            btnLast.setEnabled(false);
        } else if (index == 0) {

            btnFirst.setEnabled(false);
            btnPrevious.setEnabled(false);
            btnNext.setEnabled(true);
            btnLast.setEnabled(true);
        } else if (index == totalSize - 1) {

            btnFirst.setEnabled(true);
            btnPrevious.setEnabled(true);
            btnNext.setEnabled(false);
            btnLast.setEnabled(false);
        } else {

            btnFirst.setEnabled(true);
            btnPrevious.setEnabled(true);
            btnNext.setEnabled(true);
            btnLast.setEnabled(true);
        }
    }
}