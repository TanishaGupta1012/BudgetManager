package com.example.budgetmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import com.example.budgetmanager.Pojo.addpojo;
public class CustomAdapter extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<addpojo> arrayList;

    public CustomAdapter(Context context, int resource, ArrayList<addpojo> arrayList) {
        super(context, resource, arrayList);
        this.arrayList = arrayList;
        this.context = context;
        this.resource = resource;

    }
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(resource, null, false);

        TextView textviewname = view.findViewById(R.id.textviewname);
        TextView textviewamount = view.findViewById(R.id.textviewamount);

        addpojo addpojo1 = arrayList.get(position);
        textviewamount.setText(addpojo1.getAmount());
        textviewname.setText(addpojo1.getExpensename());

        return view;
    }
}
