package COMP5216.shoppinglist;

import java.util.Calendar;

public class Item {
    private String name;
    private int count;
    private Calendar time;
    private boolean isChecked;

    public Item(String name) {
        this(name, 1);
    }

    public Item(String name, int count) {
        this.name = name;
        this.count = count;
        this.time = Calendar.getInstance();
        this.isChecked = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
