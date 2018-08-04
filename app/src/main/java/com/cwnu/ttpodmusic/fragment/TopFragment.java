package com.cwnu.ttpodmusic.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cwnu.ttpodmusic.R;

/**
 * Created by dell on 2018/8/4.
 */

public class TopFragment extends Fragment {
    private TextView back;
    private TextView title;
    private String t;
    private View v;
    public TopFragment(){
        super();
    }
    public TopFragment(String t){
        super();
        this.t = t;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragement_top,container,false);
        setView();


        return v;
    }

    public void setView(){
        back = (TextView) v.findViewById(R.id.back);
        title = (TextView) v.findViewById(R.id.title);

        title.setText(t);
        back.setOnClickListener(new BtnClick());

    }
    public class BtnClick implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            // todo 返回上一个界面
        }
    }


}
