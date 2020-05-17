package website.entire.nonononotifications.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "chat_metadata")
public class ChatMetaData {
    @PrimaryKey(autoGenerate = false)
    public long uid;

    @ColumnInfo(name = "timestamp")
    public String timestamp;

    @ColumnInfo(name = "first_name")
    public String first_name;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "body")
    public String body;

    @ColumnInfo(name = "image_uri")
    public String imageUri;

}
