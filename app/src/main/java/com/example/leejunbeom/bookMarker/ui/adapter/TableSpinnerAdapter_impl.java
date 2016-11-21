package com.example.leejunbeom.bookMarker.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.leejunbeom.bookMarker.model.pojo.Book;
import com.example.leejunbeom.test.R;

import java.util.ArrayList;

/**
 * Created by noduritoto on 2016. 11. 20..
 */

public class TableSpinnerAdapter_impl extends BaseAdapter{
    private Context mContext;
    ArrayList<String> listTable = new ArrayList<String>();

    public TableSpinnerAdapter_impl (Context applicationContext) {
        this.mContext=applicationContext;

        // 테이블리스트 종류 설정하기
        listTable.add("NoteBook 1");
        listTable.add("NoteBook 2");
        listTable.add("일반석 1");
        listTable.add("일반석 2");
        listTable.add("일반석 3");

    }

    public void setBookData(ArrayList<String> listTable){
        this.listTable = listTable;
    }

    @Override
    public int getCount() {

        return listTable.size();
    }

    @Override
    public String getItem(int position) {

        return listTable.get(position);
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        // menu type count
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        // current menu type
        return position % 3;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = View.inflate(mContext.getApplicationContext(),
                    R.layout.item_list_app, null);
            new TableSpinnerAdapter_impl.TableHolder(convertView);
        }

        TableSpinnerAdapter_impl.TableHolder holder = (TableSpinnerAdapter_impl.TableHolder) convertView.getTag();
        String table = getItem(position);
        holder.book_name.setText(table);
        return convertView;
    }

    class TableHolder{
        TextView book_name;
        //ImageView iv_icon;

        public TableHolder(View view){
            book_name = (TextView) view.findViewById(R.id.book_name);
            view.setTag(this);
        }
    }


}
