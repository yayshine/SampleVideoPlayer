package com.yayshine.samplevideoplayer;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    ListView listView;
    SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = (ListView)findViewById(R.id.listView);
        mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, new String[] {MediaStore.Video.Media.DISPLAY_NAME}, new int[]{android.R.id.text1}, 0);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);
                Intent data = new Intent();
                data.setData(uri);
                setResult(RESULT_OK, data);
                finish();
            }
        });

        getSupportLoaderManager().initLoader(0, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Video.Media._ID, MediaStore.Video.Media.DISPLAY_NAME}, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
