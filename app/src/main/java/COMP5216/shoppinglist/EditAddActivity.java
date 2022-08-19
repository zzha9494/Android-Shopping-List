package COMP5216.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;

public class EditAddActivity extends AppCompatActivity {
    int i;
    String name;
    String count;
    int year;
    int month;
    int day;
    int hour;
    int minute;

    EditText editName;
    EditText editCount;
    DatePicker datePicker;
    TimePicker timepicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_add_item);

        editName = findViewById(R.id.editTextName);
        editCount = findViewById(R.id.editTextCount);
        datePicker = findViewById(R.id.datePicker);
        timepicker = findViewById(R.id.timePicker);

        timepicker.setIs24HourView(true);

        this.i = getIntent().getIntExtra("position",-1);
//        Log.i("intent test", ": " + this.i);
        if (this.i != -1) {
            this.name = getIntent().getStringExtra("name");
            this.count = getIntent().getStringExtra("count");
            this.year = getIntent().getIntExtra("year", 0);
            this.month = getIntent().getIntExtra("month", 0);
            this.day = getIntent().getIntExtra("day", 0);
            this.hour = getIntent().getIntExtra("hour", 0);
            this.minute = getIntent().getIntExtra("minute", 0);

            this.editName.setText(this.name);
            this.editCount.setText(this.count);

            datePicker.updateDate(this.year, this.month, this.day);
            timepicker.setHour(this.hour);
            timepicker.setMinute(this.minute);
        }

    }

    public void onSubmitSave(View v) {
        Intent result = new Intent();

        result.putExtra("position", this.i);
        result.putExtra("name", this.editName.getText().toString());
        result.putExtra("count", this.editCount.getText().toString());
        result.putExtra("year", this.datePicker.getYear());
        result.putExtra("month", this.datePicker.getMonth());
        result.putExtra("day", this.datePicker.getDayOfMonth());
        result.putExtra("hour", this.timepicker.getHour());
        result.putExtra("minute", this.timepicker.getMinute());

        setResult(RESULT_OK, result);// Set result code and bundle data for response
        finish(); // Close the activity, pass data to parent
    }

    public void onSubmitCancel(View v) {
        Intent result = new Intent();
        setResult(RESULT_CANCELED, result);
        finish();
    }
}
