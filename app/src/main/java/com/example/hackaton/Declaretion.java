package com.example.hackaton;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Declaretion extends ListActivity {

    public final static String NAME_FILE = "NAME_FILE";

    private ArrayAdapter<String> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] files = this.fileList();

        ArrayList filesToList = new ArrayList();
        for (int i=0; i<files.length; i++) {
            if (files[i].indexOf("dec:")!=-1)
                filesToList.add(files[i]);
        }

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filesToList);

        setListAdapter(mAdapter);
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent(this, SignAndSendActivity.class);
        intent.putExtra(NAME_FILE,l.getItemAtPosition(position).toString());
        startActivity(intent);
    }
}