package COMP5216.shoppinglist;

import COMP5216.shoppinglist.DB.ItemDB;
import COMP5216.shoppinglist.DB.ItemDao;
import COMP5216.shoppinglist.DB.ItemEntity;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class MainActivity extends AppCompatActivity {
    ArrayList<Item> items;
    ListView listView;
    ItemAdapter itemAdapter;
    Button add;

    ItemDB db;
    ItemDao itemDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TimeZone.setDefault(TimeZone.getTimeZone("GMT+10"));

        db = ItemDB.getDatabase(this.getApplication().getApplicationContext());
        itemDao = db.itemDao();

        readDataFromDB();
        // test data
//        for (int i = 0; i < 5; i++) {
//            items.add(new Item("item" + i));// test data
//            items.get(i).getTime().add(Calendar.HOUR, i-3);
//        }

        listView = findViewById(R.id.listView);
        add = findViewById(R.id.addButton);

        itemAdapter = new ItemAdapter(items, this);
        listView.setAdapter(itemAdapter);

        setupListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveNow();
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
                    }
                    else if (result.getResultCode() == RESULT_CANCELED) {
//                        Log.i("Updated item in list ", ": canceled");
                    }
                    saveNow();
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
//                Log.i("MainActivity", "calendar + timezone" + c.getTimeZone());
                intent.putExtra("year", c.get(Calendar.YEAR));
                intent.putExtra("month", c.get(Calendar.MONTH));
                intent.putExtra("day", c.get(Calendar.DAY_OF_MONTH));
                intent.putExtra("hour", c.get(Calendar.HOUR_OF_DAY));
                intent.putExtra("minute", c.get(Calendar.MINUTE));

                mLauncher.launch(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.dialog_delete_title)
                        .setMessage(R.string.dialog_delete_msg)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                items.remove(position);
                                saveNow();
                            }
                        })
                        .setNeutralButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Nothing happens
                            }
                        })
                        .create()
                        .show();

                return true;
            }
        });
    }

    private void sortData(List<Item> items) {
        Collections.sort(items, (i0, i1) -> {
            if (i0.isTicked() == i1.isTicked())
                return i0.getTime().compareTo(i1.getTime());
            else
                return i0.isTicked() ? 1 : -1;
        });
    }

    private void readDataFromDB() {
        CompletableFuture<Void> future = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                List<ItemEntity> itemsFromDB = itemDao.listAll();
                items = new ArrayList<>();
                if (itemsFromDB != null && itemsFromDB.size() > 0) {
                    for (ItemEntity i: itemsFromDB) {
                        Item temp = new Item(i.getName(), i.getCount());
                        Calendar c = Calendar.getInstance();
                        c.setTime(i.getTime());
                        temp.setTime(c);
                        temp.setTicked(i.isTicked());
                        items.add(temp);
                    }
                }
            }
        });

        try {
            future.get();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveDataToDB() {
        CompletableFuture<Void> future = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                itemDao.deleteAll();
                for (Item i: items) {
                    ItemEntity temp = new ItemEntity(i.getName(), i.getCount(), i.getTime().getTime(), i.isTicked());
                    itemDao.insert(temp);
                }
            }
        });

        try {
            future.get();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveNow() {
        sortData(items);
        itemAdapter.notifyDataSetChanged();
        saveDataToDB();
    }

}