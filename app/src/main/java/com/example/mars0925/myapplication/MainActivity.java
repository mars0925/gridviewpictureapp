package com.example.mars0925.myapplication;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private RecyclerView recyclerView;
    private static final int request_contacts = 1;
    private Adapter adapter;

    private final String[]  QUERY_COLUMN ={MediaStore.Images.Thumbnails._ID ,MediaStore.Images.Thumbnails.DATA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycleview);

        //檢查是否已經向使用者取得權限
        int permission = ActivityCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE}, request_contacts);

        } else {
            //已經有權限
            Toast.makeText(this, "有權限", Toast.LENGTH_SHORT).show();
            setRecyclerView();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case request_contacts:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { //同意取得權限要做的事
                    setRecyclerView();
                } else//沒有同意取得權限要做的事
                {
                    new AlertDialog.Builder(this)
                            .setMessage("必須取得權限能")
                            .setPositiveButton("OK", null)
                            .show();
                }
        }
    }

    private void setRecyclerView(){
        getLoaderManager().initLoader(0,null,this);
        adapter = new Adapter(getApplicationContext());
        recyclerView.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;

        return new CursorLoader(this,uri,QUERY_COLUMN,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
