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
        for (int i = 0; i < 1; i++) {
            items.add(new Item("item" + i));// test data
            items.get(i).getTime().add(Calendar.HOUR, i);
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
                        int i = result.getData().getIntExtra("position", -1);
                        Item updatedItem = new Item(
                                result.getData().getStringExtra("name"),
                                result.getData().getStringExtra("count")
                        );
                        Calendar updatedCalendar = Calendar.getInstance();
//                        Log.i("Updated item in list ", ": updated" + result.getData().getIntExtra("day", 0));
                        updatedCalendar.set(
                                result.getData().getIntExtra("year", updatedCalendar.get(Calendar.YEAR)),
                                result.getData().getIntExtra("month", updatedCalendar.get(Calendar.MONTH)),
                                result.getData().getIntExtra("day", updatedCalendar.get(Calendar.DAY_OF_MONTH)),
                                result.getData().getIntExtra("hour", updatedCalendar.get(Calendar.HOUR)),
                                result.getData().getIntExtra("minute", updatedCalendar.get(Calendar.MINUTE))
                        );
                        updatedItem.setTime(updatedCalendar);
                        if (i == -1)
                            items.add(updatedItem);
                        else
                            items.set(i, updatedItem);
                        Log.i("Updated item in list ", ": updated");
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
                intent.putExtra("count", items.get(i).getCount());
                Calendar c = items.get(i).getTime();
                intent.putExtra("year", c.get(Calendar.YEAR));
                intent.putExtra("month", c.get(Calendar.MONTH));
                intent.putExtra("day", c.get(Calendar.DAY_OF_MONTH));
                intent.putExtra("hour", c.get(Calendar.HOUR_OF_DAY));
                intent.putExtra("minute", c.get(Calendar.MINUTE));

                mLauncher.launch(intent);
            }
        });


    }
}