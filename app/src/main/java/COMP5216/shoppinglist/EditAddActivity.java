package COMP5216.shoppinglist;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class EditAddActivity extends AppCompatActivity {
    private int position;
    private String name;
    private String count;

    EditText editName;
    EditText editCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_add_item);

        editName = findViewById(R.id.editTextName);
        editCount = findViewById(R.id.editTextCount);

        this.position = getIntent().getIntExtra("position",-1);
        Log.i("intent test", ": " + this.position);
        if (this.position != -1) {
            this.name = getIntent().getStringExtra("name");
            this.count = getIntent().getStringExtra("count");

            this.editName.setText(this.name);
            this.editCount.setText(this.count);
        }

    }
}
