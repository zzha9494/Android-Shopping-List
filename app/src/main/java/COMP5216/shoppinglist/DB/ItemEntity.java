package COMP5216.shoppinglist.DB;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "ItemList")
public class ItemEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    private int ID;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private String count;

    @ColumnInfo
    private Date time;

    @ColumnInfo
    private boolean ticked;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isTicked() {
        return ticked;
    }

    public void setTicked(boolean ticked) {
        this.ticked = ticked;
    }
}
