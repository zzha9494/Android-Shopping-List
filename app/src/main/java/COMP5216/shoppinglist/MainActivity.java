package COMP5216.shoppinglist;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    ArrayList<Item> items;
    ListView listView;
    ItemAdapter itemAdapter;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = new ArrayList<>();

        // test data
        for (int i = 0; i < 30; i++) {
            items.add(new Item("item" + i));// test data
            items.get(i).getTime().add(Calendar.HOUR, i-5);
        }


        listView = findViewById(R.id.listView);
        add = findViewById(R.id.addButton);

        itemAdapter = new ItemAdapter(items, this);
        listView.setAdapter(itemAdapter);

        setupListener();
    }

    private void setupListener() {
        ActivityResultLauncher<Intent> mLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // Extract name value from result extras
//                        String editedItem = result.getData().getExtras().getString("item");
//                        int position = result.getData().getIntExtra("position", -1);
//                        items.set(position, editedItem);
                        Log.i("Updated item in list ", ":" + result.getData().getExtras().getString("name"));
                        Log.i("Updated item in list ", ":" + result.getData().getExtras().getString("count"));
                    }
                    else if (result.getResultCode() == RESULT_CANCELED) {
                        Log.i("Updated item in list ", ": canceled");
                    }
                }
        );

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditAddActivity.class);
                mLauncher.launch(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.i("MainActivity", "Clicked item " + position + ": " + position);
                Intent intent = new Intent(MainActivity.this, EditAddActivity.class);
                intent.putExtra("position", i);
                intent.putExtra("name", items.get(i).getName());
                intent.putExtra("count", String.valueOf(items.get(i).getCount()));

                mLauncher.launch(intent);
            }
        });


    }
}