package com.example.persistance;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class JSONNameStore extends NameStore{
    final private String namesFileName = "names-JSON.json";
    final private String RESET = "\u001B[47m";
    final private String RED = "\u001B[41m";
    @Override
    public void writeNames(Context context, ArrayList<Name> values) {
        Gson gson = new Gson();

        try (FileOutputStream fos = context.openFileOutput(namesFileName,Context.MODE_PRIVATE)) {

            String fullEntry = gson.toJson(values) ;
            fos.write(fullEntry.getBytes());
            fos.flush();

        } catch (IOException exception) {
            System.out.printf("%sERROR%s: Writing the following list of names to %s\n",RED,RESET,namesFileName);
            for(Name name: values) {
                System.out.println(name.getFirst() + "|" + name.getLast()) ;
            }
            System.out.print("\n");
        }
    }
    @Override
    public ArrayList<Name> getNames(Context context) {

        ArrayList<Name> values = new ArrayList<>();
        try (FileInputStream fis = context.openFileInput(namesFileName)){

            JsonReader reader = new JsonReader(new InputStreamReader(fis, StandardCharsets.UTF_8));
            reader.beginArray();
            while (reader.hasNext()) {
                reader.beginObject();
                while (reader.hasNext()) {
                   reader.nextName();
                   String firstName = reader.nextString();
                   reader.nextName();
                   String lastName = reader.nextString();
                   values.add(new Name(firstName, lastName));
                }
                reader.endObject();
            }
            reader.endArray();
            reader.close();

        } catch (IOException exception) {
            System.out.printf("%sERROR%s: Reading from the following file: %s\n",RED,RESET,namesFileName);
        }
        return values;
    }
}