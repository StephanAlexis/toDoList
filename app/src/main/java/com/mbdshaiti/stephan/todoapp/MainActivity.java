package com.mbdshaiti.stephan.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> toDoItems;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText etEditText;
    private final int REQUEST_CODE=20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        populateItems();
        lvItems=(ListView) findViewById(R.id.lvlitems );
        etEditText=(EditText) findViewById(R.id.etEditText);
        lvItems.setAdapter(aToDoAdapter);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    toDoItems.remove(i);
                    aToDoAdapter.notifyDataSetChanged();
                    writeItems();
                    return true;
                }

        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, EditItemActivityMain2Activity.class);
                intent.putExtra("item", toDoItems.get(i));
                intent.putExtra("position",i);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    public void populateItems ()
    {
        toDoItems=new ArrayList<String>();
        readItems();
        aToDoAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,toDoItems);
    }

    private void readItems()
    {
        File filesDir=getFilesDir();

        File file=new File(filesDir, "toDo.txt");
        try{
            toDoItems=new ArrayList<String>(FileUtils.readLines(file));
        }
        catch(IOException ex)
        {

        }
    }

    private void writeItems()
    {
        File filesDir=getFilesDir();
        File file=new File(filesDir, "toDo.txt");
        try{
            FileUtils.writeLines(file,toDoItems);
        }
        catch(IOException ex)
        {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onAddItem(View view)
    {
        aToDoAdapter.add(etEditText.getText().toString());
        etEditText.setText("");
        writeItems();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String item = data.getExtras().getString("item");
            int position = data.getExtras().getInt("position");
            toDoItems.remove(position);
            toDoItems.add(position,item);
            aToDoAdapter.notifyDataSetChanged();
            writeItems();

        }
    }
}
