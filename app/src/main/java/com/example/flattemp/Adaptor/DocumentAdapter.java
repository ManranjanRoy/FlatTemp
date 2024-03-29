package com.example.flattemp.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.flattemp.Model.Document;
import com.example.flattemp.Model.UrlsList;
import com.example.flattemp.R;

import java.util.List;

/**
 * Created by Manoranjan on 4/23/2018.
 */

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Document> mUploads;
    private OnItemClickListener mListener;
    public DocumentAdapter(Context mContext, List<Document> mUploads) {
        this.mContext = mContext;
        this.mUploads = mUploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.document_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Document uploadCurrent = mUploads.get(position);

        holder.dname.setText(uploadCurrent.getUpdate_rule_file());
       final String Download= UrlsList.base_url +uploadCurrent.getUpdate_rule_file_loca();
       holder.download.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //String URL ="http://pivotnet.co.in/SocietyManagement/"+uploadCurrent.getUpdate_rule_file_loca();

               Intent i=new Intent();
               i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
               i.setType(Intent.ACTION_VIEW);
               i.setData(Uri.parse(Download));
               mContext.startActivity(i);
           }
       });




    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{
        public TextView  dname;
        ImageView download;

         public ImageViewHolder(View itemView) {

             super(itemView);
             dname = itemView.findViewById(R.id.documentname);
             download=itemView.findViewById(R.id.download);


             itemView.setOnClickListener(this);
             itemView.setOnCreateContextMenuListener(this);
         }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                    switch (menuItem.getItemId()) {
                        case 1:
                            mListener.onWhatEverClick(position);
                            return true;
                        case 2:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;

        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select Action");
            MenuItem doWhatever = contextMenu.add(Menu.NONE, 1, 1, "Update");
            MenuItem delete = contextMenu.add(Menu.NONE, 2, 2, "Delete");

            doWhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onWhatEverClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

}