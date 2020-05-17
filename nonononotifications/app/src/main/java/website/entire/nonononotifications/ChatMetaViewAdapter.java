package website.entire.nonononotifications;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import website.entire.nonononotifications.R;
import website.entire.nonononotifications.data.entities.ChatMetaData;

import static java.lang.System.currentTimeMillis;


public class ChatMetaViewAdapter extends RecyclerView.Adapter<ChatMetaViewAdapter.MyViewHolder> {
    Activity activity;
    LinearLayout rootView;
    List<ChatMetaData> mDataset;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        // each data item is just a string in this case
        private Activity activity;
        private LinearLayout rootView;
        private ConstraintLayout mainView;
        public long uid;
        public TextView fullName;
        public TextView timeIgnored;
        public ImageView imgProfile;
        public TextView snarkyComment;

        public MyViewHolder(Activity activity, LinearLayout rootView, ConstraintLayout v) {
            super(v);
            this.activity = activity;
            this.rootView = rootView;
            mainView = v;
            fullName = v.findViewById(R.id.text_full_name);
            timeIgnored = v.findViewById(R.id.text_time_ignored);
            imgProfile = v.findViewById(R.id.img_profile);
            snarkyComment = v.findViewById(R.id.snarky_comment);

            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb-messenger://user/" + uid));
            activity.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View view){
            Vibrator v = (Vibrator) view.getContext().getSystemService(Context.VIBRATOR_SERVICE);
            int vibrationLength = 200;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(vibrationLength, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(vibrationLength);
            }
            Log.d("ChatMetaViewAdapter", "UID of long press: " + uid);
            return true;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ChatMetaViewAdapter(List<ChatMetaData> myDataset, Activity activity, LinearLayout rootView) {
        this.rootView = rootView;
        this.activity = activity;
        mDataset = myDataset;
    }

    public void setData(List<ChatMetaData> data){
        mDataset = data;
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ChatMetaViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_home_list_view, parent, false);
        MyViewHolder vh = new MyViewHolder(activity, rootView, v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if (mDataset != null){
            ChatMetaData element = mDataset.get(position);
            holder.uid = element.uid;
            holder.fullName.setText(element.fullName);
            long time = (System.currentTimeMillis() - element.timestamp) / 1000 / 60;
            holder.timeIgnored.setText("Time Since Last ignored: " + time + "min");
            holder.snarkyComment.setText(element.body);
            Bitmap img = BitmapFactory.decodeFile(element.imageUri);
            holder.imgProfile.setImageBitmap(img);
        } else {
            holder.fullName.setText("Full Name");
            holder.timeIgnored.setText("Time Since Last ignored: 0min");
            holder.snarkyComment.setText("Snarky Comment");
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
