package COMP5216.shoppinglist;

import java.util.Calendar;

public class Item {
    private String name;
    private int count;
    private Calendar time;
    private boolean ticked;

    public Item(String name) {
        this(name, 1);
    }

    public Item(String name, int count) {
        this.name = name;
        this.count = count;
        this.time = Calendar.getInstance();
        this.ticked = false;
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

    public String getTimeGap() {
        Calendar now = Calendar.getInstance();
        if (this.ticked)
            return "";

        if (this.time.before(now))
            return "OVERDUE";

        Long temp = this.time.getTimeInMillis() - now.getTimeInMillis();
        int hours = (int) (temp / 1000 / 60 / 60); //hours
        int days = hours / 24;
        hours = hours % 24;
        if (days != 0)
            return days + " Days " + hours + " Hrs";
        return hours + " Hrs";
    }

    public boolean isTicked() {
        return ticked;
    }

    public void setTicked(boolean ticked) {
        this.ticked = ticked;
    }
}
