package website.entire.nonononotifications.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import website.entire.nonononotifications.data.daos.ChatMetaDataDao;
import website.entire.nonononotifications.data.entities.ChatMetaData;

public class ChatRepository {

    private ChatMetaDataDao chatMetaDataDao;
    private LiveData<List<ChatMetaData>> allChatMeta;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public ChatRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        chatMetaDataDao = db.chatMetaDataDao();
        allChatMeta = chatMetaDataDao.getAll();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<ChatMetaData>> getAllMeta() {
        return allChatMeta;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insertChats(ChatMetaData... metas) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            chatMetaDataDao.insertAll(metas);
        });
    }

    public void insertChat(ChatMetaData meta) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            chatMetaDataDao.upsert(meta);
        });
    }

}
