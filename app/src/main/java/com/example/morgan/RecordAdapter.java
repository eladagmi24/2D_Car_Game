package com.example.morgan;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class RecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Activity activity;
    private ArrayList<Record> records = new ArrayList<>();
    private RecordItemClickListener recordItemClickListener;

    public RecordAdapter(Activity activity, ArrayList<Record> records) {
        this.activity = activity;
        this.records = records;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_record_item, parent, false);
        return new RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public interface RecordItemClickListener {
        void recordItemClick(Record record, int position);

    }
    private Record getItem(int position) {
        return records.get(position);
    }
    public class RecordViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView record_LBL_title;
        private MaterialTextView record_LBL_coins;
        private MaterialTextView record_LBL_distance;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            record_LBL_title = itemView.findViewById(R.id.record_LBL_title);
            record_LBL_coins = itemView.findViewById(R.id.record_LBL_coins);
            record_LBL_distance = itemView.findViewById(R.id.record_LBL_distance);

            itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            recordItemClickListener.recordItemClick(getItem(getAdapterPosition()), getAdapterPosition());
                        }
                    }
            );

        }
    }
}
