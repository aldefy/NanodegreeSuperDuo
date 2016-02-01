package it.jaschke.alexandria;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.barcode.Barcode;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.jaschke.alexandria.data.AlexandriaContract;
import it.jaschke.alexandria.services.BookService;

public class AddBookFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "INTENT_TO_SCAN_ACTIVITY";
    @Bind(R.id.ean)
    EditText ean;
    @Bind(R.id.scan_button)
    Button scanButton;
    @Bind(R.id.bookTitle)
    TextView bookTitle;
    @Bind(R.id.bookSubTitle)
    TextView bookSubTitle;
    @Bind(R.id.bookCover)
    ImageView bookCover;
    @Bind(R.id.authors)
    TextView authors;
    @Bind(R.id.categories)
    TextView categories;
    @Bind(R.id.delete_button)
    Button deleteButton;
    @Bind(R.id.save_button)
    Button saveButton;
    private final int LOADER_ID = 1;
    private View rootView;
    private final String EAN_CONTENT = "eanContent";
    private static final String SCAN_FORMAT = "scanFormat";
    private static final String SCAN_CONTENTS = "scanContents";

    private String mScanFormat = "Format:";
    private String mScanContents = "Contents:";

    public AddBookFragment() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (ean != null) {
            outState.putString(EAN_CONTENT, ean.getText().toString());
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_add_book, container, false);
        ButterKnife.bind(this, rootView);

        ean.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //no need
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //no need
            }

            @Override
            public void afterTextChanged(Editable s) {
                String ean = s.toString();

                if (ean.length() < 10) {
                    return;
                }

                //catch isbn10 numbers
                if (ean.length() == 10 && !ean.startsWith("978")) {
                    ean = "978" + ean;
                }

                //Once we have an ISBN, start a book intent
                Intent bookIntent = new Intent(getActivity(), BookService.class);
                bookIntent.putExtra(BookService.EAN, ean);
                bookIntent.setAction(BookService.FETCH_BOOK);
                getActivity().startService(bookIntent);
                Log.d("anst", "started service");
                getLoaderManager().restartLoader(LOADER_ID, null, AddBookFragment.this);

            }
        });

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), BarcodeScannerActivity.class), 123);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // use correct ean for deleting a book
                String eanStr = ean.getText().toString();
                if (eanStr.length() == 10 && !eanStr.startsWith("978")) {
                    eanStr = "978" + eanStr;
                }

                Intent bookIntent = new Intent(getActivity(), BookService.class);
                bookIntent.putExtra(BookService.EAN, eanStr);
                bookIntent.setAction(BookService.DELETE_BOOK);
                getActivity().startService(bookIntent);
                reset();
            }
        });

        if (savedInstanceState != null) {
            ean.setText(savedInstanceState.getString(EAN_CONTENT));
            ean.setHint("");
        }

        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123 && resultCode == Activity.RESULT_OK) {
            Barcode barcode = data.getParcelableExtra("barcode");
            ean.setText(barcode.displayValue);

        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String eanStr = ean.getText().toString();
        if (eanStr.length() == 10 && !eanStr.startsWith("978")) {
            eanStr = "978" + eanStr;
        }

        final long ean = Long.parseLong(eanStr);

        return new CursorLoader(getActivity(), AlexandriaContract.BookEntry.buildFullBookUri(ean), null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d("anst", "onLoadFinished");

        if (data == null || !data.moveToFirst()) {
            return;
        }

        String title = data.getString(data.getColumnIndex(AlexandriaContract.BookEntry.TITLE));
        bookTitle.setText(title);

        String subTitle = data.getString(data.getColumnIndex(AlexandriaContract.BookEntry.SUBTITLE));
        bookSubTitle.setText(subTitle);

        String authorsString = data.getString(data.getColumnIndex(AlexandriaContract.AuthorEntry.AUTHOR));
        if (authorsString != null) {
            String[] authorsArr = authorsString.split(",");
            authors.setLines(authorsArr.length);
            authors.setText(authorsString.replace(",", "\n"));
        } else {
            authors.setText("");
        }

        String imgUrl = data.getString(data.getColumnIndex(AlexandriaContract.BookEntry.IMAGE_URL));
        if (Patterns.WEB_URL.matcher(imgUrl).matches()) {
            bookCover.setVisibility(View.VISIBLE);
            Picasso.with(getActivity()).load(imgUrl).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).into(bookCover);
        }

        String categoriesString = data.getString(data.getColumnIndex(AlexandriaContract.CategoryEntry.CATEGORY));
        categories.setText(categoriesString);

        /*rootView.findViewById(R.id.save_button).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.delete_button).setVisibility(View.VISIBLE);*/
        saveButton.setEnabled(true);
        deleteButton.setEnabled(true);
        saveButton.setVisibility(View.VISIBLE);
        deleteButton.setVisibility(View.VISIBLE);
        Log.d("anst", "onLoadFinished view must come visible by now");


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    private void reset() {
        ean.setText("");
        bookTitle.setText("");
        bookSubTitle.setText("");
        authors.setText("");
        bookCover.setImageDrawable(null);
        categories.setText("");
        saveButton.setVisibility(View.INVISIBLE);
        deleteButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activity.setTitle(R.string.scan);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
