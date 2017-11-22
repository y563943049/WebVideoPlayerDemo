package com.example.netvideoplayerdemo;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * @version $Rev$
 * @anthor Administrator
 * @dsc ${TOOD}
 * @updateAuthor $Author
 * @updateDsc ${TOOD}
 */
public class MP4Adapter extends RecyclerView.Adapter<MP4Adapter.MyViewHolder> {

    private List<MP4> mMP4List;

    public MP4Adapter(List MP4List){
        this.mMP4List = MP4List;
    }

    @Override
    public MP4Adapter.MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler,parent,false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        myViewHolder.videoName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = myViewHolder.getAdapterPosition();
                MP4 mp4 = mMP4List.get(position);
                Intent intent = new Intent(parent.getContext(),MainActivity.class);
                intent.putExtra("mp4",mp4);
                parent.getContext().startActivity(intent);
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MP4Adapter.MyViewHolder holder, int position) {
        MP4 mp4 = mMP4List.get(position);
        holder.videoName.setText(mp4.getName());
        holder.videoKind.setText(mp4.getKind());
    }

    @Override
    public int getItemCount() {
        return mMP4List.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView videoName;
        private TextView videoKind;
        public MyViewHolder(View itemView) {
            super(itemView);
            videoKind = (TextView) itemView.findViewById(R.id.video_kind);
            videoName = (TextView) itemView.findViewById(R.id.video_name);
        }
    }
}
