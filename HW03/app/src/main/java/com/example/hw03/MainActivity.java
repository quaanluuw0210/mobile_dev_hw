package com.example.hw03;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity implements UserList.OnStudentSelectedListener, UserDetail.OnNavigationListener {

    private UserList userListFragment;
    private UserDetail userDetailFragment;

    SQLiteDatabase db;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File storagePath = getApplication().getFilesDir();
        String myDbPath = storagePath + "/classDB";
        try {
            db = SQLiteDatabase.openDatabase(myDbPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);

            createTable();
            insertMockupData();

            db.close();
        } catch (SQLiteException e) {
            System.out.println(e.getMessage());
        }

        userListFragment = (UserList) getSupportFragmentManager().findFragmentById(R.id.fragmentList);
        userDetailFragment = (UserDetail) getSupportFragmentManager().findFragmentById(R.id.fragmentDetail);
    }

    private void createTable() {
        String sqlCreateLopHocTable = "CREATE TABLE IF NOT EXISTS LOPHOC (" +
                "MaLop INTEGER PRIMARY KEY, " +
                "TenLop TEXT, " +
                "SiSo INTEGER);";

        // Thêm cột Diem (REAL) và Avatar (TEXT)
        String sqlCreateHocSinhTable = "CREATE TABLE IF NOT EXISTS HOCSINH (" +
                "MaHS TEXT PRIMARY KEY, " +
                "TenHS TEXT, " +
                "Diem REAL, " +
                "Avatar TEXT, " +
                "MaLop INTEGER);";

        String sqlCreateTriggerInsertHS = "CREATE TRIGGER IF NOT EXISTS check_malop_before_insert_hocsinh " +
                "BEFORE INSERT ON HOCSINH " +
                "FOR EACH ROW " +
                "WHEN (SELECT MaLop FROM LOPHOC WHERE MaLop = NEW.MaLop) IS NULL " +
                "BEGIN " +
                "   SELECT RAISE(ABORT, 'Loi: MaLop khong ton tai trong bang LOPHOC!'); " +
                "END;";

        db.execSQL(sqlCreateLopHocTable);
        db.execSQL(sqlCreateHocSinhTable);
        db.execSQL(sqlCreateTriggerInsertHS);
    }


    private void insertMockupData() {
        db.beginTransaction();
        try {

            db.execSQL("DELETE FROM HOCSINH;");
            db.execSQL("DELETE FROM LOPHOC;");

            // 1. Chèn dữ liệu lớp học
            db.execSQL("INSERT OR IGNORE INTO LOPHOC (MaLop, TenLop, SiSo) VALUES (1, 'A1', 40);");
            db.execSQL("INSERT OR IGNORE INTO LOPHOC (MaLop, TenLop, SiSo) VALUES (2, 'A2', 38);");
            db.execSQL("INSERT OR IGNORE INTO LOPHOC (MaLop, TenLop, SiSo) VALUES (3, 'B1', 42);");

            db.execSQL("INSERT OR IGNORE INTO HOCSINH (MaHS, TenHS, Diem, Avatar, MaLop) VALUES ('A1_9829', 'Le Thi A', 8.0, 'avatar1', 1);");
            db.execSQL("INSERT OR IGNORE INTO HOCSINH (MaHS, TenHS, Diem, Avatar, MaLop) VALUES ('A1_1809', 'Nguyen Van Q', 4.5, 'avatar2', 1);");
            db.execSQL("INSERT OR IGNORE INTO HOCSINH (MaHS, TenHS, Diem, Avatar, MaLop) VALUES ('A1_2809', 'Nguyen Thi W', 9.5, 'avatar3', 1);");

            // Lớp A2 (MaLop = 2)
            db.execSQL("INSERT OR IGNORE INTO HOCSINH (MaHS, TenHS, Diem, Avatar, MaLop) VALUES ('A2_3509', 'Tran Van C', 9.0, 'avatar3', 2);");
            db.execSQL("INSERT OR IGNORE INTO HOCSINH (MaHS, TenHS, Diem, Avatar, MaLop) VALUES ('A2_4100', 'Pham Thi D', 6.5, 'avatar1', 2);");
            db.execSQL("INSERT OR IGNORE INTO HOCSINH (MaHS, TenHS, Diem, Avatar, MaLop) VALUES ('A2_3880', 'Luu Van E', 9.8, 'avatar3', 2);");

            // Lớp B1 (MaLop = 3)
            db.execSQL("INSERT OR IGNORE INTO HOCSINH (MaHS, TenHS, Diem, Avatar, MaLop) VALUES ('B1_1001', 'Hoang Van H', 8.2, 'avatar1', 3);");
            db.execSQL("INSERT OR IGNORE INTO HOCSINH (MaHS, TenHS, Diem, Avatar, MaLop) VALUES ('B1_1002', 'Vu Thi K', 3.8, 'avatar2', 3);");
            db.execSQL("INSERT OR IGNORE INTO HOCSINH (MaHS, TenHS, Diem, Avatar, MaLop) VALUES ('B1_1003', 'Doan Van L', 7.8, 'avatar3', 3);");

            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            System.out.println("Lỗi chèn dữ liệu mẫu: " + e.getMessage());
        } finally {
            db.endTransaction();
        }
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

        userListFragment.setSelectionIndex(currentIndex);
        userDetailFragment.updateStudentDetail(list.get(currentIndex), currentIndex, list.size());
    }
}