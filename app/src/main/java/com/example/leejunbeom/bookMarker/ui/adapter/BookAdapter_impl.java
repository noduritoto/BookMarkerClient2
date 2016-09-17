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
 * Created by noduritoto on 2016-04-04.
 */

public class BookAdapter_impl extends BaseAdapter {

    private Context mContext;
    ArrayList<Book> listBook=new ArrayList<Book>();


    public BookAdapter_impl(Context applicationContext) {
        this.mContext=applicationContext;
    }

    public void setBookData(ArrayList<Book> listBook){
        this.listBook=listBook;
    }

    @Override
    public int getCount() {

        return listBook.size();
    }

    @Override
    public Book getItem(int position) {

        return listBook.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        // menu type count
        return 3;
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
            new BookHolder(convertView);
        }

        BookHolder holder = (BookHolder) convertView.getTag();
        Book book = getItem(position);
        holder.book_name.setText(book.getMark());
        return convertView;
    }

    class BookHolder{
        TextView book_name;
        //ImageView iv_icon;

        public BookHolder(View view){
            book_name = (TextView) view.findViewById(R.id.book_name);
            view.setTag(this);
        }
    }
}
