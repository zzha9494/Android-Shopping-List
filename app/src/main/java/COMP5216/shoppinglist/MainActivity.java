package COMP5216.shoppinglist;

import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Item> items;
    ListView listView;
    ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = new ArrayList<>();
        items.add(new Item("test"));// test data

        listView = findViewById(R.id.listView);

        itemAdapter = new ItemAdapter(items, this);
        listView.setAdapter(itemAdapter);




    }
}