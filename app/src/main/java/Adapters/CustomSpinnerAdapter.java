package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import Model.Item;

public class CustomSpinnerAdapter extends ArrayAdapter<Integer> {

    public CustomSpinnerAdapter(@NonNull Context context, List<Integer> listIds) {
        super(context, android.R.layout.simple_spinner_item, listIds);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return customView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return customView(position, convertView, parent);
    }

    private View customView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_item, parent, false);
        }
        TextView textView = (TextView) convertView;
        Integer listId = getItem(position);
        textView.setText("List ID: " + listId);
        return convertView;
    }
}
