package it.jaschke.alexandria.api;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import it.jaschke.alexandria.R;
import it.jaschke.alexandria.data.AlexandriaContract;
import it.jaschke.alexandria.data.DbHelper;

/**
 * Created by aditlal on 20/01/16.
 */
public class BookListAdapter extends CursorAdapter {


    private String TAG = "BooksListAdapter";
    DbHelper mDBHelper;
    Context c;

    public static class ViewHolder {
        public final ImageView bookCover;
        public final TextView bookTitle;
        public final TextView bookSubTitle;

        public ViewHolder(View view) {
            bookCover = (ImageView) view.findViewById(R.id.fullBookCover);
            bookTitle = (TextView) view.findViewById(R.id.listBookTitle);
            bookSubTitle = (TextView) view.findViewById(R.id.listBookSubTitle);
        }
    }

    public BookListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.c = context;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        String imgUrl = cursor.getString(cursor.getColumnIndex(AlexandriaContract.BookEntry.IMAGE_URL));

        if (imgUrl != null && !imgUrl.isEmpty())
            Picasso.with(context).load(imgUrl).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).into(viewHolder.bookCover);

        String bookTitle = cursor.getString(cursor.getColumnIndex(AlexandriaContract.BookEntry.TITLE));
        viewHolder.bookTitle.setText(bookTitle);

        String bookSubTitle = cursor.getString(cursor.getColumnIndex(AlexandriaContract.BookEntry.SUBTITLE));
        viewHolder.bookSubTitle.setText(bookSubTitle);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    public Cursor fetchBooksByName(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        mDBHelper = new DbHelper(c);
        Cursor mCursor = null;
        final String selection = AlexandriaContract.BookEntry.TITLE + " LIKE ? OR " + AlexandriaContract.BookEntry.SUBTITLE + " LIKE ? ";
        if (inputText == null || inputText.length() == 0) {
            mCursor = mDBHelper.getReadableDatabase().query(AlexandriaContract.BookEntry.TABLE_NAME, null,
                    selection, null, null, null, null);

        } else {
            mCursor = mDBHelper.getReadableDatabase().query(true, AlexandriaContract.BookEntry.TABLE_NAME, null,
                    selection, null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

}
