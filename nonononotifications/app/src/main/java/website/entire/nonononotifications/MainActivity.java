package website.entire.nonononotifications;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.List;

import website.entire.nonononotifications.data.entities.ChatMetaData;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button testButton;
    private LinearLayout rootView;
    private TextView testTextView;
    private RecyclerView recyclerView;
    private ChatMetaViewAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<ChatMetaData> myDataset;

    private ChatMetaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        testButton = findViewById(R.id.testButton);
//        testButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                buttonClick(v);
//            }
//        });
        rootView = findViewById(R.id.root_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.home_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        myDataset = new ArrayList<>();
        mAdapter = new ChatMetaViewAdapter(myDataset, this, rootView);
        recyclerView.setAdapter(mAdapter);

        viewModel = ViewModelProviders.of(this).get(ChatMetaViewModel.class);
        viewModel.getMetas().observe(this, new Observer<List<ChatMetaData>>() {
                    @Override
                    public void onChanged(List<ChatMetaData> chatMetaData) {
                        mAdapter.setData(chatMetaData);
                    }
                }
        );

        testTextView = findViewById(R.id.textView);
    }

    private void logFirebaseInstanceId(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//
//                        // Set value to textview
//                        testTextView.setText(msg);
                    }
                });
    }

}
