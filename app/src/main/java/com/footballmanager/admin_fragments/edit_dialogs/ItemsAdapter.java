package com.footballmanager.admin_fragments.edit_dialogs;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.footballmanager.R;
import com.footballmanager.model.MappedItem;

import java.util.List;

/**
 * Created by Andrew on 12-Mar-17.
 */

public class ItemsAdapter extends ArrayAdapter<MappedItem> {

    public ItemsAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<MappedItem> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View result = inflater.inflate(R.layout.simple_spinner_layout, null);
        ((TextView) result.findViewById(R.id.name_label)).setText(getItem(position).getName());
        return result;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}
