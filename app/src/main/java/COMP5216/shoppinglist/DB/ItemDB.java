package COMP5216.shoppinglist.DB;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import androidx.room.TypeConverters;

@Database(entities = {ItemEntity.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ItemDB extends RoomDatabase {
    private static final String DATABASE_NAME = "item_db";
    private static ItemDB DBINSTANCE;

    public abstract ItemDao itemDao();

    public static ItemDB getDatabase(Context context) {
        if (DBINSTANCE == null) {
            synchronized (ItemDB.class) {
                DBINSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        ItemDB.class, DATABASE_NAME).build();
            }
        }
        return DBINSTANCE;
    }
    public static void destroyInstance() {
        DBINSTANCE = null;
    }
}
