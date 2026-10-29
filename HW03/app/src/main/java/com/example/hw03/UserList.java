package com.example.hw03;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;

import java.io.File;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class UserList extends Fragment {


    private ListView lvStudents;
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
    private List<User> loadStudentsFromDB() {
        List<User> list = new ArrayList<>();

        File storagePath = requireContext().getApplicationContext().getFilesDir();
        String myDbPath = storagePath + "/classDB";

        try {
            SQLiteDatabase db = SQLiteDatabase.openDatabase(myDbPath, null, SQLiteDatabase.OPEN_READONLY);

            // Truy vấn thêm cột Diem và Avatar
            String sql = "SELECT h.MaHS, h.TenHS, h.Diem, h.Avatar, l.TenLop " +
                    "FROM HOCSINH h " +
                    "LEFT JOIN LOPHOC l ON h.MaLop = l.MaLop";

            Cursor cursor = db.rawQuery(sql, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int colMaHS = cursor.getColumnIndex("MaHS");
                    int colTenHS = cursor.getColumnIndex("TenHS");
                    int colDiem = cursor.getColumnIndex("Diem");
                    int colAvatar = cursor.getColumnIndex("Avatar");
                    int colTenLop = cursor.getColumnIndex("TenLop");

                    String maHS = (colMaHS != -1) ? cursor.getString(colMaHS) : "";
                    String tenHS = (colTenHS != -1) ? cursor.getString(colTenHS) : "";
                    double diem = (colDiem != -1) ? cursor.getDouble(colDiem) : 0.0;
                    String avatarName = (colAvatar != -1) ? cursor.getString(colAvatar) : "avatar1";
                    String tenLop = (colTenLop != -1) ? cursor.getString(colTenLop) : "";

                    // Chuyển chuỗi tên avatar (VD: "avatar1") thành ID tài nguyên drawable
                    int avatarResId = requireContext().getResources().getIdentifier(
                            avatarName, "drawable", requireContext().getPackageName());

                    if (avatarResId == 0) { // Trường hợp không tìm thấy hình ảnh tương ứng
                        avatarResId = R.drawable.avatar1;
                    }

                    list.add(new User(maHS, tenHS, tenLop, diem, avatarResId));
                } while (cursor.moveToNext());

                cursor.close();
            }

            db.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        lvStudents = view.findViewById(R.id.lvStudents);

        // Tạo dữ liệu mẫu
        studentList = new ArrayList<>();

        studentList=loadStudentsFromDB();



        adapter = new CustomerAdapter(requireContext(), studentList);
        lvStudents.setAdapter(adapter);

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