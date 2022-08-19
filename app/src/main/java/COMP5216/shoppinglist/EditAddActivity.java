package COMP5216.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class EditAddActivity extends AppCompatActivity {
    private int i;
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

        this.i = getIntent().getIntExtra("position",-1);
        Log.i("intent test", ": " + this.i);
        if (this.i != -1) {
            this.name = getIntent().getStringExtra("name");
            this.count = getIntent().getStringExtra("count");

            this.editName.setText(this.name);
            this.editCount.setText(this.count);
        }

    }

    public void onSubmitSave(View v) {
        Intent result = new Intent();

        result.putExtra("position", this.i);
        result.putExtra("name", this.editName.getText().toString());
        result.putExtra("count", this.editCount.getText().toString());

        setResult(RESULT_OK, result);// Set result code and bundle data for response
        finish(); // Close the activity, pass data to parent
    }

    public void onSubmitCancel(View v) {
        Intent result = new Intent();
        setResult(RESULT_CANCELED, result);
        finish();
    }
}
