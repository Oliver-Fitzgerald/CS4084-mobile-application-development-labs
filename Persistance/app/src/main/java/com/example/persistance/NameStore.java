package com.example.persistance;

import java.util.ArrayList;
import android.content.Context;
public abstract class NameStore {
    public abstract void writeNames(Context context, ArrayList<Name> values);
    public abstract ArrayList<Name> getNames(Context context);

    public ArrayList<Name> splitIntoNames(String rawData) {
        ArrayList<Name> names = new ArrayList<>();

        String[] name = rawData.split(" ");

        if (name.length == 2) {
            String first = name[0];
            String last = name[1];
            names.add(new Name(first,last));
        }

        return names;
    }
}
