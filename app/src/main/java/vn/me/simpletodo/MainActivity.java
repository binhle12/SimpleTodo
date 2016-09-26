package vn.me.simpletodo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<TodoItem> items;
    private ItemsAdapter itemsAdapter;
    private ListView lvItems;
    private EditText etNewItem;
    private int editingPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readItemsFromDB();
        lvItems = (ListView) findViewById(R.id.lvItems);
        etNewItem = (EditText) findViewById(R.id.etNewItem);
        itemsAdapter = new ItemsAdapter(this, items);
        lvItems.setAdapter(itemsAdapter);
        lvItems.requestFocus();
    }

    public void onAddItem(View view) {
        String itemText = etNewItem.getText().toString();
        if (!itemText.isEmpty()) {
            itemsAdapter.add(new TodoItem(itemText));
            etNewItem.setText("");
            writeItems();
            hideSoftKeyboard(etNewItem);
        } else {
            Toast.makeText(this, "New item is empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void readItems() {
//        File filesDir = getFilesDir();
//        File taskFile = new File(filesDir, GlobalConstants.TASK_FILE);
//        try {
//            items = new ArrayList<>(FileUtils.readLines(taskFile));
//        } catch (IOException e) {
//            items = new ArrayList<>();
//            e.printStackTrace();
//        }
    }

    private void writeItems() {
//        File fileDir = getFilesDir();
//        File taskFile = new File(fileDir, GlobalConstants.TASK_FILE);
//        try {
//            FileUtils.writeLines(taskFile, items);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GlobalConstants.REQUEST_CODE && resultCode == RESULT_OK) {
            int position = data.getIntExtra(GlobalConstants.POSITION, -1);
            if (position != -1) {
                String newContent = data.getStringExtra(GlobalConstants.ITEM);
                TodoItem item = items.get(position);
                item.content = newContent;
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                Toast.makeText(this, "Edit successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void readItemsFromDB() {
        items = new ArrayList<>();
    }
}
