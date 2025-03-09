package com.example.persistance;

import android.content.Context;

import java.util.ArrayList;

public class DBNameStore extends NameStore{

    private static DBHandler names;

    public DBNameStore(Context context) {
        if (names == null)
            names = new DBHandler(context);
    }
    @Override
    public void writeNames(Context context, ArrayList<Name> values) {

        if (!values.isEmpty()) {

            for (Name name: values) {
                names.add(name);
            }
        }
    }

    @Override
    public ArrayList<Name> getNames(Context context) {
        return names.getNames();
    }
}
