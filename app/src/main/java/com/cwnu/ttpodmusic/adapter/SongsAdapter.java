package com.cwnu.ttpodmusic.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cwnu.ttpodmusic.R;
import com.cwnu.ttpodmusic.entity.Songs;
import com.cwnu.ttpodmusic.utils.Constat;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by dell on 2018/8/5.
 * 重写适配器
 */

public class SongsAdapter extends BaseAdapter {

    List<Songs> list;
    private LayoutInflater inflate;  //视图扩充器 拿布局文件
    private Context context;
    public SongsAdapter(Context context, List<Songs> list) {
        this.list = list;
        inflate = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SongsAdapter.Holder holder;
        final int position = i;
        if(view==null){
            view = inflate.inflate(R.layout.songs_list_item, null);
            holder = new SongsAdapter.Holder();
            holder.artist = (TextView) view.findViewById(R.id.item_artist);
            holder.song = (TextView) view.findViewById(R.id.item_name);
            holder.download = (ImageView) view.findViewById(R.id.item_download);
            view.setTag(holder);
        }else {
            //如果有直接根据标记拿来使用
            holder = (SongsAdapter.Holder) view.getTag();
        }
        //把数据源设置要显示的控件上
        holder.song.setText(list.get(i).getName());
        holder.artist.setText(list.get(i).getArtist());
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Constat.DOWNLOAD_FLAG);
                intent.putExtra("id",list.get(position).getId());
                intent.putExtra("song",list.get(position).getName());
                Log.i(TAG, "onClick: "+list.get(position).getName()+"准备下载");
                context.sendBroadcast(intent);      // 发送一个下载的广播
            }
        });

        return view;
    }


    private class Holder{
        TextView song;
        TextView artist;
        ImageView download;
    }


}
