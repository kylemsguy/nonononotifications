package website.entire.nonononotifications;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import java.util.List;

import website.entire.nonononotifications.data.ChatRepository;
import website.entire.nonononotifications.data.entities.ChatMetaData;

public class ChatMeatViewModel extends AndroidViewModel {
    private ChatRepository repository;
    private LiveData<List<ChatMetaData>> metas;

    public ChatMeatViewModel(Application application) {
        super(application);
        repository = new ChatRepository(application);
        metas = repository.getAllMeta();
    }

    public LiveData<List<ChatMetaData>> getMetas() {
        return metas;
    }
}