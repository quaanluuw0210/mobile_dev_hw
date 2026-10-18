package com.example.hw04;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements UserList.OnStudentSelectedListener, UserDetail.OnNavigationListener {

    private UserList userListFragment;
    private UserDetail userDetailFragment;

    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lấy tham chiếu tới 2 Fragment
        userListFragment = (UserList) getSupportFragmentManager().findFragmentById(R.id.fragmentList);
        userDetailFragment = (UserDetail) getSupportFragmentManager().findFragmentById(R.id.fragmentDetail);
    }


    @Override
    public void onStudentSelected(User student, int index) {
        this.currentIndex = index;
        if (userDetailFragment != null && userListFragment != null) {
            userDetailFragment.updateStudentDetail(student, currentIndex, userListFragment.getStudentList().size());
        }
    }

    @Override
    public void onNavigate(String action) {
        if (userListFragment == null || userDetailFragment == null) return;

        List<User> list = userListFragment.getStudentList();
        if (list == null || list.isEmpty()) return;

        switch (action) {
            case "FIRST":
                currentIndex = 0;
                break;
            case "PREVIOUS":
                if (currentIndex > 0) currentIndex--;
                break;
            case "NEXT":
                if (currentIndex < list.size() - 1) currentIndex++;
                break;
            case "LAST":
                currentIndex = list.size() - 1;
                break;
        }

        // 1. Cập nhật vệt chọn trên Fragment 1
        userListFragment.setSelectionIndex(currentIndex);

        // 2. Cập nhật chi tiết và bật/tắt nút trên Fragment 2
        userDetailFragment.updateStudentDetail(list.get(currentIndex), currentIndex, list.size());
    }

}