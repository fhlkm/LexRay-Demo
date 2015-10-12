package com.lextech.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lextech.model.Transactions;
import com.lextech.quiz.json.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanlu Feng on 10/12/2015.
 */
public class MainAdapter extends BaseAdapter {
    private Context mContext;
    private List<Transactions> transactions= new ArrayList<Transactions>();
    private LayoutInflater mInflater;
    public MainAdapter(Context mContext, List<Transactions> transactions){
        this.mContext = mContext;
        this.transactions = transactions;
        this.mInflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        return transactions.size();
    }

    @Override
    public Object getItem(int i) {
        return transactions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return Long.parseLong(transactions.get(i).getmID());
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        ViewHolder mholder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.row_layout,null);
            mholder = new ViewHolder();
            mholder.id = (TextView) convertView.findViewById(R.id.text_id);
            mholder.description = (TextView) convertView.findViewById(R.id.text_description);
            mholder.price = (TextView)convertView.findViewById(R.id.text_price);
            convertView.setTag(mholder);
        }else{
            mholder = (ViewHolder)convertView.getTag();
        }
        mholder.id.setText(transactions.get(i).getmID());
        mholder.description.setText(transactions.get(i).getmDescription());
        mholder.price.setText(transactions.get(i).getPrice()+"");
        return convertView;
    }

    public class ViewHolder{
        public TextView id;
        public TextView description;
        public TextView price;

    }



}
