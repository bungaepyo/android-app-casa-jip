package com.example.casa_jip;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {
    private List<TaskData> mDataset;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item has a checkbox, a string and a view
        public CheckBox CheckBox_taskChecked;
        public TextView TextView_taskMessage;
        public View rootView;
        public MyViewHolder(View v) {
            super(v);
            TextView_taskMessage = v.findViewById(R.id.TextView_taskMessage);
            CheckBox_taskChecked = v.findViewById(R.id.CheckBox_taskChecked);
            rootView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TaskAdapter(List<TaskData> myDataset, Context context) {
        mDataset = myDataset;;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TaskAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_task, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Log.d("TASKTASK", mDataset.toString());
        TaskData task = mDataset.get(position);
        holder.TextView_taskMessage.setText(task.getTaskMessage());
        holder.CheckBox_taskChecked.setChecked(task.getTaskChecked());
        holder.TextView_taskMessage.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset == null ? 0 :  mDataset.size();
    }

    public TaskData getTask(int position) {
        return mDataset != null ? mDataset.get(position) : null;
    }

    public void addTask(TaskData task){
        mDataset.add(task);
        notifyItemInserted(mDataset.size()-1);
    }

}