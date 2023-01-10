package com.example.todo;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btn;
    ListView lv;
    DBHelper db;
    EditText newwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAdjustScreen();

        ImageView info = findViewById(R.id.right_icon);
        info.setOnClickListener(view -> Toast.makeText(MainActivity.this, "Long press the task if completed", Toast.LENGTH_SHORT).show());

        btn = findViewById(R.id.button);
        newwork = findViewById(R.id.newwork);
        lv= findViewById(R.id.mylist);

        Fill();

        btn.setOnClickListener(this::AddData);
    }

    public void AddData(View v) {
        try {
            if (TextUtils.isEmpty(newwork.getText().toString()))
                Toast.makeText(this, "Please enter text", Toast.LENGTH_SHORT).show();
            else {
                db = new DBHelper(this);
                String s = db.InsertData(newwork.getText().toString());
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
                Fill();
                newwork.setText("");
                closeKeyboard();
            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Fill() {
        try {
            int[] id = {R.id.txtListElement};
            String[] work = new String[]{"work"};
            if (db == null)
                db= new DBHelper(this);
            Cursor c = db.getworks();
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.list_template, c, work, id, 0);
            lv.setAdapter(adapter);
            lv.setOnItemLongClickListener((adapterView, view, i, l) -> {
                Cursor c1 = (Cursor)lv.getItemAtPosition(i);
                String s = c1.getString(1);
                db.DeleteData(s);
                Fill();
                Toast.makeText(MainActivity.this, "Task Done :)", Toast.LENGTH_SHORT).show();
                return true;
            });
        } catch (Exception ex) {
            Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    protected void setAdjustScreen(){
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    private void closeKeyboard(){
        View view = this.getCurrentFocus();
        if(view!=null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }


}