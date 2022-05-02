package quietboy.android.sampleproject.localdb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DataItem {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "track_name")
    public String trackName;

    @ColumnInfo(name = "track_price")
    public Double trackPrice;

    @ColumnInfo(name = "track_kind")
    public String trackType;
}
