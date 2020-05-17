package website.entire.nonononotifications.data.daos;


import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import website.entire.nonononotifications.data.entities.ChatMetaData;

import java.util.List;



@Dao
public abstract class ChatMetaDataDao {
    @Query("SELECT * FROM chat_metadata")
    public abstract LiveData<List<ChatMetaData>> getAll();

    @Query("SELECT * FROM chat_metadata WHERE uid IN (:ids) ORDER BY timestamp DESC")
    public abstract LiveData<List<ChatMetaData>> loadAllByIds(long[] ids);

    @Insert
    public abstract void insertAll(ChatMetaData... chats);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    public abstract void insert(ChatMetaData chat);

    @Update(onConflict = OnConflictStrategy.FAIL)
    public abstract void update(ChatMetaData chat);

    public void upsert(ChatMetaData entity) {
        try {
            insert(entity);
        } catch (SQLiteConstraintException exception) {
            update(entity);
        }
    }

    @Delete
    public abstract void delete(ChatMetaData chats);
}