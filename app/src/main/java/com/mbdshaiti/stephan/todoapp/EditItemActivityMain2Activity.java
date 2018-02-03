package com.mbdshaiti.stephan.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivityMain2Activity extends AppCompatActivity {
    EditText etEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item_main2);
        String item = getIntent().getStringExtra("item");
        etEditText= (EditText) findViewById(R.id.editText);
        etEditText.setText(item);
    }
public void saveItem(View view)
{
    Intent intent = new Intent();
    int position = getIntent().getIntExtra("position", 0);
    intent.putExtra("item", etEditText.getText().toString());
    intent.putExtra("position",position) ;
    setResult(RESULT_OK, intent); // set result code and bundle data for response
    finish(); // closes the activity, pass data to parent
}

}
