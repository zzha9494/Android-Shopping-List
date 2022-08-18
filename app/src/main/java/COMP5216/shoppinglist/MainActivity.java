package COMP5216.shoppinglist;

import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    ArrayList<Item> items;
    ListView listView;
    ItemAdapter itemAdapter;

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

        itemAdapter = new ItemAdapter(items, this);
        listView.setAdapter(itemAdapter);




    }
}