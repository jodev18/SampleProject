package quietboy.android.sampleproject.localdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DataItemsDao {

    @Query("SELECT * FROM dataitem")
    List<DataItem> getAll();

    @Query("SELECT * FROM dataitem WHERE uid IN (:itemIds)")
    List<DataItem> loadAllByIds(int[] itemIds);

//    @Query("SELECT * FROM dataitem WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    User findByName(String first, String last);

    @Insert
    void insertAll(DataItem... users);

    @Delete
    void delete(DataItem user);
}
