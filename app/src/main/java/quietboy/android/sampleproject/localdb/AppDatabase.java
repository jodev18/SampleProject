package quietboy.android.sampleproject.localdb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DataItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DataItemsDao dataItemsDao();

}
