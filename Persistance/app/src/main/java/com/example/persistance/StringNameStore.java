package com.example.persistance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import android.content.Context;

import kotlin.ParameterName;

public class StringNameStore extends NameStore {
    final private String namesFileName = "names.dsv";
    final private String RESET = "\u001B[47m";
    final private String RED = "\u001B[41m";
    @Override
    public void writeNames(Context context, ArrayList<Name> values){

        try (FileOutputStream fos = context.openFileOutput(namesFileName,Context.MODE_PRIVATE)) {

            for (Name name: values) {
                String fullEntry = name.getFirst() + "|" + name.getLast() + "\n";
                fos.write(fullEntry.getBytes());
                fos.flush();
            }
        } catch (IOException exception) {
            System.out.printf("%sERROR%s: Writing the following list of names to %s\n",RED,RESET,namesFileName);
            for(Name name: values) {
                System.out.println(name.getFirst() + "|" + name.getLast()) ;
            }
            System.out.print("\n");
        }
    }

    @Override
    public ArrayList<Name> getNames(Context context){

        ArrayList<Name> values = new ArrayList<>();
        try (FileInputStream fis = context.openFileInput(namesFileName)){

            InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {

                String line = reader.readLine();
                while(line != null) {
                    String name[] = line.split("\\|");
                    values.add(new Name(name[0], name[1]));
                    line = reader.readLine();
                }
            } catch(IOException exception) {
                System.out.printf("%sERROR%s: Reading from the following file: %s\n",RED,RESET,namesFileName);
            }
        } catch (IOException exception) {
            System.out.printf("%sERROR%s: Reading from the following file: %s\n",RED,RESET,namesFileName);
        }
        return values;
    }
}