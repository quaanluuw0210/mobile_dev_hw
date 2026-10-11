package com.example.hw03;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvSelection;
    private ListView listView;
    private Button btn1, btn2, btn3;

    // 1. Danh sách gốc chứa đầy đủ 15 người dùng
    private List<User> fullList = new ArrayList<>();
    // 2. Danh sách hiển thị trên ListView (chỉ chứa 5 người dùng tương ứng mỗi trang)
    private List<User> displayList = new ArrayList<>();

    // Sử dụng tên Adapter của bạn (CustomerAdapter hoặc UserAdapter)
    private CustomerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ các thành phần từ giao diện XML
        tvSelection = findViewById(R.id.tvSelection); // Thay ID đúng với file activity_main.xml của bạn
        listView = findViewById(R.id.listUsers);
        btn1 = findViewById(R.id.btn1);               // Thay ID đúng với nút 1 của bạn
        btn2 = findViewById(R.id.btn2);               // Thay ID đúng với nút 2 của bạn
        btn3 = findViewById(R.id.btn3);               // Thay ID đúng với nút 3 của bạn

        // Khởi tạo đủ 15 user mẫu vào fullList
        initData();

        // Khởi tạo Adapter liên kết với displayList
        adapter = new CustomerAdapter(this, displayList);
        listView.setAdapter(adapter);


        loadPage(1);

        // Sự kiện khi người dùng chọn một item trên ListView
        listView.setOnItemClickListener((parent, view, position, id) -> {
            User selectedUser = displayList.get(position);
            adapter.setSelectedPosition(position);
            adapter.notifyDataSetChanged();
            tvSelection.setText("You choose: " + selectedUser.getName());
        });

        // Xử lý sự kiện click cho 3 nút phân trang
        btn1.setOnClickListener(v -> loadPage(1));
        btn2.setOnClickListener(v -> loadPage(2));
        btn3.setOnClickListener(v -> loadPage(3));
    }

    // Hàm tạo 15 user mẫu
    private void initData() {
        fullList.clear();
        // Trang 1
        fullList.add(new User("Nguyen Van A", "0989897873", R.drawable.avatar1));
        fullList.add(new User("Le Thi B", "0967995843", R.drawable.avatar2));
        fullList.add(new User("Tran Van C", "0907955843", R.drawable.avatar3));
        fullList.add(new User("Phan Van C", "0967885811", R.drawable.avatar4));
        fullList.add(new User("Dinh Van D", "0988885231", R.drawable.avatar5));

        // Trang 2
        fullList.add(new User("Hoang Van E", "0912345678", R.drawable.avatar2));
        fullList.add(new User("Vu Thi F", "0923456789", R.drawable.avatar3));
        fullList.add(new User("Ngo Van G", "0934567890", R.drawable.avatar4));
        fullList.add(new User("Dang Thi H", "0945678901", R.drawable.avatar5));
        fullList.add(new User("Bui Van I", "0956789012", R.drawable.avatar1));

        // Trang 3
        fullList.add(new User("Do Thi K", "0967890123", R.drawable.avatar3));
        fullList.add(new User("Ho Van L", "0978901234", R.drawable.avatar4));
        fullList.add(new User("Dương Thi M", "0989012345", R.drawable.avatar5));
        fullList.add(new User("Lieu Van N", "0990123456", R.drawable.avatar1));
        fullList.add(new User("Trinh Thi O", "0901122334", R.drawable.avatar2));
    }

    // Hàm lấy 5 người dùng tương ứng theo trang
    private void loadPage(int page) {
        displayList.clear();
        int startIndex = (page - 1) * 5; // Trang 1 -> 0, Trang 2 -> 5, Trang 3 -> 10
        int endIndex = Math.min(startIndex + 5, fullList.size()); // Đảm bảo không bị quá chỉ số danh sách

        for (int i = startIndex; i < endIndex; i++) {
            displayList.add(fullList.get(i));
        }

        if (adapter != null) {
            adapter.setSelectedPosition(-1);
            adapter.notifyDataSetChanged();
        }


        btn1.setEnabled(page != 1);
        btn2.setEnabled(page != 2);
        btn3.setEnabled(page != 3);
    }
}