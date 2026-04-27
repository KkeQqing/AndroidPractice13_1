package com.example.androidpractice13_1;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etStuId, etName, etAge, etMajor;
    private Button btnAdd, btnUpdate, btnDelete, btnQueryAll;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etStuId = findViewById(R.id.et_stuid);
        etName = findViewById(R.id.et_name);
        etAge = findViewById(R.id.et_age);
        etMajor = findViewById(R.id.et_major);
        btnAdd = findViewById(R.id.btn_add);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);
        btnQueryAll = findViewById(R.id.btn_query_all);
        tvResult = findViewById(R.id.tv_result);

        // 添加
        btnAdd.setOnClickListener(v -> addStudent());
        // 修改
        btnUpdate.setOnClickListener(v -> updateStudent());
        // 删除
        btnDelete.setOnClickListener(v -> deleteStudent());
        // 查询全部
        btnQueryAll.setOnClickListener(v -> queryAllStudents());
    }

    // 添加学生
    private void addStudent() {
        String stuId = etStuId.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String ageStr = etAge.getText().toString().trim();
        String major = etMajor.getText().toString().trim();

        if (stuId.isEmpty() || name.isEmpty() || ageStr.isEmpty()) {
            Toast.makeText(this, "信息不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = Integer.parseInt(ageStr);
        ContentValues values = new ContentValues();
        values.put(StudentDBHelper.STU_ID, stuId);
        values.put(StudentDBHelper.NAME, name);
        values.put(StudentDBHelper.AGE, age);
        values.put(StudentDBHelper.MAJOR, major);

        getContentResolver().insert(StudentProvider.CONTENT_URI, values);
        Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
        clearEdit();
    }

    // 修改学生（按学号）
    private void updateStudent() {
        String stuId = etStuId.getText().toString().trim();
        if (stuId.isEmpty()) {
            Toast.makeText(this, "请输入学号", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put(StudentDBHelper.NAME, etName.getText().toString().trim());
        values.put(StudentDBHelper.AGE, Integer.parseInt(etAge.getText().toString().trim()));
        values.put(StudentDBHelper.MAJOR, etMajor.getText().toString().trim());

        int rows = getContentResolver().update(StudentProvider.CONTENT_URI,
                values, StudentDBHelper.STU_ID + "=?", new String[]{stuId});

        if (rows > 0) {
            Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "未找到该学生", Toast.LENGTH_SHORT).show();
        }
    }

    // 删除学生（按学号）
    private void deleteStudent() {
        String stuId = etStuId.getText().toString().trim();
        if (stuId.isEmpty()) {
            Toast.makeText(this, "请输入学号", Toast.LENGTH_SHORT).show();
            return;
        }

        int rows = getContentResolver().delete(StudentProvider.CONTENT_URI,
                StudentDBHelper.STU_ID + "=?", new String[]{stuId});

        if (rows > 0) {
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
            clearEdit();
        } else {
            Toast.makeText(this, "未找到学生", Toast.LENGTH_SHORT).show();
        }
    }

    // 查询所有学生
    private void queryAllStudents() {
        StringBuilder sb = new StringBuilder();
        Cursor cursor = getContentResolver().query(StudentProvider.CONTENT_URI,
                null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String stuId = cursor.getString(cursor.getColumnIndexOrThrow(StudentDBHelper.STU_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(StudentDBHelper.NAME));
                int age = cursor.getInt(cursor.getColumnIndexOrThrow(StudentDBHelper.AGE));
                String major = cursor.getString(cursor.getColumnIndexOrThrow(StudentDBHelper.MAJOR));

                sb.append("学号：").append(stuId).append("\n");
                sb.append("姓名：").append(name).append("\n");
                sb.append("年龄：").append(age).append("\n");
                sb.append("专业：").append(major).append("\n\n");
            }
            cursor.close();
        }
        tvResult.setText(sb);
    }

    private void clearEdit() {
        etStuId.setText("");
        etName.setText("");
        etAge.setText("");
        etMajor.setText("");
    }
}