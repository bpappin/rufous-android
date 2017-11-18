package pappin.rufous.widget;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SimpleListItemCursorAdapter extends CursorRecyclerViewAdapter<SimpleListItemCursorAdapter.ViewHolder> {
    //    private static final String TAG = "SimpleListItemCursorAdapter";
    private String displayColumn;
    private final String clickedColumn;
    private final OnSimpleListItemClickedListener callback;

    public interface OnSimpleListItemClickedListener {
        void OnSimpleListItemClicked(View view, String data);
    }

    public SimpleListItemCursorAdapter(Context context, Cursor cursor, String displayColumn, String clickedColumn, OnSimpleListItemClickedListener callback) {
        super(context, cursor);
        this.displayColumn = displayColumn;
        this.clickedColumn = clickedColumn;
        this.callback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder vh, Cursor cursor) {

//        DatabaseUtils.dumpCurrentRow(cursor);
        bind(vh.text1, displayColumn, cursor);
        final String data = string(clickedColumn, cursor);
        vh.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.OnSimpleListItemClicked(view, data);
                }
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView text1;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.text1 = view.findViewById(android.R.id.text1);
        }
    }

    public static void bind(TextView view, String field, Cursor cursor) {
        if (!cursor.isNull(cursor.getColumnIndex(field))) {
            final String value = cursor.getString(cursor.getColumnIndex(field));
            view.setText(value);
        } else {
            view.setText(null);
        }
    }

    public static String string(String column, Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex(column));
    }
}
