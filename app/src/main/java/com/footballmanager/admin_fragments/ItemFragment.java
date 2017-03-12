package com.footballmanager.admin_fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.footballmanager.R;
import com.footballmanager.db.DbHelper;
import com.footballmanager.admin_fragments.edit_dialogs.EntityEditDialog;
import com.footballmanager.model.MappedItem;

import java.util.LinkedList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
abstract public class ItemFragment extends Fragment {

    abstract protected String getTableName();
    abstract protected String getIdColumnName();
    abstract protected MappedItem getEmptyItem();
    abstract protected MappedItem makeInstance(Cursor cursor, SQLiteDatabase db);
    abstract protected String[] getProjection();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.add_fab);
        if (view instanceof ListView) {
            ListView listView = (ListView) view;
            listView.setAdapter(new MyItemRecyclerViewAdapter(
                    getContext(), android.R.layout.simple_list_item_1, getData()
            ));
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EntityEditDialog dialog = getEntityEditDialog(getEmptyItem());
                    dialog.show(
                            getFragmentManager(),
                            dialog.getTitle()
                    );
                }
            });
        } else
            fab.setVisibility(View.INVISIBLE);
        return view;
    }

    protected List<MappedItem> getData() {
        List<MappedItem> result = new LinkedList<>();
        SQLiteDatabase db = new DbHelper(getContext()).getReadableDatabase();
        Cursor cursor = db.query(
                getTableName(),
                getProjection(),
                "",
                null,
                "",
                "",
                ""
        );
        while (cursor.moveToNext())
            result.add(makeInstance(cursor, db));
        return result;
    }

    abstract protected EntityEditDialog getEntityEditDialog(MappedItem item);

    class MyItemRecyclerViewAdapter extends ArrayAdapter<MappedItem> {
        public MyItemRecyclerViewAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<MappedItem> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View result = inflater.inflate(R.layout.fragment_item, null);
            final MappedItem item = getItem(position);
            DbHelper dbHelper = new DbHelper(getContext());
            final SQLiteDatabase db = dbHelper.getReadableDatabase();
            Button itemButton = (Button) result.findViewById(R.id.item_selection_button);
            itemButton.setText(item.getName());
            itemButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    EntityEditDialog dialog = getEntityEditDialog(item);
                    dialog.show(
                            getFragmentManager(),
                            dialog.getTitle()
                    );

                    clear();
                    addAll(getData());
                    notifyDataSetChanged();
                }
            });
            result.findViewById(R.id.delete_item_button).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage(R.string.delete_item_dialog_title)
                                   .setPositiveButton(R.string.delete_item_confirm_label, new DialogInterface.OnClickListener() {
                                       public void onClick(DialogInterface dialog, int id) {
                                           String[] whereArgs = { Long.toString(item.getId()) };

                                           db.delete(
                                                   getTableName(),
                                                   getIdColumnName() + " = ?",
                                                   whereArgs
                                           );

                                           clear();
                                           addAll(getData());
                                           notifyDataSetChanged();
                                       }
                                   })
                                   .setNegativeButton(R.string.delete_item_reject_label, null);
                        builder.create().show();
                        }

                    }
            );
            return result;
        }
    }
}
