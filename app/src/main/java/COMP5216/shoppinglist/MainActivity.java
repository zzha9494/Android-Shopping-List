package COMP5216.shoppinglist;

import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        reference
        listView = findViewById(R.id.listView);

        items = new ArrayList<>();
        items.add(new Item("test"));// test data


    }
}