package COMP5216.shoppinglist.DB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM ItemList")
    List<ItemEntity> listAll();

    @Insert
    void insert(ItemEntity itemEntity);

    @Insert
    void insertAll(ItemEntity... itemEntities);

    @Query("DELETE FROM ItemList")
    void deleteAll();
}


