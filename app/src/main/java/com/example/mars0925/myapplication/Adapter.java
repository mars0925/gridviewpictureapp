package com.example.mars0925.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * Created by mars0925 on 2018/6/19.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private Cursor mCursor;
    private Context context;

    public Adapter(Context context){
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mCursor.moveToPosition(position);//移動游標到一個絕對的位置
        int id = mCursor.getInt(0);

        /*取得*/
        Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail
                (context.getContentResolver(),  id, MediaStore.Images.Thumbnails.MINI_KIND, null);

        ImageView iv = holder.iv;
        iv.setImageBitmap(bitmap);
    }


    @Override
    public int getItemCount() {
        if (mCursor != null) {
            return mCursor.getCount();
        } else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv;
        //itemView 就是要RecyclerView裡面每個項目
        ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.imageView);
        }
    }

    /*自訂swapCursor()方法來更新Cursor，mCursor 為資料集，並由 notifyDataSetChanged() 通知資料更新*/
    void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }
}
